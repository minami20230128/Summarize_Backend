package com.example.project.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.entity.RelatedBook;

public interface RelatedBookRepository extends JpaRepository<RelatedBook, Integer> {
    List<RelatedBook> findByBookId(Long bookId);
}
