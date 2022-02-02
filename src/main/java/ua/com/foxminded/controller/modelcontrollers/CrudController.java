package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.service.CrudService;

public abstract class CrudController<T> {

    protected static final String REDIRECT = "redirect:/";
    protected static final String INDEX_VIEW = "/index";
    protected static final String SHOW_VIEW = "/show";
    protected static final String EDIT_VIEW = "/edit";
    protected static final String NEW_VIEW = "/new";
    protected static final String ID = "id";
    protected static final String ENTITY_NAME = "entity";
    protected static final String INDEX_ENTITY_NAME = "entities";

    protected abstract CrudService<T> getCrudService();

    protected abstract String getRootPackage();

    @GetMapping
    protected String index(Model model) {
        model.addAttribute(INDEX_ENTITY_NAME, this.getCrudService().getAll());
        return this.getRootPackage() + INDEX_VIEW;
    }

    @GetMapping("/{id}")
    protected String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(ENTITY_NAME, this.getCrudService().getById(id));
        return this.getRootPackage() + SHOW_VIEW;
    }

    @GetMapping("/new")
    protected String newEntity(Model model, T value) {
        model.addAttribute(ENTITY_NAME, value);
        return this.getRootPackage() + NEW_VIEW;
    }

    @PostMapping
    protected String create(@ModelAttribute(ENTITY_NAME) T model) {

        this.getCrudService().create(model);
        return REDIRECT + this.getRootPackage();
    }

    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_NAME, this.getCrudService().getById(id));
        return this.getRootPackage() + EDIT_VIEW;
    }

    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_NAME) T model, @PathVariable(ID) long id) {

        this.getCrudService().update(id, model);
        return REDIRECT + this.getRootPackage();
    }

    @DeleteMapping("/{id}")
    protected String delete(@PathVariable(ID) long id) {

        this.getCrudService().delete(id);
        return REDIRECT + this.getRootPackage();
    }
}
