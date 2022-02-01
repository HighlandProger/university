package ua.com.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    private static final String MAIN_VIEW = "/main";

    @GetMapping
    public String main() {
        return MAIN_VIEW;
    }

}
