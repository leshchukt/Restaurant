package model.dao.implementation;

import model.dao.MenuDao;
import model.dao.mapper.CategoryMapper;
import model.dao.mapper.MenuMapper;
import model.entity.Category;
import model.entity.Menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class JDBCMenuDao implements MenuDao {
    private Connection connection;

    public JDBCMenuDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(Menu entity) {

    }

    @Override
    public Optional<Menu> findById(int id) {
        return null;
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> meals = new ArrayList<>();

        Map<Integer,Menu> menuMap = new HashMap<>();
        Map<Integer,Category> categoryMap = new HashMap<>();

        MenuMapper menuMapper = new MenuMapper();
        CategoryMapper categoryMapper = new CategoryMapper();

        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery("SELECT * FROM menu JOIN category USING (idcategory)");
            while ( rs.next() ){
                Category category = categoryMapper.makeUnique(
                        categoryMap,
                        categoryMapper.extractFromResultSet(rs)
                );
                Menu menu = menuMapper.makeUnique(
                        menuMap,
                        menuMapper.extractFromResultSet(rs)
                );
                menu.setCategory(category);
                meals.add(menu);
            }
        } catch (Exception e) {
            throw new RuntimeException("problem in findAll");
        }
        return meals;
    }

    @Override
    public void update(Menu entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
