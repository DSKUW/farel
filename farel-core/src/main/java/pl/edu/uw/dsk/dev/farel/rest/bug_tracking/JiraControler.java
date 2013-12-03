package pl.edu.uw.dsk.dev.farel.rest.bug_tracking;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@RequestMapping("/jira")
public class JiraControler {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String generalInfo() {
        return "General info about Jira";
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{projectId}")
    public @ResponseBody String projectInfo(@PathVariable("projectId") String id) {
        return "Informacje o stanie Jiry dla projektu o id=" + id;
    }
}
