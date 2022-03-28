package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.DepartmentService;

/**
 * Controller class {@link DepartmentController} for model {@link Department}
 */
@Controller
@RequestMapping("/departments")
public class DepartmentController extends AbstractController<Department> {

    /**
     * Property - {@link #VIEW_FOLDER_NAME} name of folder with index view
     */
    private static final String VIEW_FOLDER_NAME = "departments";
    /**
     * Property - {@link #departmentService} service for database
     */
    private final DepartmentService departmentService;

    /**
     * Constructor {@link DepartmentController} initializes {@link #departmentService} property using Spring
     *
     * @param departmentService {@link DepartmentService} bean
     */
    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Returns {@link AbstractService} with value {@link Department} for using inherited methods
     *
     * @return {@link AbstractService} with value {@link Department} for using inherited methods
     * @see AbstractController#getCrudService()
     */
    @Override
    protected AbstractService<Department> getCrudService() {
        return departmentService;
    }

    /**
     * Returns {@link #VIEW_FOLDER_NAME} for using inherited methods
     *
     * @return {@link #VIEW_FOLDER_NAME} for using inherited methods
     * @see AbstractController#getViewFolderName()
     */
    @Override
    protected String getViewFolderName() {
        return VIEW_FOLDER_NAME;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for POST request casts model to {@link Department}
     * object. After, retrieved {@link Department} object saves to database
     *
     * @param department retrieved from model {@link Department} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * with all and created a new one {@link Department} objects
     * @see AbstractService#create(Object)
     */
    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) Department department) {

        departmentService.create(department);
        return REDIRECT + this.getViewFolderName();
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for PUT request casts model to {@link Department}
     * object. After, retrieved {@link Department} object updates in database
     *
     * @param department retrieved from model {@link Department} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#update(Object)
     */
    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) Department department) {

        departmentService.update(department);
        return REDIRECT + this.getViewFolderName();
    }
}
