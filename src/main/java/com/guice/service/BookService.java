package com.guice.service;

import java.util.List;

import com.guice.model.Book;

public interface BookService {
	public void addBook(Book book);
	public void updateBook(int bookId,Book book);
	public void deleteBook(int bookId);
	public List<Book> allBooks();
	public Book getBook(int bookId);

	public void hello();
}
