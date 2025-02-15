package com.example.project.repository;

import com.example.project.entity.Chapter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByBookId(Long bookId); // bookId でフィルタするメソッドを定義
}
