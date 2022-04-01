package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.foxminded.config.SpringWebConfig;
import ua.com.foxminded.service.AbstractService;

/**
 * Controller class {@link AbstractController}
 *
 * @param <T> class from {@link ua.com.foxminded.model} package
 */
public abstract class AbstractController<T> {

    /**
     * Property - {@link #REDIRECT} for mappings
     */
    protected static final String REDIRECT = "redirect:/";
    /**
     * Property - {@link #INDEX_VIEW} for mappings
     */
    protected static final String INDEX_VIEW = "/index";
    /**
     * Property - {@link #EDIT_VIEW} for mappings
     */
    protected static final String EDIT_VIEW = "/edit";
    /**
     * Property - {@link #ID} model property
     */
    protected static final String ID = "id";
    /**
     * Property - {@link #ENTITY_ATTRIBUTE_NAME} for model attribute
     */
    protected static final String ENTITY_ATTRIBUTE_NAME = "entity";
    /**
     * Property - {@link #ENTITIES_ATTRIBUTE_NAME} for model attribute
     */
    protected static final String ENTITIES_ATTRIBUTE_NAME = "entities";

    /**
     * Returns service class to work with table in database,
     * mapped by value, which described in {@link AbstractController}
     *
     * @return service class to work with table in database,
     * mapped by value, which described in {@link AbstractController}
     */
    protected abstract AbstractService<T> getCrudService();

    /**
     * Returns name of view folder in path, described in {@link SpringWebConfig#templateResolver()}
     *
     * @return name of view folder in path, described in {@link SpringWebConfig#templateResolver()}
     * @see SpringWebConfig#templateResolver()
     */
    protected abstract String getViewFolderName();

    /**
     * Returns view of all elements from table of a value, described in AbstractController.
     * Mapping for GET request in {@link #INDEX_VIEW}. Model adds list of entities of type,
     * described in AbstractController with attribute name: {@link #ENTITIES_ATTRIBUTE_NAME}.
     *
     * @param model model to send to view
     * @return view of all elements form table of a value, described in {@link AbstractController}
     * @see AbstractService#getAll()
     */
    @GetMapping
    protected String index(Model model) {
        model.addAttribute(ENTITIES_ATTRIBUTE_NAME, this.getCrudService().getAll());
        return this.getViewFolderName() + INDEX_VIEW;
    }

    /**
     * Returns view of creation new entity of type, described in {@link AbstractController}.
     * Mapping for GET request in {@link #EDIT_VIEW} of current model. Model adds entity with empty fields
     * of type, described in {@link AbstractController} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME}
     *
     * @param model model to send to view
     * @param value entity of type, described in {@link AbstractController}
     * @return new entity creation view of type, described in {@link AbstractController}
     */
    @GetMapping("/new")
    protected String newEntity(Model model, T value) {
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, value);
        return this.getViewFolderName() + EDIT_VIEW;
    }

    /**
     * Returns view of edition entity. Path variable annotation reads from model property {@link #ID}.
     * After, using AbstractService, searches by read id entity in database.
     * This entity puts in param model with attribute name: {@link #ENTITY_ATTRIBUTE_NAME}
     *
     * @param model model to send to view
     * @param id    {@link #ID} of entity, described by {@link AbstractController}
     * @return view of edition entity
     * @see AbstractService#getById(Long)
     */
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, this.getCrudService().getById(id));
        return this.getViewFolderName() + EDIT_VIEW;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of entities,
     * described in {@link AbstractController} without deleted by {@link #ID} entity.
     * Path variable annotation reads entity's property {@link #ID} from model and
     * deletes this entity from its table.
     *
     * @param id {@link #ID} of entity, described by {@link AbstractController}
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of entities,
     * described in {@link AbstractController} without deleted by {@link #ID} entity
     * @see AbstractService#delete(Long)
     */
    @DeleteMapping("/{id}")
    protected String delete(@PathVariable(ID) long id) {

        this.getCrudService().delete(id);
        return REDIRECT + this.getViewFolderName();
    }
}
