package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String chapterTitle;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnore // `book` フィールドを JSON でシリアライズしない
    private Book book;

    @Column(name = "book_id", insertable = false, updatable = false)
    private long bookId;

    public Chapter()
    {
    }

    public Chapter(String chapterTitle, String content){
        this.chapterTitle = chapterTitle;
        this.content = content;
    }

    // ゲッターとセッター
    public Long getId(){
        return this.id;
    }

    public String getTitle()
    {
        return this.chapterTitle;
    }

    public Long getBookId(){
        return this.bookId;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setChapterTitle(String title)
    {
        this.chapterTitle = title;
    }

    public void setBookId(long bookId)
    {
        this.bookId = bookId;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }
}
