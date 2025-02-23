package com.example.project.controller;

import com.example.project.entity.Book;
import com.example.project.entity.Chapter;
import com.example.project.repository.BookRepository;
import com.example.project.repository.ChapterRepository;
import com.example.project.service.BookService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    private final BookService bookService;

    @Autowired
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
        System.out.println(chapter.getContent());
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

        // 章を更新するエンドポイント
        @PutMapping("/chapters/{id}")
        public ResponseEntity<Map<String, Object>> updateChapter(@PathVariable("id") Long id, @RequestBody Chapter updatedChapter) {
            Chapter existingChapter = chapterRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Chapter not found"));
    
            // 更新された内容を適用
            existingChapter.setChapterTitle(updatedChapter.getTitle());
            existingChapter.setContent(updatedChapter.getContent());
    
            // もしbookが更新された場合（別途送られてくる場合）、bookの更新
            if (updatedChapter.getBook() != null) {
                Book book = bookRepository.findById(updatedChapter.getBook().getId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                existingChapter.setBook(book);
            }
    
            // 保存して更新を反映
            Chapter savedChapter = chapterRepository.save(existingChapter);
    
            // 更新された章情報を返す
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedChapter.getId());
            response.put("title", savedChapter.getTitle());
            response.put("content", savedChapter.getContent());
    
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    @GetMapping(value = "/books/details", produces = "application/json;charset=UTF-8")
    public List<Book> getAllBooksWithChapters() {
        var books = bookRepository.findAll();

        for(var book : books)
        {
            List<Chapter> chapters = chapterRepository.findByBookId(book.getId());
            for(var chapter : chapters)
            {
                System.out.println(chapter.getTitle());
            }
            book.setChapters(chapters);
        }

        return books;
    }


    // すべての章を取得するエンドポイント
    @GetMapping("/chapters")
    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }
}
