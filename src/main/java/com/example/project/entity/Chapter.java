package com.example.project.entity;

import jakarta.persistence.*;

@Entity
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chapterTitle;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Chapter(String chapterTitle, String content){
        this.chapterTitle = chapterTitle;
        this.content = content;
    }

    // ゲッターとセッター
    public void setBook(Book book){
        this.book = book;
    }
}
