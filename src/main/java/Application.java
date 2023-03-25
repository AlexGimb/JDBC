import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.List;

public class Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/skypro";
    private static final String USER = "postgres";
    private static final String PASSWORD = "74217351";

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        EmployeeDAO employeeDAO = new EmployeeDAOImpl(sessionFactory);

        // Создание нового сотрудника
        Employee vanek = new Employee("Иван", "Иванов","men" , 25, 1);
        employeeDAO.create(vanek);
        System.out.println(vanek.toString());
        System.out.println("====================================================================================");

        // Получение и вывод в консоль данных об одном из сотрудников
        System.out.println(vanek);

        System.out.println("====================================================================================");

        // Получение и вывод в консоль всех сотрудников
        List<Employee> employees = employeeDAO.getAll();
        for (Employee e : employees) {
            System.out.println(e);
        }

        System.out.println("====================================================================================");

        // Изменение данных сотрудника
        vanek.setAge(30);
        employeeDAO.update(vanek);
        System.out.println(vanek.toString());

        System.out.println("====================================================================================");

        // Удаление сотрудника
        employeeDAO.delete(vanek);
        System.out.println(vanek.toString());
    }
}
