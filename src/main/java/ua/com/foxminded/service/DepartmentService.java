package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.DepartmentDao;
import ua.com.foxminded.exception.EntityNotFoundException;
import ua.com.foxminded.model.Department;

import java.util.List;

@Service
public class DepartmentService implements CrudService<Department> {

    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentService(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public Department create(Department department) {
        return departmentDao.create(department);
    }

    @Override
    public Department getById(Long id) {
        return departmentDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Department with id=" + id + " is not found"));
    }

    @Override
    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    @Override
    public void delete(Long id) {
        departmentDao.delete(id);
    }

    @Override
    public void update(Long id, Department department) {
        departmentDao.update(id, department);
    }
}
