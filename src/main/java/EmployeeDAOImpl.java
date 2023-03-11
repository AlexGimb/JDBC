import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private final Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(Employee employee){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO employee (first_name, last_name, gender, age, city_id) " +
                            "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCity().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных или выполнения SQL запроса: " + e.getMessage());
        }
    }

    @Override
    public Employee getById(int id) {
        Employee search = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM employee JOIN city ON employee.city_id = city.city_id WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                City city = new City(resultSet.getInt("city_id"), resultSet.getString("city_name"));
                search = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("gender"),
                        resultSet.getInt("age"),
                        city);
            }
            return search;
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных или выполнения SQL запроса: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM employee JOIN city ON employee.city_id = city.city_id");
            while (resultSet.next()) {
                City city = new City(resultSet.getInt("city_id"), resultSet.getString("city_name"));
                Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("gender"),
                        resultSet.getInt("age"),
                        city);
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных или выполнения SQL запроса: " + e.getMessage());
        }
        return employees;
    }

    @Override
    public void update(int id, Employee employee) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE employee SET first_name=?, last_name=?, gender=?, age=?, city_id=? " +
                            "WHERE id=?");
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCity().getId());
            statement.setInt(6, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных или выполнения SQL запроса: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных или выполнения SQL запроса: " + e.getMessage());
        }
    }
}
