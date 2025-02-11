package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Chapter> chapters = new ArrayList<>();  // ✅ nullを防ぐ

    // ✅ JPA用のデフォルトコンストラクタ（必須）
    protected Book() {}

    // ✅ @JsonCreatorを追加し、デシリアライズ時の `null` を防ぐ
    @JsonCreator
    public Book(@JsonProperty("title") String title, 
                @JsonProperty("chapters") List<Chapter> chapters) {
        this.title = title;
        this.chapters = (chapters != null) ? chapters : new ArrayList<>();  // ✅ nullチェック
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = (chapters != null) ? chapters : new ArrayList<>();  // ✅ nullチェック
    }

    public void addChapter(Chapter chapter)
    {
        this.chapters.add(chapter);
    }
}
