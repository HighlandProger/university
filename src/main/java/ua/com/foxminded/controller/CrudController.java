package ua.com.foxminded.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.foxminded.service.CrudService;

public abstract class CrudController<T> {

    protected static final String REDIRECT = "redirect:/";
    private static final String INDEX_VIEW = "/index";
    private static final String SHOW_VIEW = "/show";
    private static final String EDIT_VIEW = "/edit";
    private static final String NEW_VIEW = "/new";

    abstract CrudService<T> getCrudService();

    abstract String getRootPackage();

    abstract String getIndexAttributeName();

    abstract String getAttributeName();

    @GetMapping
    protected String index(Model model) {
        model.addAttribute(this.getIndexAttributeName(), this.getCrudService().getAll());
        return this.getRootPackage() + INDEX_VIEW;
    }

    @GetMapping("/{id}")
    protected String show(long id, Model model) {
        model.addAttribute(this.getAttributeName(), this.getCrudService().getById(id));
        return this.getRootPackage() + SHOW_VIEW;
    }

    protected String newEntity() {
        return this.getRootPackage() + NEW_VIEW;
    }

    protected String create(T model) {

        this.getCrudService().create(model);
        return REDIRECT + this.getRootPackage();
    }

    @GetMapping("/{id}/edit")
    protected String edit(Model model, long id) {

        model.addAttribute(this.getAttributeName(), this.getCrudService().getById(id));
        return this.getRootPackage() + EDIT_VIEW;
    }

    protected String update(T model, long id) {
        this.getCrudService().update(id, model);
        return REDIRECT + this.getRootPackage();
    }

    protected String delete(long id) {

        this.getCrudService().delete(id);
        return REDIRECT + this.getRootPackage();
    }
}
