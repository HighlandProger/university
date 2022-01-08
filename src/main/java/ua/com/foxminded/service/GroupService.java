package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.exception.ServiceException;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public Group create(Group group) {
        return groupDao.create(group);
    }

    public Group getById(Long id) {

        Optional<Group> group = groupDao.getById(id);
        if (!group.isPresent()) {
            throw new ServiceException("Group with id=" + id + " is not found");
        }
        return group.get();
    }

    public List<Group> getAll() {
        return groupDao.getAll();
    }

    public void delete(Long id) {
        groupDao.delete(id);
    }
}
