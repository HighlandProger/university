package ua.com.foxminded;

import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.dao.postgresql.PostgreSqlStudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.util.Template;

public class UniversityApplication {

    public static void main(String[] args) {

        SqlRunner sqlRunner = new SqlRunner(Template.getJdbcTemplate());

        sqlRunner.createTables();

        PostgreSqlStudentDao studentDao = new PostgreSqlStudentDao(Template.getJdbcTemplate());

        System.out.println(studentDao.create(new Student("John", "Newman", 24)));

    }
}
