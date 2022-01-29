package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.foxminded.model.*;
import ua.com.foxminded.service.*;
import ua.com.foxminded.utils.DateUtils;

@Controller
public class MenuController {

    private static final String MAIN_VIEW = "/main";
    private static final String SUCCESS_PAGE_VIEW = "/successPage";
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final DepartmentService departmentService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final LessonService lessonService;

    @Autowired
    public MenuController(StudentService studentService,
                          TeacherService teacherService,
                          DepartmentService departmentService,
                          CourseService courseService,
                          GroupService groupService,
                          LessonService lessonService) {

        this.studentService = studentService;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.lessonService = lessonService;
    }

    @GetMapping
    public String main() {
        return MAIN_VIEW;
    }

    @PostMapping
    public String createDefaultObjects() {
        studentService.create(new Student("John", "Snow", 23));
        Teacher teacher = teacherService.create(new Teacher("Steve", "Jobs", 45));
        Department department = departmentService.create(new Department("IT"));
        Course course = courseService.create(new Course(2022));
        Group group = groupService.create(new Group(department.getId(), course.getId(), 1));
        lessonService.create(new Lesson("Math", teacher.getId(), group.getId(), DateUtils.getLocalDateTimeFromString("15.01.2022 15:30")));

        return SUCCESS_PAGE_VIEW;
    }
}
