package com.booktown.dao.daoImpl;

import com.booktown.pojo.User;

import java.sql.SQLException;

public class UserDao extends BasicDao<User> {
    /**
     * @param username
     * @return 0 - no such user, 1 - found 1 user, -1 - other cases
     */
    public int queryByUsername(String username) {
        String sql = "select count(id) from user where username = ?";
        Long count = (Long) queryField(sql, username);
        int id = count.intValue();
        if (id == 0 || id == 1) {
            return id;
        } else return -1;
    }

    /**
     * @param username
     * @param password
     * @return 1- wrong password;2-success
     */
    public int queryByUsernameAndPassword(String username, String password) {
        String sql = "select * from user where username = ? and password = ?";
        User user = queryItem(sql, User.class, username, password);
        if (null == user) {
            return 1;
        } else {
            return 2;
        }
    }

    public int register(String username, String password, String email) throws SQLException {
        String sql = "insert into user values(null,?,?,?)";
        return dml(sql,username,password,email);
    }
}
