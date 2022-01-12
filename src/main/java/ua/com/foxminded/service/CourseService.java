package ua.com.foxminded.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.List;

@Service
public class CourseService {

    private final Logger logger = LoggerFactory.getLogger(CourseService.class.getName());
    private final CourseDao courseDao;

    @Autowired
    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Course create(Course course) {

        logger.info("Call courseDao.create()");
        return courseDao.create(course);
    }

    public Course getById(Long id) {

        logger.info("Call courseDao.getById({})", id);
        return courseDao.getById(id).orElseThrow(() ->
            new EntityNotFoundException("Course with id=" + id + " is not found"));
    }

    public List<Course> getAll() {

        logger.info("Call courseDao.getAll()");
        return courseDao.getAll();
    }

    public void delete(Long id) {

        logger.info("Call courseDao.delete({})", id);
        courseDao.delete(id);
    }
}
