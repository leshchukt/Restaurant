package model.dao.implementation;

import model.dao.MenuDao;
import model.dao.implementation.query.LoginQuery;
import model.dao.implementation.query.MenuQuery;
import model.dao.mapper.CategoryMapper;
import model.dao.mapper.ColumnLabel;
import model.dao.mapper.EntityMapper;
import model.dao.mapper.MenuMapper;
import model.entity.Category;
import model.entity.Menu;
import model.entity.Order;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCMenuDao implements MenuDao {

    private static final Logger LOGGER = Logger.getLogger(JDBCMenuDao.class);

    private EntityMapper<Menu> menuMapper = new MenuMapper();
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
        try (PreparedStatement ps = connection.prepareStatement(MenuQuery.SELECT_BY_ID)){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Category category = new CategoryMapper().extractFromResultSet(resultSet);
                Menu menu = menuMapper.extractFromResultSet(resultSet);
                menu.setCategory(category);
                return Optional.of(menu);
            } else return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Menu> findAll() {

        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(MenuQuery.SELECT_ALL);

            return menuMapper.extractListFromResultSet(resultSet);
        } catch (SQLException e) {
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
    public List<Menu> getMenuFromOrder(Order order) {
        try (PreparedStatement ps = connection.prepareStatement(MenuQuery.SELECT_BY_ORDER)){
            ps.setInt(1, order.getId());
            ResultSet resultSet = ps.executeQuery();

            List<Menu> meals = new ArrayList<>();

            Map<Integer,Menu> menuMap = new HashMap<>();
            Map<Integer,Category> categoryMap = new HashMap<>();

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
                menu.setAmount(resultSet.getInt(ColumnLabel.AMOUNT));
                meals.add(menu);
            }
            order.setMenu(meals);
            return meals;

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Menu entity) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }

}
