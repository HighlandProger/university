package ua.com.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class {@link IndexController}
 */
@Controller
public class IndexController {

    /**
     * Returns index view - main page
     *
     * @return index view - main page
     */
    @GetMapping
    public String index() {
        return "index";
    }
}
