package ua.com.foxminded.controller;

import org.springframework.ui.Model;
import ua.com.foxminded.service.CrudService;

public abstract class CrudController<T> {

    static final String INDEX_VIEW = "/index";
    static final String SHOW_VIEW = "/show";
    static final String EDIT_VIEW = "/edit";
    static final String NEW_VIEW = "/new";
    static final String REDIRECT = "redirect:/";

    abstract CrudService<T> getCrudService();

    abstract String getRootPackage();

    abstract String getIndexAttributeName();

    abstract String getAttributeName();


    String index(Model model) {
        model.addAttribute(this.getIndexAttributeName(), this.getCrudService().getAll());
        return this.getRootPackage() + INDEX_VIEW;
    }

    String show(long id, Model model) {
        model.addAttribute(this.getAttributeName(), this.getCrudService().getById(id));
        return this.getRootPackage() + SHOW_VIEW;
    }

    String newEntity() {
        return this.getRootPackage() + NEW_VIEW;
    }

    String create(T model) {

        this.getCrudService().create(model);
        return REDIRECT + this.getRootPackage();
    }

    String edit(Model model, long id) {

        model.addAttribute(this.getAttributeName(), this.getCrudService().getById(id));
        return this.getRootPackage() + EDIT_VIEW;
    }

    String update(T model, long id) {
        this.getCrudService().update(id, model);
        return REDIRECT + this.getRootPackage();
    }

    String delete(long id) {

        this.getCrudService().delete(id);
        return REDIRECT + this.getRootPackage();
    }
}
