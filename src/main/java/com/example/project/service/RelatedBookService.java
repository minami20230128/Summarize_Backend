package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.project.entity.RelatedBook;
import com.example.project.repository.RelatedBookRepository;

@Service
public class RelatedBookService {

    @Autowired
    private RelatedBookRepository relatedBookRepository;

    // 全ての関連書籍を取得
    public List<RelatedBook> getAllRelatedBooks() {
        return relatedBookRepository.findAll();
    }

    // ID に基づいて関連書籍を取得
    public Optional<RelatedBook> getRelatedBookById(Integer id) {
        return relatedBookRepository.findById(id);
    }

    // 新しい関連書籍を追加
    public RelatedBook createRelatedBook(RelatedBook relatedBook) {
        return relatedBookRepository.save(relatedBook);
    }

    // 既存の関連書籍を更新
    public RelatedBook updateRelatedBook(Integer id, RelatedBook relatedBook) {
        if (relatedBookRepository.existsById(id)) {
            relatedBook.setId(id);
            return relatedBookRepository.save(relatedBook);
        }
        return null;
    }

    // 関連書籍を削除
    public void deleteRelatedBook(Integer id) {
        relatedBookRepository.deleteById(id);
    }

    public List<RelatedBook> getRelatedBooksByBookId(Long bookId) {
        return relatedBookRepository.findByBookId(bookId);
    }
}
