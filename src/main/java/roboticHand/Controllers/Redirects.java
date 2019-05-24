package roboticHand.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
Class for redirecting to the authorization page
 */
@Controller
@RequestMapping("/")
public class Redirects {

    /*
    This method redirects to the authorization page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    /*
    This method redirects to the authorization page
     */
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirect(){
        return "index";
    }
}
