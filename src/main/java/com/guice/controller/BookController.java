package com.guice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.guice.model.Book;
import com.guice.service.BookService;

@Singleton
public class BookController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private BookService bookService;

//	@Override
//	public void init() throws ServletException {
//		Injector injector = Guice.createInjector(new ServletConfig());
//		bookService = injector.getInstance(BookService.class);
//	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/new":
			showNewForm(req, resp);
			break;
		case "/insert":
			insertBook(req, resp);
			break;
		case "/delete":
			deleteBook(req, resp);
			break;
		case "/edit":
			showEditForm(req, resp);
			break;
		case "/update":
			updateBook(req, resp);
			break;
		default:
			listBook(req, resp);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void listBook(HttpServletRequest request, HttpServletResponse response) {
		List<Book> listBook = bookService.allBooks();
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("bookList.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("bookform.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = bookService.getBook(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("bookform.jsp");
		request.setAttribute("book", existingBook);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));
		Book newBook = new Book(title, author, price);
		bookService.addBook(newBook);
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));
		Book book = new Book(id, title, author, price);
		bookService.updateBook(id, book);
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		bookService.deleteBook(id);
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
