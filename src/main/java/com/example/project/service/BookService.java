package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Book;
import com.example.project.entity.Chapter;
import com.example.project.repository.BookRepository;

import java.util.Arrays;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void addDummyData() {
        var chapter1 = new Chapter("chapter1", "aaaa");
        var chapter2 = new Chapter("chapter2", "bbbb");

        var book1 = new Book("title", Arrays.asList(chapter1));
        var book2 = new Book("Jane Smith", Arrays.asList(chapter2));

        bookRepository.save(book1);
        bookRepository.save(book2);

        System.out.println("ダミーデータがデータベースに登録されました。");
    }
}
