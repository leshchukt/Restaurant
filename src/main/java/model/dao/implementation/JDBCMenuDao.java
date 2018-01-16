package model.dao.implementation;

import model.dao.MenuDao;
import model.dao.implementation.query.MenuQuery;
import model.dao.mapper.CategoryMapper;
import model.dao.mapper.MenuMapper;
import model.entity.Category;
import model.entity.Menu;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JDBCMenuDao implements MenuDao {

    private static final Logger LOGGER = Logger.getLogger(JDBCMenuDao.class);

    private Connection connection;

    public JDBCMenuDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Menu entity) {
        return false;
    }

    @Override
    public Optional<Menu> findById(int id) {
        return null;
    }

    @Override
    public List<Menu> findAll() {

        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(MenuQuery.SELECT_ALL);
            return getFromRS(resultSet);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("problem in findAll");
        }

    }

    @Override
    public void update(Menu entity) {

    }

    @Override
    public void delete(int id) {

    }

    public List<Menu> getFromRS(ResultSet resultSet) throws SQLException{
        List<Menu> meals = new ArrayList<>();

        Map<Integer,Menu> menuMap = new HashMap<>();
        Map<Integer,Category> categoryMap = new HashMap<>();

        MenuMapper menuMapper = new MenuMapper();
        CategoryMapper categoryMapper = new CategoryMapper();

        while ( resultSet.next() ) {
            Category category = categoryMapper.makeUnique(
                    categoryMap,
                    categoryMapper.extractFromResultSet(resultSet)
            );
            Menu menu = menuMapper.makeUnique(
                    menuMap,
                    menuMapper.extractFromResultSet(resultSet)
            );
            menu.setCategory(category);
            meals.add(menu);
        }
        return meals;
    }
}
