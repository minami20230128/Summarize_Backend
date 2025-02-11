package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Book;
import com.example.project.entity.Chapter;
import com.example.project.repository.BookRepository;

import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;
    private List<Book> cachedBooks;  // メモリ上でデータを保持

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void loadBooksOnStartup() {
        this.cachedBooks = bookRepository.findAll();  // アプリ起動時にDBから読み込む
    }

    // キャッシュに新しい本を追加
    public void addBookToCache(Book book) {
        cachedBooks.add(book);

        for(var b : cachedBooks)
        {
            System.out.println(b);
        }   
    }

    // DBにアクセスせずにメモリ上のデータを検索
    public Optional<Book> findById(Long id) {
        return cachedBooks.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
}
