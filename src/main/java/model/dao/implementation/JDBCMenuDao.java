package model.dao.implementation;

import model.dao.MenuDao;
import model.dao.implementation.query.LoginQuery;
import model.dao.implementation.query.MenuQuery;
import model.dao.mapper.CategoryMapper;
import model.dao.mapper.MenuMapper;
import model.entity.Category;
import model.entity.Menu;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCMenuDao implements MenuDao {

    private static final Logger LOGGER = Logger.getLogger(JDBCMenuDao.class);

    private Connection connection;
    private MenuMapper menuMapper = new MenuMapper();

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

            return menuMapper.extractListFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Menu> findByCategory(Category category) {
        List<Menu> menus = new ArrayList<>();
        Map<Integer,Menu> menuMap = new HashMap<>();

        int idCategory = category.getId();
        try (PreparedStatement ps = connection.prepareStatement(MenuQuery.SELECT_BY_CATEGORY)){
            ps.setInt(1, idCategory);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Menu menu = menuMapper.makeUnique(
                        menuMap,
                        menuMapper.extractFromResultSet(resultSet)
                );
                menu.setCategory(category);
                menus.add(menu);
            }
            return menus;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Menu entity) {

    }

    @Override
    public void delete(int id) {

    }

}
