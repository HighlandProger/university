package ua.com.foxminded;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.dao.CrudDao;

public class ApplicationService {

    public static final String STUDENT_DAO_BEAN = "studentDao";
    public static final String TEACHER_DAO_BEAN = "teacherDao";
    public static final String COURSE_DAO_BEAN = "courseDao";
    public static final String DEPARTMENT_DAO_BEAN = "departmentDao";
    public static final String GROUP_DAO_BEAN = "groupDao";
    public static final String LESSON_DAO_BEAN = "lessonDao";
    private final AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(SpringConfig.class);

    public CrudDao getCrudDao(String beanName) {
        return context.getBean(beanName, CrudDao.class);
    }

}
