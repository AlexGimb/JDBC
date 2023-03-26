import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Создание объектов City
        City city1 = new City("Москва");
        City city2 = new City("Санкт-Петербург");

        // Создание объектов Employee с привязкой к City
        Employee employee1 = new Employee("Иван", "Иванов", "М", 25, city1);
        Employee employee2 = new Employee("Петр", "Петров", "М", 30, city1);
        Employee employee3 = new Employee("Мария", "Сидорова", "Ж", 28, city2);

        // Создание DAO для сущностей City и Employee
        CityDAO cityDAO = new CityDAOImpl(sessionFactory);
        EmployeeDAO employeeDAO = new EmployeeDAOImpl(sessionFactory);

//        // Создание записей в БД
//        cityDAO.save(city1);
//        cityDAO.save(city2);
//        employeeDAO.create(employee1);
//        employeeDAO.create(employee2);
//        employeeDAO.create(employee3);

        // Вывод всех сотрудников в консоль
        List<Employee> employees = employeeDAO.getAll();
        System.out.println("Список всех сотрудников:");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
        System.out.println("==================================================================================");

        // Получение сотрудника по id и его изменение
        Employee employeeToUpdate = employeeDAO.getById(38);
        System.out.println("Сотрудник до изменения:");
        System.out.println(employeeToUpdate.toString());
        employeeToUpdate.setAge(26);
        employeeDAO.update(employeeToUpdate);
        System.out.println("Сотрудник после изменения:");
        System.out.println(employeeDAO.getById(38).toString());
        System.out.println("==================================================================================");

        // Удаление сотрудника
        System.out.println("Количество сотрудников до удаления: " + employeeDAO.getAll().size());
        employeeDAO.delete(employee1);
        System.out.println("Количество сотрудников после удаления: " + employeeDAO.getAll().size());
        System.out.println("==================================================================================");

        // Получение города по id и его изменение
        City cityToUpdate = cityDAO.getById(1);
        System.out.println("Город до изменения:");
        System.out.println(cityToUpdate.toString());
        cityToUpdate.setName("Новый город");
        cityDAO.update(cityToUpdate);
        System.out.println("Город после изменения:");
        System.out.println(cityDAO.getById(1).toString());
        System.out.println("==================================================================================");

        // Удаление города
        System.out.println("Количество городов до удаления: " + cityDAO.getAll().size());
        cityDAO.delete(city1);
        System.out.println("Количество городов после удаления: " + cityDAO.getAll().size());

        sessionFactory.close();
    }
}
