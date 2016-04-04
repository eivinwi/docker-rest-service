package hello;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController implements InitializingBean {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private HashMap<String, Boolean> teams = new HashMap<>();

    public void afterPropertiesSet() {
        //public void setTeam(Map<String, Boolean> teams) {
        //this.teams = teams;
        this.teams = new HashMap<>();
        teams.put("test", false);
        teams.put("already_there", true);
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/apply")
    public String apply(HttpServletRequest request) {//},
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
        String name = "hey";

        // Valid team, check request

        String addr = request.getRemoteAddr();
        String host = request.getRemoteHost();

        String hostName = request.getParameter("hostname");


        teams.replace(name, true);
        return "Team " + name + " successfully signed up from addr: " + addr + " host:" + host + ". Docker HostName: " + hostName;

                //+ ".\nCheck the progress on port 5000 on the ip specified in task assignment";
    }

}