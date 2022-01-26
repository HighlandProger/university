package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController extends CrudController<Group> {

    private static final String ROOT_PACKAGE = "groups";
    private static final String INDEX_ATTRIBUTE_NAME = "groups";
    private static final String ATTRIBUTE_NAME = "group";
    private static final String ID = "id";
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    protected CrudService<Group> getCrudService() {
        return groupService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

    @Override
    protected String getIndexEntityName() {
        return INDEX_ATTRIBUTE_NAME;
    }

    @Override
    protected String getEntityName() {
        return ATTRIBUTE_NAME;
    }

    public String index(Model model) {
        return super.index(model);
    }

    public String show(@PathVariable(ID) long id, Model model) {
        return super.show(id, model);
    }

    @GetMapping("/new")
    public String newEntity(@ModelAttribute(ATTRIBUTE_NAME) Group group) {
        return super.newEntity();
    }

    @PostMapping
    public String create(@ModelAttribute(ATTRIBUTE_NAME) Group group) {
        return super.create(group);
    }

    public String edit(Model model, @PathVariable(ID) long id) {
        return super.edit(model, id);
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute(ATTRIBUTE_NAME) Group group, @PathVariable(ID) long id) {
        return super.update(group, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        return super.delete(id);
    }
}
