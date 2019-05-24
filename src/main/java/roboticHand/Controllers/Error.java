package roboticHand.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class Error {
    /*
    This method
     */
    @RequestMapping(value = "/")
    public String redirectError(){
        return "error";
    }
}
