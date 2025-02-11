package com.example.project.controller;

import com.example.project.entity.Book;
import com.example.project.entity.Chapter;
import com.example.project.repository.BookRepository;
import com.example.project.repository.ChapterRepository;
import com.example.project.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    private final BookService bookService;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    // 書籍を追加するエンドポイント
    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);

        bookService.addBookToCache(savedBook); // キャッシュを更新

        // 保存された書籍の ID を含めて JSON を返す
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedBook.getId());
        response.put("title", savedBook.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // 書籍の一覧を取得するエンドポイント
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // 章を追加するエンドポイント
    @PostMapping("/chapters")
    public ResponseEntity<Map<String, Object>> addChapter(@RequestBody Chapter chapter) {
        System.out.println(chapter.getBookId());
        Book book = bookRepository.findById(chapter.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

        book.addChapter(chapter);
        chapter.setBook(book);
        var savedChapter = chapterRepository.save(chapter);
        // 保存された書籍の ID を含めて JSON を返す
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedChapter.getId());
        response.put("title", savedChapter.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //@Autowired
    //private BookService bookService;
    //@GetMapping("/add-dummy-data")
    //public void addDummyData()
    //{
    //    bookService.addDummyData();
    //    System.out.println("dummy data added.");
    //}
}
