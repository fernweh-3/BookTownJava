package com.booktown.dao.daoImpl;

import com.booktown.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDao<T> {
    QueryRunner runner = new QueryRunner();
    Connection connection = null;

    public int dml(String sql, Object... params) throws SQLException {
        try {
            connection = DBUtils.getConnection();
            int i = runner.update(connection, sql, params);
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                DBUtils.close(null, null, connection);
            }
        }
    }


    public List<T> queryList(String sql, Class<T> tClass, Object... params) throws SQLException {
        try {
            connection = DBUtils.getConnection();
            List<T> list = runner.query(connection, sql, new BeanListHandler<T>(tClass), params);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                DBUtils.close(null, null, connection);
            }
        }
    }

    public T queryItem(String sql, Class<T> tClass, Object... params) {
        try {
            connection = DBUtils.getConnection();
            T t = runner.query(connection, sql, new BeanHandler<T>(tClass), params);
            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                DBUtils.close(null, null, connection);
            }
        }
    }

    public Object queryField(String sql, Object... params) {
        try {
            connection = DBUtils.getConnection();
            return runner.query(connection, sql, new ScalarHandler<>(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                DBUtils.close(null, null, connection);
            }
        }
    }

}
