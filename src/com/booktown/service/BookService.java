package com.booktown.service;

import com.booktown.dao.daoImpl.BookDao;
import com.booktown.pojo.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookService {

    BookDao bookDao = new BookDao();

    public HashMap<String, Integer> getPageInfo(int PAGE_VOLUME) {
        HashMap<String, Integer> info = new HashMap<>();
        int bookTotal = bookDao.queryBookTotal();
        int pageTotal = bookTotal / PAGE_VOLUME + 1;
        info.put("bookTotal", bookTotal);
        info.put("pageTotal", pageTotal);
        return info;
    }

    public HashMap<String, Integer> getPageInfo(int PAGE_VOLUME, String category) {
        HashMap<String, Integer> info = new HashMap<>();
        int bookTotal = bookDao.queryBookTotal(category);
        int pageTotal = bookTotal / PAGE_VOLUME + 1;
        info.put("bookTotal", bookTotal);
        info.put("pageTotal", pageTotal);
        return info;
    }

    public List<Book> getBookListByPageNo(int pageNo, int PAGE_VOLUME) throws SQLException {
        int start = (pageNo - 1) * PAGE_VOLUME;
        return bookDao.queryBookList(start, PAGE_VOLUME);
    }

    public List<Book> getBookListByIds(String ids) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String[] bookIdList = ids.split(",");
        for (String s : bookIdList) {
            int bookId = Integer.parseInt(s);
            Book book = getBookInfo(bookId);
            bookList.add(book);
        }
        return bookList;
    }

    public Book getBookInfo(int id) {
        return bookDao.queryBookById(id);
    }

    public List<Book> getBookListByCategory(int pageNo, String category, int PAGE_VOLUME) throws SQLException {
        int start = (pageNo - 1) * PAGE_VOLUME;
        if (category.equals("全部")) {
            return getBookListByPageNo(start, PAGE_VOLUME);
        }
        return bookDao.queryBookList(category, start, PAGE_VOLUME);
    }
}
