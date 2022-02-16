package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.service.CrudService;

public abstract class CrudController<T> {

    protected static final String REDIRECT = "redirect:/";
    protected static final String INDEX_VIEW = "/index";
    protected static final String EDIT_VIEW = "/edit";
    protected static final String ID = "id";
    protected static final String ENTITY_ATTRIBUTE_NAME = "entity";
    protected static final String ENTITIES_ATTRIBUTE_NAME = "entities";

    protected abstract CrudService<T> getCrudService();

    protected abstract String getViewFolderName();

    @GetMapping
    protected String index(Model model) {
        model.addAttribute(ENTITIES_ATTRIBUTE_NAME, this.getCrudService().getAll());
        return this.getViewFolderName() + INDEX_VIEW;
    }

    @GetMapping("/new")
    protected String newEntity(Model model, T value) {
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, value);
        return this.getViewFolderName() + EDIT_VIEW;
    }

    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) T model) {

        this.getCrudService().create(model);
        return REDIRECT + this.getViewFolderName();
    }

    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, this.getCrudService().getById(id));
        return this.getViewFolderName() + EDIT_VIEW;
    }

    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) T model, @PathVariable(ID) long id) {

        this.getCrudService().update(id, model);
        return REDIRECT + this.getViewFolderName();
    }

    @DeleteMapping("/{id}")
    protected String delete(@PathVariable(ID) long id) {

        this.getCrudService().delete(id);
        return REDIRECT + this.getViewFolderName();
    }
}
