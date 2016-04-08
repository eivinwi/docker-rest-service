package hello;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javafx.util.Pair;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@EnableWebMvc
@Controller
public class TeamController implements InitializingBean {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private Map<String, Team> teams = new HashMap<>();

    public void afterPropertiesSet() {
        createTestTeams();
        Team.startTime = Instant.now();
    }

    private void createTestTeams() {
        teams.put("testTeam", new Team("testTeam"));
        teams.put("uWotM8", new Team("uWotM8"));
    }

    @RequestMapping(value = "/test")
    public String doTest() {
        return "test";
    }

    @RequestMapping(value = "/status")
    public String getStatus(Model model) {
        int hasFinished = 0;
        for (Team t : teams.values()) {
            if(t.isHasFinished()) {
                hasFinished++;
            }
        }
        model.addAttribute("hasFinished", hasFinished);
        model.addAttribute("registered", teams.size());
        model.addAttribute("teams", teams.values());
        return "status";
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Team apply(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("status");
        //@RequestParam(value="name") String name) {
        /*if(name == null || name.isEmpty()) {
            return "Team name not supplied.";
        }
        if(!teams.containsKey(name)) {
            return "Team name " + name + " is incorrect, does not exist in predefined list.";
        }
        if(teams.get(name)) {
            return "Team " + name + " has already registered";
        }*/
        // Valid team, check request

        String name = (request.getHeader("name") != null)? request.getHeader("name") : "";
        if(name.isEmpty()) {
           return null;
        }
        String addr = request.getRemoteAddr();
        String host = request.getRemoteHost();
        String hostName = request.getParameter("hostname");

        Team team;
        if (!teams.containsKey(name)) {
            team = null;
            //team = new Team(name);
            //team.setHostIp(addr);
            //teams.put(name, team);
        } else {
            team = teams.get(name);
            team.setHostIp(addr);
            team.setHasFinished(true);
        }
        return team;
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<Team> getTeams() {
        return teams.values();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getAll() {
        Map<String, Object> json = new HashMap<>();
        json.put("startTime", Team.getStartTime());
        json.put("teams", teams.values());
        return json;
    }

    @RequestMapping(value = "/scoreboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List scoreBoard() {
        List<Team> list = new ArrayList<>(teams.values());
        Collections.sort(list);
        return list.stream().map(t -> new Pair<>(t.getName(), t.getTotalTimeString())).collect(Collectors.toList());
    }

}


//teams.get(name);
//return "Team " + name + " successfully signed up from addr: " + addr + " host:" + host + ". Docker HostName: " + hostName;

//+ ".\nCheck the progress on port 5000 on the ip specified in task assignment";