package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.service.LessonService;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    private final LessonService lessonService;

    @Autowired
    public TimetableController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("entities", lessonService.getAll());
        return "timetable/index";
    }
}
