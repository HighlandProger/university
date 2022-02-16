package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.dto.GroupDTO;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.service.CourseService;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class GroupController extends CrudController<Group> {

    private static final String VIEW_FOLDER_NAME = "groups";
    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";
    private static final String COURSES_ATTRIBUTE_NAME = "courses";
    private static final String GROUPS_ATTRIBUTE_NAME = "groupDTOS";
    private final GroupService groupService;
    private final DepartmentService departmentService;
    private final CourseService courseService;

    @Autowired
    public GroupController(GroupService groupService, DepartmentService departmentService, CourseService courseService) {
        this.groupService = groupService;
        this.departmentService = departmentService;
        this.courseService = courseService;
    }

    @Override
    protected CrudService<Group> getCrudService() {
        return groupService;
    }

    @Override
    protected String getViewFolderName() {
        return VIEW_FOLDER_NAME;
    }

    @Override
    @GetMapping
    protected String index(Model model) {

        model.addAttribute(GROUPS_ATTRIBUTE_NAME, getGroupDTOS());
        return super.index(model);
    }

    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Group group) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, group);
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        model.addAttribute(COURSES_ATTRIBUTE_NAME, courseService.getAll());
        return VIEW_FOLDER_NAME + EDIT_VIEW;
    }

    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, this.getCrudService().getById(id));
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        model.addAttribute(COURSES_ATTRIBUTE_NAME, courseService.getAll());
        return this.getViewFolderName() + EDIT_VIEW;
    }

    private List<GroupDTO> getGroupDTOS() {
        return groupService.getAll().stream()
            .map(el -> new GroupDTO(
                el,
                departmentService.getById(el.getDepartmentId()).getName(),
                courseService.getById(el.getCourseId()).getEstablishYear()))
            .collect(Collectors.toList());
    }
}
