package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.LessonService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/lessons")
public class LessonController extends CrudController<Lesson> {

    private static final String POST_MAPPING_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter postMappingFormatter = DateTimeFormatter.ofPattern(POST_MAPPING_TIME_PATTERN);
    private static final String PATCH_MAPPING_TIME_PATTERN = "dd.MM.yyyy, HH:mm";
    private static final DateTimeFormatter patchMappingFormatter = DateTimeFormatter.ofPattern(PATCH_MAPPING_TIME_PATTERN);

    private static final String ROOT_PACKAGE = "lessons";
    private static final String INDEX_ATTRIBUTE_NAME = "lessons";
    private static final String ATTRIBUTE_NAME = "lesson";
    private static final String ID = "id";
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    CrudService<Lesson> getCrudService() {
        return lessonService;
    }

    @Override
    String getRootPackage() {
        return ROOT_PACKAGE;
    }

    @Override
    String getIndexAttributeName() {
        return INDEX_ATTRIBUTE_NAME;
    }

    @Override
    String getAttributeName() {
        return ATTRIBUTE_NAME;
    }

    @Override
    @GetMapping
    public String index(Model model) {
        return super.index(model);
    }

    @Override
    @GetMapping("/{id}")
    public String show(@PathVariable(ID) long id, Model model) {
        return super.show(id, model);
    }

    @GetMapping("/new")
    public String newEntity(@ModelAttribute(ATTRIBUTE_NAME) Lesson lesson) {
        return super.newEntity();
    }

    @PostMapping
    public String create(@RequestParam("name") String name, @RequestParam("groupId") Long groupId, @RequestParam("teacherId") Long teacherId, @RequestParam("dateTime") String dateTimeString, Model model) {

        Lesson lesson = lessonService.create(new Lesson(name, groupId, teacherId, LocalDateTime.parse(dateTimeString, postMappingFormatter)));
        model.addAttribute(ATTRIBUTE_NAME, lesson);

        return REDIRECT + ROOT_PACKAGE;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable(ID) long id) {
        return super.edit(model, id);
    }

    @PatchMapping("/{id}")
    public String update(@RequestParam("name") String name, @RequestParam("groupId") Long groupId, @RequestParam("teacherId") Long teacherId, @RequestParam("dateTime") String dateTimeString, Model model, @PathVariable(ID) long id) {

        lessonService.update(id, new Lesson(name, groupId, teacherId, LocalDateTime.parse(dateTimeString, patchMappingFormatter)));
        model.addAttribute(ATTRIBUTE_NAME, lessonService.getById(id));

        return REDIRECT + ROOT_PACKAGE;
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        return super.delete(id);
    }
}

