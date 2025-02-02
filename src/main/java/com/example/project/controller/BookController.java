package com.example.project.controller;

import com.example.project.entity.Book;
import com.example.project.entity.Chapter;
import com.example.project.repository.BookRepository;
import com.example.project.repository.ChapterRepository;
import com.example.project.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    // 書籍を追加するエンドポイント
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // 書籍の一覧を取得するエンドポイント
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // 章を追加するエンドポイント
    @PostMapping("/chapters")
    public Chapter addChapter(@RequestBody Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Autowired
    private BookService bookService;
    @GetMapping("/add-dummy-data")
    public void addDummyData()
    {
        bookService.addDummyData();
        System.out.println("dummy data added.");
    }
}
