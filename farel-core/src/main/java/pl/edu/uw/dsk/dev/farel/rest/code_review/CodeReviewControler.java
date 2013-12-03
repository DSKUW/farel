package pl.edu.uw.dsk.dev.farel.rest.code_review;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@RequestMapping("/codereview")
public class CodeReviewControler {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String generalInfo() {
        return "General info about CodeReview";
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{projectId}")
    public @ResponseBody String projectInfo(@PathVariable("projectId") String id) {
        return "Informacje o stanie CodeReview dla projektu o id=" + id;
    }
}
