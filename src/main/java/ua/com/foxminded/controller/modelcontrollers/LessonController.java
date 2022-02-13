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

import static ua.com.foxminded.controller.modelcontrollers.CrudController.REDIRECT;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private static final String POST_MAPPING_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter postMappingFormatter = DateTimeFormatter.ofPattern(POST_MAPPING_TIME_PATTERN);
    private static final String PATCH_MAPPING_TIME_PATTERN = "dd.MM.yyyy, HH:mm";
    private static final DateTimeFormatter patchMappingFormatter = DateTimeFormatter.ofPattern(PATCH_MAPPING_TIME_PATTERN);

    private static final String ROOT_PACKAGE = "lessons";
    private static final String INDEX_VIEW = "/index";
    private static final String EDIT_VIEW = "/edit";
    private static final String ID = "id";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String GROUPS_ATTRIBUTE_NAME = "groups";
    private static final String TEACHERS_ATTRIBUTE_NAME = "teachers";
    private static final String CLASSROOMS_ATTRIBUTE_NAME = "classrooms";
    private static final String LESSON_DTOS_ATTRIBUTE_NAME = "lessonDTOS";

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

    private List<LessonDTO> getLessonDTOS(){
        return lessonService.getAll().stream().
            map(el -> new LessonDTO(
                el,
                groupService.getById(el.getGroupId()),
                teacherService.getById(el.getTeacherId()),
                classRoomService.getById(el.getClassRoomId()))).
            collect(Collectors.toList());
    }

    @GetMapping
    protected String index(Model model) {

        model.addAttribute(LESSON_DTOS_ATTRIBUTE_NAME, getLessonDTOS());
        model.addAttribute(ENTITIES_ATTRIBUTE_NAME, lessonService.getAll());
        return ROOT_PACKAGE + INDEX_VIEW;
    }

    @GetMapping("/new")
    protected String newEntity(Model model) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, new Lesson());
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        model.addAttribute(TEACHERS_ATTRIBUTE_NAME, teacherService.getAll());
        model.addAttribute(CLASSROOMS_ATTRIBUTE_NAME, classRoomService.getAll());
        return ROOT_PACKAGE + EDIT_VIEW;
    }

    @PostMapping
    public String createLesson(@RequestParam("name") String name, @RequestParam("groupId") Long groupId, @RequestParam("teacherId") Long teacherId, @RequestParam("dateTime") String dateTimeString, @RequestParam("classRoomId") Long classRoomId, Model model) {

        Lesson lesson = lessonService.create(new Lesson(name, groupId, teacherId, LocalDateTime.parse(dateTimeString, postMappingFormatter), classRoomId));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lesson);

        return REDIRECT + ROOT_PACKAGE;
    }

    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lessonService.getById(id));
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        model.addAttribute(TEACHERS_ATTRIBUTE_NAME, teacherService.getAll());
        model.addAttribute(CLASSROOMS_ATTRIBUTE_NAME, classRoomService.getAll());
        return ROOT_PACKAGE + EDIT_VIEW;
    }

    @PutMapping("/{id}")
    public String update(@RequestParam("name") String name, @RequestParam("groupId") Long groupId, @RequestParam("teacherId") Long teacherId, @RequestParam("dateTime") String dateTimeString, @RequestParam("classRoomId") Long classRoomId, Model model, @PathVariable(ID) long id) {

        lessonService.update(id, new Lesson(name, groupId, teacherId, LocalDateTime.parse(dateTimeString, patchMappingFormatter), classRoomId));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lessonService.getById(id));

        return REDIRECT + ROOT_PACKAGE;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {

        lessonService.delete(id);
        return REDIRECT + ROOT_PACKAGE;
    }
}

