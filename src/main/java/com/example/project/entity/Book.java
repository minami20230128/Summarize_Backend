package com.example.project.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters;

    public Book(String title, List<Chapter> chapters) {
        this.title = title;
        this.chapters = chapters;

        for (Chapter chapter : chapters) {
            chapter.setBook(this);  // Chapterのbookを自動的に設定
        }
    }

    // ゲッターとセッター
}
