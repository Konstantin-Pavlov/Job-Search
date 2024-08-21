package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.util.MvcControllersUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String welcome(Model model, Authentication authentication) {
        MvcControllersUtil.authCheck(
                model,
                authentication
        );
        return "index";
    }
}
