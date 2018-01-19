package model.dao.implementation;

import model.dao.CategoryDao;
import model.dao.implementation.query.CategoryQuery;
import model.dao.implementation.query.MenuQuery;
import model.dao.mapper.CategoryMapper;
import model.dao.mapper.EntityMapper;
import model.dao.mapper.MenuMapper;
import model.entity.Category;
import org.apache.log4j.Logger;

import javax.swing.text.html.parser.Entity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCategoryDao implements CategoryDao{
    private static final Logger LOGGER = Logger.getLogger(CategoryDao.class);

    private Connection connection;
    private EntityMapper<Category> categoryMapper = new CategoryMapper();

    public JDBCCategoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Category entity) {
        return false;
    }

    @Override
    public Optional<Category> findById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CategoryQuery.SELECT_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(categoryMapper.extractFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> findAll() {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(CategoryQuery.SELECT_ALL);
            return categoryMapper.extractListFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public void delete(int id) {

    }
}
