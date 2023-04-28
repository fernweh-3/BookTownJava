package com.booktown.dao.daoImpl;

import com.booktown.pojo.Book;

import java.sql.SQLException;
import java.util.List;

public class BookDao extends BasicDao<Book> {

    public Book queryBookById(int id) {
        String sql = "select * from book where id = ?";
        return queryItem(sql, Book.class, id);
    }

    public List<Book> queryBookList(int start, int size) throws SQLException {
        String sql = "select * from book order by id DESC limit ?,?";
        return queryList(sql, Book.class, start, size);
    }

    public List<Book> queryBookList(String category, int start, int size) throws SQLException {
        String sql = "select * from book where category=? order by id DESC limit ?,?";
        return queryList(sql, Book.class, category, start, size);
    }


    public int queryBookTotal(String category) {
        String sql = "select count(*) from book where category=?";
        return ((Long) queryField(sql, category)).intValue();

    }

    public int queryBookTotal() {
        String sql = "select count(*) from book";
        return ((Long) queryField(sql)).intValue();
    }
}