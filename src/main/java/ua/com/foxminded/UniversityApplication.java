package ua.com.foxminded;

import ua.com.foxminded.dao.CrudDao;
import ua.com.foxminded.domain.Student;

public class UniversityApplication {

    public static void main(String[] args) {

        ApplicationService service = new ApplicationService();
        CrudDao dao = service.getCrudDao(ApplicationService.STUDENT_DAO_BEAN);

        dao.create(new Student("Jack", "Nicolson", 32));
    }
}
