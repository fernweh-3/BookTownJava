package com.booktown.service;

import com.booktown.dao.daoImpl.UserDao;
import com.booktown.pojo.Result;
import com.booktown.pojo.User;
import com.wf.captcha.utils.CaptchaUtil;

import java.sql.SQLException;

public class UserService {
    UserDao userDao = new UserDao();
    MessageService<User> messageService = new MessageService<>();

    /**
     * @param username
     * @param password
     * @return 0- no such user; 1- wrong password;2-success; -1 - other cases
     */
    public Result<User> login(String username, String password) {
        int i = userDao.queryByUsername(username);
        if (i == 0) {
            return messageService.error("该用户不存在");
        } else if (i == 1) {
            int j = userDao.queryByUsernameAndPassword(username, password);
            if (j == 1) {
                return messageService.error("密码错误");
            } else if (j == 2) {
                return messageService.success("登陆成功",null);
            }
        }
        return messageService.error("未知错误");
    }


    public Result<User> register(String username, String password, String email) throws SQLException {
        int i = userDao.queryByUsername(username);
        if (i == 0) {
            int j = userDao.register(username,password,email);
            return messageService.success("注册成功");
        } else if (i == 1) {
            return messageService.error("用户名已存在");
        }
        return messageService.error("未知错误");
    }


}

