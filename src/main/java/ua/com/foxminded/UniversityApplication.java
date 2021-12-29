package ua.com.foxminded;

import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.util.Template;

public class UniversityApplication {

    public static void main(String[] args) {

        SqlRunner sqlRunner = new SqlRunner(Template.getDataSource());

        sqlRunner.createTables();

        StudentDao studentDao = new StudentDao(Template.getDataSource());

        System.out.println(studentDao.create(new Student("John", "Newman", 24)));

    }
}
