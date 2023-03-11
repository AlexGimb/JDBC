import java.sql.*;
import java.util.List;

public class Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/skypro";
    private static final String USER = "postgres";
    private static final String PASSWORD = "74217351";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            int employeeId = 15;
            String query = "SELECT e.first_name, e.last_name, e.gender, e.age, c.city_name " +
                    "FROM employee e " +
                    "JOIN city c ON e.city_id = c.city_id " +
                    "WHERE e.id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String cityName = resultSet.getString("city_name");
                System.out.printf("Имя: %s" +
                        "\nФамилия: %s " +
                        "\nПол: %s " +
                        "\nВозраст: %s" +
                        "\nГород: %s " +
                        "\n", firstName, lastName, gender, age, cityName);
            } else {
                System.out.println("Сотрудник не найден!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных или выполнения SQL запроса: " + e.getMessage());
        }
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);
            // Создание(добавление) сущности Employee в таблицу
            Employee tolya = new Employee(15, "Анатолий", "Позер", "man", 21, new City(1, "Москва"));
            employeeDAO.create(tolya);
            // Получение конкретного объекта Employee по id
            System.out.println(employeeDAO.getById(15));
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
            // Получение списка всех объектов Employee из базы
            List<Employee> employees = employeeDAO.getAll();
            for (Employee emp : employees) {
                System.out.println(emp);
                System.out.println("----------------------------");
            }
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
            // Изменение конкретного объекта Employee в базе по id
            tolya = new Employee(27, "Анатолий", "Позер", "man", 25, new City(1, "Москва"));
            employeeDAO.update(15, tolya);
            // Получение конкретного объекта Employee по id
            System.out.println(employeeDAO.getById(15));
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
            employeeDAO.delete(25);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных или выполнения SQL запроса: " + e.getMessage());
        }
    }
}
