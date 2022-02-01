package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.service.LessonService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ua.com.foxminded.controller.modelcontrollers.CrudController.REDIRECT;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private static final String POST_MAPPING_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter postMappingFormatter = DateTimeFormatter.ofPattern(POST_MAPPING_TIME_PATTERN);
    private static final String PATCH_MAPPING_TIME_PATTERN = "dd.MM.yyyy, HH:mm";
    private static final DateTimeFormatter patchMappingFormatter = DateTimeFormatter.ofPattern(PATCH_MAPPING_TIME_PATTERN);

    private static final String ROOT_PACKAGE = "lessons";
    private static final String ENTITY_NAME = "entity";
    private static final String INDEX_ENTITY_NAME = "entities";
    private static final String INDEX_VIEW = "/index";
    private static final String SHOW_VIEW = "/show";
    private static final String EDIT_VIEW = "/edit";
    private static final String NEW_VIEW = "/new";
    private static final String ID = "id";
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    protected String index(Model model) {
        model.addAttribute(INDEX_ENTITY_NAME, lessonService.getAll());
        return ROOT_PACKAGE + INDEX_VIEW;
    }

    @GetMapping("/{id}")
    protected String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(ENTITY_NAME, lessonService.getById(id));
        return ROOT_PACKAGE + SHOW_VIEW;
    }

    @GetMapping("/new")
    protected String newEntity(@ModelAttribute("entity") Lesson lesson) {
        return ROOT_PACKAGE + NEW_VIEW;
    }

    @PostMapping
    public String createLesson(@RequestParam("name") String name,
                               @RequestParam("groupId") Long groupId,
                               @RequestParam("teacherId") Long teacherId,
                               @RequestParam("dateTime") String dateTimeString,
                               @RequestParam("classRoomId") Long classRoomId,
                               Model model) {

        Lesson lesson = lessonService.create(new Lesson(name, groupId, teacherId, LocalDateTime.parse(dateTimeString, postMappingFormatter), classRoomId));
        model.addAttribute(ENTITY_NAME, lesson);

        return REDIRECT + ROOT_PACKAGE;
    }

    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_NAME, lessonService.getById(id));
        return ROOT_PACKAGE + EDIT_VIEW;
    }

    @PutMapping("/{id}")
    public String update(@RequestParam("name") String name,
                         @RequestParam("groupId") Long groupId,
                         @RequestParam("teacherId") Long teacherId,
                         @RequestParam("dateTime") String dateTimeString,
                         @RequestParam("classRoomId") Long classRoomId, Model model,
                         @PathVariable(ID) long id) {

        lessonService.update(id, new Lesson(name, groupId, teacherId, LocalDateTime.parse(dateTimeString, patchMappingFormatter), classRoomId));
        model.addAttribute(ENTITY_NAME, lessonService.getById(id));

        return REDIRECT + ROOT_PACKAGE;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        lessonService.delete(id);
        return REDIRECT + ROOT_PACKAGE;
    }
}

