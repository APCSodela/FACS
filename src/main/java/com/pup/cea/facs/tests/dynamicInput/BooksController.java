package com.pup.cea.facs.tests.dynamicInput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/all")
	public String showAll(Model model) {
	    model.addAttribute("books", bookService.findAll());
	    return "test/allBooks";
	}
	@GetMapping("/create")
	public String showCreateForm(Model model) {
	    BooksCreationDto booksForm = new BooksCreationDto();
	 
		/* for (int i = 1; i <= 10; i++) { */
	        booksForm.addBook(new Book());
		/* } */
	 
	    model.addAttribute("form", booksForm);
	    return "test/createBooksForm";
	}
	@PostMapping("/save")
	public String saveBooks(@ModelAttribute BooksCreationDto form, Model model) {
	    bookService.saveAll(form.getBooks());
	 
	    model.addAttribute("books", bookService.findAll());
	    return "redirect:/books/all";
	}

}
