package pl.edu.uw.dsk.dev.farel.rest.calendar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@RequestMapping("/calendar")
public class CalendarControler {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String readProjects() {
        return "Calendar";
    }
}
