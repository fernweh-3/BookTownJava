package com.booktown.servlet;

import com.booktown.pojo.Book;
import com.booktown.pojo.Result;
import com.booktown.service.BookService;
import com.booktown.service.MessageService;
import com.booktown.utils.GetRequestJsonUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class BookServlet extends BaseServlet {
    BookService bookService = new BookService();
    Gson gson = new Gson();

    protected void getBookList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter respWriter = resp.getWriter();
        MessageService<List<Book>> messageService = new MessageService<>();
        HashMap<String, String> map = GetRequestJsonUtils.getRequestJson(req);
        Result<List<Book>> response;
        String pageNoObj = map.get("pageNo");
        String bookIdSet = map.get("bookIdSet");
        String PAGE_VOLUME = map.get("pageVolume");
        if (pageNoObj != null && pageNoObj.length() != 0) {
            String pageNoStr = map.get("pageNo");
            String category = map.get("category");
            int pageNo = Integer.parseInt(pageNoStr);
            if ("全部".equals(category) || category == null) {
                try {
                    List<Book> bookList = bookService.getBookListByPageNo(pageNo, Integer.parseInt(PAGE_VOLUME));
                    if (bookList.size() > 0) {
                        response = messageService.createResponse(200, "success", bookList);
                    } else {
                        response = messageService.createResponse(404, "Not Found", bookList);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    List<Book> bookList = bookService.getBookListByCategory(pageNo, category, Integer.parseInt(PAGE_VOLUME));
                    if (bookList.size() > 0) {
                        response = messageService.createResponse(200, "success", bookList);
                    } else {
                        response = messageService.createResponse(404, "Not Found", bookList);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (bookIdSet != null && bookIdSet.length() != 0) {
            try {
                List<Book> bookList = bookService.getBookListByIds(bookIdSet);
                response = messageService.createResponse(200, "success", bookList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            response = messageService.createResponse(406, "Not Acceptable 参数错误", null);
        }
        String str = gson.toJson(response);
        respWriter.write(str);
    }

    protected void getBookInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MessageService<Book> messageService = new MessageService<>();
        HashMap<String, String> map = GetRequestJsonUtils.getRequestJson(req);
        String bookIdStr = map.get("bookId");
        Result<Book> response;
        int bookId = Integer.parseInt(bookIdStr);
        try {
            Book book = bookService.getBookInfo(bookId);
            response = messageService.createResponse(200, "success", book);
            String str = gson.toJson(response);
            PrintWriter respWriter = resp.getWriter();
            respWriter.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void getPageInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> map = GetRequestJsonUtils.getRequestJson(req);
        int page_volume = Integer.parseInt(map.get("page_volume"));
        HashMap<String, Integer> pageInfo;
        if (map.containsKey("category")) {
            pageInfo = bookService.getPageInfo(page_volume, map.get("category"));
        } else {
            pageInfo = bookService.getPageInfo(page_volume);
        }
        MessageService<HashMap<String, Integer>> messageService = new MessageService<>();
        Result<HashMap<String, Integer>> response = messageService.createResponse(200, "success", pageInfo);
        String str = gson.toJson(response);
        PrintWriter respWriter = resp.getWriter();
        respWriter.write(str);
    }
}
