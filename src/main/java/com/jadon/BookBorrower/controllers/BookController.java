package com.jadon.BookBorrower.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jadon.BookBorrower.models.Book;
import com.jadon.BookBorrower.models.LoginUser;
import com.jadon.BookBorrower.models.User;
import com.jadon.BookBorrower.services.BookService;
import com.jadon.BookBorrower.services.UserService;

@Controller
public class BookController {
    
    @Autowired
    private UserService userServ;
    
    @Autowired
    private BookService bookService;
    
    
    
    
//	show all books
    //I ADDED THE book AND MODEL ATTRIBUTE TO THE SUCCESS ROUTE AND IT WORKS
    @RequestMapping("/home")
    public String showAll(HttpSession s, Model model) {
        List<Book> books = bookService.allBooks();
        model.addAttribute("books", books);
  	  	Long userID = (Long) s.getAttribute("user_id");
  	  	model.addAttribute("user", userID);
  	  	System.out.println(books);
        return "/login/landing.jsp";
    }
    
    
    //FIND ONE AND RETURN TO JSP THROUGH ADD ATTRIBUTE
    @GetMapping("/books/{bookId}")
    public String getOneBook(@PathVariable("bookId") Long id, HttpSession s, Model model) {
    	//FINDING
    	Book thisBook = bookService.findBook(id); 
    	Long userId = (Long) s.getAttribute("user_id");
    	User thisUser = userServ.findOne(userId);
    	List<Book> borrowedBooks = bookService.allBooks();
  	  	model.addAttribute("borrowedBooks", borrowedBooks);
    	//PASS TO JSP
    	model.addAttribute("thisBook", thisBook);
    	model.addAttribute("user", thisBook.getUser());
    	model.addAttribute("userCon", thisUser.getUserName());
    	model.addAttribute("userEdit", thisBook.getUser().getUserName());
    	return "/books/show.jsp";
    }
    
//  render create page
   @RequestMapping("/books/new")
   public String newBook(@ModelAttribute("book") Book book, Model model) {
       model.addAttribute("books", bookService.allBooks());
	   return "/books/new.jsp";
   }
   
   
//   create method
   @RequestMapping(value="/books", method=RequestMethod.POST)
   //Include session to find which user is logged in
   public String create(@Valid @ModelAttribute("book") Book book, BindingResult result, HttpSession s) {
	   //reaching in session to get users id
	   Long userID = (Long) s.getAttribute("user_id");
	   //finding the user by Id
	   User thisUser = userServ.findOne(userID);
	   System.out.println(userID);
	   if (result.hasErrors()) {
           return "/books/new.jsp";
       } else {
    	   //session the new book to have a user_id of the logged users ID
    	   book.setUser(thisUser);
    	   //crate the book!
           bookService.createBook(book);
           return "redirect:/home";
       }
   }
   
   //--------EDIT BOOK---------//
   @RequestMapping("/books/{Id}/edit")
   public String edit(@PathVariable("Id") Long id, Model model) {
       Book book = bookService.findBook(id);
       model.addAttribute("book", book);
       return "/books/edit.jsp";
   }
   
   @RequestMapping(value="/books/{id}", method=RequestMethod.PUT)
   public String update(@Valid @ModelAttribute("book") Book book, HttpSession s, BindingResult result) {
	   //Finding logged user//
	   //Not having this created a bug because when the book was being updated it wasnt assigned a user.
	   Long userID = (Long) s.getAttribute("user_id");
       User thisUser = userServ.findOne(userID);
	   //finding the user by Id
	   if (result.hasErrors()) {
           return "/books/edit.jsp";
       } else {
    	   book.setUser(thisUser);
           bookService.updateBook(book);
           return "redirect:/home";
       }
   }
   
   
   //BORROW THE BOOK//
   @GetMapping("/books/{id}/borrow")
   public String borrow(@PathVariable("id") Long id, HttpSession s) {
	   Long userID = (Long) s.getAttribute("user_id");
	   Book borrowedBook = bookService.findBook(id);
	   User thisUser = userServ.findOne(userID);
	   borrowedBook.setBorrower(thisUser);
	   bookService.updateBook(borrowedBook);
	   return "redirect:/home";
   }
   
   //returning the book
   @GetMapping("/books/{id}/return")
   public String returnBook(@PathVariable("id") Long id, HttpSession s) {
	   //gets book
	   Book borrowedBook = bookService.findBook(id);
	   //sets the borrower on the book table to null;
	   borrowedBook.setBorrower(null);
	   bookService.updateBook(borrowedBook);
	   return "redirect:/home";
   }
   
   //DELETE BOOK
   @DeleteMapping("/books/{id}")
   public String DeleteMe(@PathVariable("id") Long id) {
//   	delete the book
   	bookService.deleteBook(id);
   	return "redirect:/home";
   }
   
   
   
   
   //USER CONTROLS
   @GetMapping("/")
   public String index(Model model) {
       model.addAttribute("newUser", new User());
       model.addAttribute("newLogin", new LoginUser());
       return "/login/index.jsp";
   }
   
   //CREATE USER
   @PostMapping("/register")
   public String register(@Valid @ModelAttribute("newUser") User newUser, 
           BindingResult result, Model model, HttpSession session) {
       userServ.register(newUser, result);
       if(result.hasErrors()) {
           model.addAttribute("newLogin", new LoginUser());
           return "login/index.jsp";
       }
       session.setAttribute("user_id", newUser.getId());
       return "redirect:/home";
   }
   
   //SUCCESS
	@GetMapping("/home")
	public String home(HttpSession s, Model model) {
//		retrieve what is in session
		Long userID = (Long) s.getAttribute("user_id");
		List<Book> books = bookService.allBooks();
		//I had to turn userID into a string to compare it to borrower.id
		String s1 = Long.toString(userID);
//		Book thisBook = bookService.findBook(id); 
//		route guard
//		check if userID is or is not null
		if (userID == null) {
			return "redirect:/";
		} else {			
			User thisUser = userServ.findOne(userID);
			model.addAttribute("name", thisUser.getUserName());
			model.addAttribute("books", books);
			//adding the stringified user ID here
			model.addAttribute("user", s1);
			System.out.println(userID);
			return "login/landing.jsp";
		}
	}
   
   //LOGIN USER
   @PostMapping("/login")
   public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
           BindingResult result, Model model, HttpSession session) {
       User user = userServ.login(newLogin, result);
       if(result.hasErrors()) {
           model.addAttribute("newUser", new User());
           return "login/index.jsp";
       }
       session.setAttribute("user_id", user.getId());
       return "redirect:/home";
   }
   
   //LOGOUT
	@GetMapping("/logout")
	public String logout(HttpSession s) {
		s.invalidate();
		return "redirect:/";
	}
   
}
