package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.ClassRoomDao;
import ua.com.foxminded.exception.EntityNotFoundException;
import ua.com.foxminded.model.ClassRoom;

import java.util.List;

@Service
public class ClassRoomService implements CrudService<ClassRoom> {

    private final ClassRoomDao classRoomDao;

    @Autowired
    public ClassRoomService(ClassRoomDao classRoomDao) {
        this.classRoomDao = classRoomDao;
    }

    @Override
    public ClassRoom create(ClassRoom classRoom) {
        return classRoomDao.create(classRoom);
    }

    @Override
    public ClassRoom getById(Long id) {
        return classRoomDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Class room with id=" + id + " is not found"));
    }

    @Override
    public List<ClassRoom> getAll() {
        return classRoomDao.getAll();
    }

    @Override
    public void delete(Long id) {
        classRoomDao.delete(id);
    }

    @Override
    public void update(Long id, ClassRoom classRoom) {
        classRoomDao.update(id, classRoom);
    }
}
