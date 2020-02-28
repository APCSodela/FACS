package com.pup.cea.facs.tests.dynamicInput;

import java.util.ArrayList;
import java.util.List;

public class BooksCreationDto {
    private List<Book> books =new ArrayList<Book>();
 
 
    public void addBook(Book book) {
        this.books.add(book);
    }

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}