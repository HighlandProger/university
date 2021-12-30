package ua.com.foxminded;

import ua.com.foxminded.dao.SqlRunner;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.util.DataSourceFactory;

public class UniversityApplication {

    public static void main(String[] args) {

        SqlRunner sqlRunner = new SqlRunner(DataSourceFactory.getInstance().initDataSource());

        sqlRunner.createTables();

        StudentDao studentDao = new StudentDao(DataSourceFactory.getInstance().initDataSource());

        System.out.println(studentDao.create(new Student("John", "Newman", 24)));

    }
}
