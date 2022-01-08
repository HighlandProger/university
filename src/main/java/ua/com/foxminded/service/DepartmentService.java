package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.DepartmentDao;
import ua.com.foxminded.domain.Department;
import ua.com.foxminded.exception.ServiceException;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentService(DepartmentDao departmentDao){
        this.departmentDao = departmentDao;
    }

    public Department create(Department department){
        return departmentDao.create(department);
    }

    public Department getById(Long id){

        Optional<Department> department = departmentDao.getById(id);
        if (!department.isPresent()) {
            throw new ServiceException("Department with id=" + id +" is not found");
        }
        return department.get();
    }

    public List<Department> getAll(){
        return departmentDao.getAll();
    }

    public void delete(Long id){
        departmentDao.delete(id);
    }
}
