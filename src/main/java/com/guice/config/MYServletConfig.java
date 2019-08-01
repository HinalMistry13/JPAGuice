package com.guice.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.guice.controller.BookController;
import com.guice.service.BookService;
import com.guice.service.BookServiceImpl;

public class MYServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletModule() {

			@Override
			protected void configureServlets() {
				install(new JpaPersistModule("Crud"));
				bind(BookController.class);
				bind(BookService.class).to(BookServiceImpl.class);
				serve("/list").with(BookController.class);
				serve("/edit").with(BookController.class);
				serve("/delete").with(BookController.class);
				serve("/new").with(BookController.class);
				serve("/insert").with(BookController.class);
				serve("/update").with(BookController.class);
				filter("/*").through(PersistFilter.class);
			}
		});
	}

}
