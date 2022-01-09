package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class CourseService {

    private final CourseDao courseDao;

    @Autowired
    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Course create(Course course) {
        return courseDao.create(course);
    }

    public Course getById(Long id) {
        return courseDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Course with id=" + id + " is not found"));
    }

    public List<Course> getAll() {
        return courseDao.getAll();
    }

    public void delete(Long id) {
        courseDao.delete(id);
    }
}
