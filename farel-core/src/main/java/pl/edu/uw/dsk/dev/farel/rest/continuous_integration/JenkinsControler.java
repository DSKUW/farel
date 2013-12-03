package pl.edu.uw.dsk.dev.farel.rest.continuous_integration;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@RequestMapping("/jenkins")
public class JenkinsControler {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String generalInfo() {
        return "General info about Jenkins";
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{projectId}")
    public @ResponseBody String projectInfo(@PathVariable("projectId") String id) {
        return "Informacje o stanie Jenkins'a dla projektu o id=" + id;
    }
}
