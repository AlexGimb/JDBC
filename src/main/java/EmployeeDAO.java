import java.util.List;

public interface EmployeeDAO {
    void create(Employee employee);
    Employee getById(int id);
    List<Employee> getAll();
    void update(int id, Employee updatedEmployee);
    void delete(int id);
}