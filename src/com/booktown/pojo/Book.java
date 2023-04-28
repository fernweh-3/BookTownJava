package com.booktown.pojo;

public class Book {
    private Integer id;
    private String name;
    private Double price;
    private String author;
    private String printer;
    private String print_date;
    private Integer volume_sold;
    private Integer volume_stock;
    private String cover;
    private String category;

    public Book(Integer id, String name, Double price, String author, String printer, String print_time, Integer volume_sold, Integer volume_stock, String cover, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.printer = printer;
        this.print_date = print_time;
        this.volume_sold = volume_sold;
        this.volume_stock = volume_stock;
        this.cover = cover;
        this.category = category;
    }

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getVolume_sold() {
        return volume_sold;
    }

    public void setVolume_sold(Integer volume_sold) {
        this.volume_sold = volume_sold;
    }

    public Integer getVolume_stock() {
        return volume_stock;
    }

    public void setVolume_stock(Integer volume_stock) {
        this.volume_stock = volume_stock;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getPrint_date() {
        return print_date;
    }

    public void setPrint_date(String print_time) {
        this.print_date = print_time;
    }
}
