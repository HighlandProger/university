package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController extends CrudController<Group> {

    private static final String ROOT_PACKAGE = "groups";
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

}
