package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.exception.EntityNotFoundException;
import ua.com.foxminded.model.Course;

import java.util.List;

@Service
public class CourseService implements CrudService<Course> {

    private final CourseDao courseDao;

    @Autowired
    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public Course create(Course course) {
        return courseDao.create(course);
    }

    @Override
    public Course getById(Long id) {
        return courseDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Course with id=" + id + " is not found"));
    }

    @Override
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @Override
    public void delete(Long id) {
        courseDao.delete(id);
    }

    @Override
    public void update(Long id, Course course) {
        courseDao.update(id, course);
    }
}
