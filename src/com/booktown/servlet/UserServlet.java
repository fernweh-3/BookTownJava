package com.booktown.servlet;

import com.booktown.pojo.Result;
import com.booktown.pojo.User;
import com.booktown.service.MessageService;
import com.booktown.service.UserService;
import com.booktown.utils.GetRequestJsonUtils;
import com.google.gson.Gson;
import com.wf.captcha.utils.CaptchaUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

public class UserServlet extends BaseServlet {
    UserService userService = new UserService();
    Gson gson = new Gson();
    MessageService<User> messageService = new MessageService<>();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //HashMap<String, String> map = (HashMap<String, String>) req.getAttribute("json");
        HashMap<String, String> map = GetRequestJsonUtils.getRequestJson(req);
        String username = map.get("username");
        String password = map.get("password");
        Result<User> response = userService.login(username, password);
        PrintWriter out = resp.getWriter();
        if (200 == response.getCode()) {
            HttpSession session = req.getSession();
            session.setAttribute("login", username);
            String sessionId = session.getId();
            Cookie cookieSessionId = new Cookie("JSESSIONID", sessionId);
            Cookie cookieUsername = new Cookie("username", username);
            cookieSessionId.setPath("/");
            cookieUsername.setPath("/");
            resp.addCookie(cookieSessionId);
            resp.addCookie(cookieUsername);
        }
        String json = gson.toJson(response);
        out.println(json);
        out.flush();
        out.close();
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        Result<User> response = new Result<>();
        PrintWriter out = resp.getWriter();
        Cookie[] cookies = req.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            cookies[i].setMaxAge(0);
            cookies[i].setPath("/");
            resp.addCookie(cookies[i]);
        }
        response = messageService.createResponse(200, "logout success", null);
        HttpSession session = req.getSession();
        session.removeAttribute("login");
        session.invalidate();

        String json = gson.toJson(response);
        out.println(json);
        out.flush();
        out.close();
        resp.flushBuffer();
    }


    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        HashMap<String, String> map = GetRequestJsonUtils.getRequestJson(req);
        String username = map.get("username");
        String password = map.get("password");
        String email = map.get("email");
        String captcha = map.get("captcha");
        PrintWriter out = resp.getWriter();
        Result<User> response;
        if (!CaptchaUtil.ver(captcha, req)) {
            System.out.println("检测验证码不正确");
            CaptchaUtil.clear(req);  // 清除session中的验证码
            response = messageService.error("验证码不正确");
        } else {
            System.out.println("检测验证码正确");
            response = userService.register(username, password, email);
        }
        System.out.println(response);
        String json = gson.toJson(response);
        out.println(json);
        out.flush();
        out.close();
    }
}
