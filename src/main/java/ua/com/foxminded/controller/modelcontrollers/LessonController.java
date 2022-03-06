package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.dto.LessonDTO;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.service.ClassRoomService;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.LessonService;
import ua.com.foxminded.service.TeacherService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ua.com.foxminded.controller.modelcontrollers.AbstractController.REDIRECT;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private static final String TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);

    private static final String VIEW_FOLDER_NAME = "lessons";
    private static final String INDEX_VIEW = "/index";
    private static final String EDIT_VIEW = "/edit";
    private static final String ID = "id";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String GROUPS_ATTRIBUTE_NAME = "groups";
    private static final String TEACHERS_ATTRIBUTE_NAME = "teachers";
    private static final String CLASSROOMS_ATTRIBUTE_NAME = "classrooms";
    private static final String LESSONS_ATTRIBUTE_NAME = "lessonDTOS";

    private final LessonService lessonService;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final ClassRoomService classRoomService;

    @Autowired
    public LessonController(LessonService lessonService, GroupService groupService, TeacherService teacherService, ClassRoomService classRoomService) {
        this.lessonService = lessonService;
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.classRoomService = classRoomService;
    }

    @GetMapping
    protected String index(Model model) {

        model.addAttribute(LESSONS_ATTRIBUTE_NAME, getLessonDTOS());
        model.addAttribute(ENTITIES_ATTRIBUTE_NAME, lessonService.getAll());
        return VIEW_FOLDER_NAME + INDEX_VIEW;
    }

    @GetMapping("/new")
    protected String newEntity(Model model) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, new Lesson());
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        model.addAttribute(TEACHERS_ATTRIBUTE_NAME, teacherService.getAll());
        model.addAttribute(CLASSROOMS_ATTRIBUTE_NAME, classRoomService.getAll());
        return VIEW_FOLDER_NAME + EDIT_VIEW;
    }

    @PostMapping
    public String create(@RequestParam("name") String name, @RequestParam("teacherId") Long teacherId, @RequestParam("groupId") Long groupId, @RequestParam("dateTime") String dateTimeString, @RequestParam("classRoomId") Long classRoomId, Model model) {

        Lesson lesson = lessonService.create(new Lesson(name, teacherId, groupId, LocalDateTime.parse(dateTimeString, timeFormatter), classRoomId));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lesson);

        return REDIRECT + VIEW_FOLDER_NAME;
    }

    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lessonService.getById(id));
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        model.addAttribute(TEACHERS_ATTRIBUTE_NAME, teacherService.getAll());
        model.addAttribute(CLASSROOMS_ATTRIBUTE_NAME, classRoomService.getAll());
        return VIEW_FOLDER_NAME + EDIT_VIEW;
    }

    @PutMapping("/{id}")
    public String update(@RequestParam("name") String name, @RequestParam("teacherId") Long teacherId, @RequestParam("groupId") Long groupId, @RequestParam("dateTime") String dateTimeString, @RequestParam("classRoomId") Long classRoomId, Model model, @PathVariable(ID) long id) {

        lessonService.update(id, new Lesson(name, teacherId, groupId, LocalDateTime.parse(dateTimeString, timeFormatter), classRoomId));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lessonService.getById(id));

        return REDIRECT + VIEW_FOLDER_NAME;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {

        lessonService.delete(id);
        return REDIRECT + VIEW_FOLDER_NAME;
    }

    private List<LessonDTO> getLessonDTOS() {
        return lessonService.getAll().stream().map(el -> new LessonDTO(el, groupService.getById(el.getGroupId()).getAbbreviation(), teacherService.getById(el.getTeacherId()), classRoomService.getById(el.getClassRoomId()).getClassNumber())).collect(Collectors.toList());
    }
}
