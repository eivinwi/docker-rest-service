package hello;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@EnableWebMvc
@Controller
@PropertySource("classpath:teams.properties")
public class TeamController implements InitializingBean {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Value("${teams.names}")
    private List<String> teamNames;
    @Value("${tasks.numTasks}")
    private Integer numTasks;

    private Map<String, Team> teams = new HashMap<>();

    public void afterPropertiesSet() {
        for(String teamName : teamNames) {
            teams.put(teamName, new Team(teamName, numTasks));
        }
        Team.startTime = Instant.now();
    }

    @RequestMapping(value = "/status")
    public String getStatus(Model model) {
        int hasFinished = 0;
        for (Team t : teams.values()) {
            if(t.isHasFinished()) {
                hasFinished++;
            }
        }
        model.addAttribute("numTasks", numTasks);
        model.addAttribute("hasFinished", hasFinished);
        model.addAttribute("registered", teams.size());
        model.addAttribute("teams", teams.values());
        return "status";
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String apply(HttpServletRequest request, Model model) {
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
        return getStatus(model);
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
/*
    @RequestMapping(value = "/scoreboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List scoreBoard() {
        List<Team> list = new ArrayList<>(teams.values());
        Collections.sort(list);
        return list.stream().map(t -> new Pair<>(t.getName(), t.getTotalTimeString())).collect(Collectors.toList());
    }
*/
}


//teams.get(name);
//return "Team " + name + " successfully signed up from addr: " + addr + " host:" + host + ". Docker HostName: " + hostName;

//+ ".\nCheck the progress on port 5000 on the ip specified in task assignment";