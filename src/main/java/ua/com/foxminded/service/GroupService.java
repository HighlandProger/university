package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class GroupService implements CrudService<Group> {

    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public Group create(Group group) {
        return groupDao.create(group);
    }

    @Override
    public Group getById(Long id) {
        return groupDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Group with id=" + id + " is not found"));
    }

    @Override
    public List<Group> getAll() {
        return groupDao.getAll();
    }

    @Override
    public void delete(Long id) {
        groupDao.delete(id);
    }
}
