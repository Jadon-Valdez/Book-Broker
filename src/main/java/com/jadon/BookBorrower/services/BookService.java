package com.jadon.BookBorrower.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jadon.BookBorrower.models.Book;
import com.jadon.BookBorrower.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepo;
	
//	CREATE
	public Book createBook(Book b) {
        return bookRepo.save(b);
    }
//	READ ALL
	public List<Book> allBooks() {
        return bookRepo.findAll();
    }
//	READ ONE
	public Book findBook(Long id) {
        Optional<Book> optionalBook = bookRepo.findById(id);
        if(optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            return null;
        }
	}
	
//	UPDATE entire book
	public Book updateBook(Book b) {
		return bookRepo.save(b);
	}
	
	
//	DELETE
	public void deleteBook(Long id) {
		bookRepo.deleteById(id);
	}
}
