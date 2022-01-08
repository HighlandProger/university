package ua.com.foxminded.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.ServiceException;

import java.util.List;
import java.util.Optional;

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

        Optional<Course> course = courseDao.getById(id);
        if (!course.isPresent()) {
            throw new ServiceException("Course with id=" + id + " is not found");
        }
        return course.get();
    }

    public List<Course> getAll() {
        return courseDao.getAll();
    }

    public void delete(Long id) {
        courseDao.delete(id);
    }
}
