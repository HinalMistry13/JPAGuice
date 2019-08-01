package com.guice.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.guice.model.Book;

public class BookServiceImpl implements BookService {

	@Inject
	Provider<EntityManager> entityManagerProvider;

//	public void connect() { 
//		entityManager = Persistence.createEntityManagerFactory("Crud").createEntityManager();
//		entityManager.getTransaction().begin();
//	}

	@Override
	@Transactional
	public void addBook(Book book) {
//		connect();
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.persist(book);
	}

	@Override
	@Transactional
	public void updateBook(int bookId, Book book) {
//		connect();
		EntityManager entityManager = entityManagerProvider.get();
		Book b = entityManager.find(Book.class, bookId);
		b.setAuthor(book.getAuthor());
		b.setTitle(book.getTitle());
		b.setPrice(book.getPrice());
	}

	@Override
	@Transactional
	public void deleteBook(int bookId) {
//		connect();
		EntityManager entityManager = entityManagerProvider.get();
		Book b = entityManager.find(Book.class, bookId);
		entityManager.remove(b);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> allBooks() {
//		connect();
		EntityManager entityManager = entityManagerProvider.get();
		Query query = entityManager.createQuery("select b from Book b");
		return (List<Book>) query.getResultList();
	}

	@Override
	public Book getBook(int bookId) {
//		connect();
		EntityManager entityManager = entityManagerProvider.get();
		Book book = entityManager.find(Book.class, bookId);
		return book;
	}

	@Override
	public void hello() {
		System.out.println("++++++++++++++++++++++");
	}

}
