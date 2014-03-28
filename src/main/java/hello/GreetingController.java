package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  Created by Rasesh Desai on 3/26/14.
 */
@Controller
public class GreetingController {

    @RequestMapping (value = "/greeting", method = RequestMethod.GET)
    public String greeting(@RequestParam (value = "name", required = false, defaultValue = "World") String name, Model model){
        System.out.println("In controller...");
        model.addAttribute("name", name);
        return "greeting";
    }
}
