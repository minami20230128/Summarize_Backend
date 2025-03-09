package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.project.entity.RelatedBook;
import com.example.project.service.RelatedBookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/related-books")
public class RelatedBookController {

    @Autowired
    private RelatedBookService relatedBookService;

    // 全ての関連書籍を取得
    @GetMapping
    public List<RelatedBook> getAllRelatedBooks() {
        return relatedBookService.getAllRelatedBooks();
    }

    // 特定の関連書籍を取得
    @GetMapping("/{id}")
    public ResponseEntity<RelatedBook> getRelatedBookById(@PathVariable Integer id) {
        Optional<RelatedBook> relatedBook = relatedBookService.getRelatedBookById(id);
        return relatedBook.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 新しい関連書籍を作成
    @PostMapping
    public ResponseEntity<RelatedBook> createRelatedBook(@RequestBody RelatedBook relatedBook) {
        RelatedBook createdRelatedBook = relatedBookService.createRelatedBook(relatedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRelatedBook);
    }

    // 関連書籍を更新
    @PutMapping("/{id}")
    public ResponseEntity<RelatedBook> updateRelatedBook(@PathVariable Integer id, @RequestBody RelatedBook relatedBook) {
        RelatedBook updatedRelatedBook = relatedBookService.updateRelatedBook(id, relatedBook);
        return updatedRelatedBook != null ? ResponseEntity.ok(updatedRelatedBook) :
                ResponseEntity.notFound().build();
    }

    // 関連書籍を削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelatedBook(@PathVariable Integer id) {
        relatedBookService.deleteRelatedBook(id);
        return ResponseEntity.noContent().build();
    }

    // 同じbookIdを持つ関連書籍を取得
    @GetMapping(value = "/by-book-id/{bookId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<RelatedBook>> getRelatedBooksBybookId(@PathVariable Integer bookId) {
        List<RelatedBook> relatedBooks = relatedBookService.getRelatedBooksByBookId(bookId);
        for(var relatedBook : relatedBooks)
        {
            System.out.println(relatedBook.getTitle());
        }
        return ResponseEntity.ok(relatedBooks);
    }
}
