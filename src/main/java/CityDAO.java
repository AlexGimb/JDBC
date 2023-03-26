import java.util.List;

public interface CityDAO {
    void save(City city);
    City getById(int id);
    List<City> getAll();
    void update(City city);
    void delete(City city);
}
