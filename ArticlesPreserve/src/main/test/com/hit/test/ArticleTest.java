//package com.hit.test;
//
//import java.awt.print.Book;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runners.MethodSorters;
//
//import com.hit.model.Article;
//import com.hit.model.Author;
//import com.hit.repository.BookRepository;
//import com.hit.reuse.exceptions.CustomException;
//import com.hit.utils.BookshopUtils;
//
//
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class ArticleTest {
//
//	Article article;
//
//	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//	private final PrintStream originalOut = System.out;
//
//	@Rule
//	public ExpectedException thrown = ExpectedException.none();
//
//
//	@Before
//	public void before() {
//		article = BookRepository.createBookDatabase();
//
//		System.setOut(new PrintStream(outContent));
//
//	}
//
//	@Test
//	public void testA_SearchBookByBookName() {
//
//		Assert.assertEquals(1, article.getListOfBooksByName("Java").size());
//	}
//
//
//	@Test
//	public void testB_SearchBookByAuthorName() {
//
//		Assert.assertEquals(1, article.getListOfBooksByAuthor("Bert").size());
//	}
//
//	@Test
//	public void testC_SearchBookByPriceRange() {
//
//		Assert.assertEquals(3, article.getListOfBooksByPriceRange(150, 500).size());
//	}
//
//	@Test
//	public void testD_AddingNewBookToBookShop() throws CustomException {
//
//		int noOfBooksBeforeAdding = article.getBooks().size();
//
//		String bookIdToAdd = BookshopUtils.getUUID();
//		Set<Author> al = new HashSet<>();
//		Author aa = new Author(BookshopUtils.getUUID(), "J.K Rowling");
//		al.add(aa);
//		Book b = new Book(bookIdToAdd, "Harry Potter", al, 500.0, 2, 0);
//		article.addBook(b, 2, 2);
//
//		int noOfBooksAfterAdding = article.getBooks().size();
//
//		Assert.assertEquals(noOfBooksBeforeAdding + 1, noOfBooksAfterAdding);
//		Assert.assertEquals("Harry Potter",
//				article.getBooks().stream().filter(b1 -> b1.getBookId().equals(bookIdToAdd))
//						.collect(Collectors.toList()).get(0).getBookName());
//
//	}
//
//	@Test
//	public void testE_AddingNewBookToBookShopWhenBookAlreadyExists_shouldonlyincreasequantity()
//			throws CustomException {
//
//		int noOfBooksBeforeAdding = article.getBooks().size();
//
//		int quantityBeforerAdd = article.getBooks().stream()
//				.filter(b1 -> b1.getBookName().equals("Java Programming"))
//				.collect(Collectors.toList()).get(0).getNoOfCopies();
//
//		String bookIdToAdd = BookshopUtils.getUUID();
//		Set<Author> al = new HashSet<>();
//		Author aa = new Author(BookshopUtils.getUUID(), "Bert Bates");
//		al.add(aa);
//		Book b = new Book(bookIdToAdd, "Java Programming", al, 500.0, 2, 0);
//		article.addBook(b, 2, 2);
//
//		int noOfBooksAfterAdding = article.getBooks().size();
//
//		// As book is already present it should only increase quantity
//		Assert.assertEquals(noOfBooksBeforeAdding, noOfBooksAfterAdding);
//
//		int quantityAfterAdd = article.getBooks().stream()
//				.filter(b1 -> b1.getBookName().equals("Java Programming"))
//				.collect(Collectors.toList()).get(0).getNoOfCopies();
//
//		Assert.assertEquals(quantityBeforerAdd + 2, quantityAfterAdd);
//
//
//	}
//
//	@Test
//	public void testF_AddingNewBookToBookShopWhenBookNameAlreadyExistsButDifferentAuthor_shouldAddasNewBook()
//			throws CustomException {
//
//		int noOfBooksBeforeAdding = article.getBooks().size();
//
//		String bookIdToAdd = BookshopUtils.getUUID();
//		Set<Author> al = new HashSet<>();
//		Author aa = new Author(BookshopUtils.getUUID(), "John Davis");
//		al.add(aa);
//		Book b = new Book(bookIdToAdd, "Java Programming", al, 500.0, 2, 0);
//		article.addBook(b, 2, 2);
//
//		int noOfBooksAfterAdding = article.getBooks().size();
//
//		// As book with book name is already present but with different author then it should add as
//		// new book
//		Assert.assertEquals(noOfBooksBeforeAdding + 1, noOfBooksAfterAdding);
//
//
//	}
//
//
//	@Test
//	public void testG_AddingNewBookWithZeroQuantity() throws CustomException {
//
//		thrown.expect(CustomException.class);
//		thrown.expectMessage("Quantity should be greater than 0.");
//
//		String bookIdToAdd = BookshopUtils.getUUID();
//		Set<Author> al = new HashSet<>();
//		Author aa = new Author(BookshopUtils.getUUID(), "Bert Bates");
//		al.add(aa);
//		Book b = new Book(bookIdToAdd, "Java Programming", al, 500.0, 0, 0);
//		article.addBook(b, 0, 0);
//
//	}
//
//	@Test
//	public void testH_AddingNewBookWithBookIdNull() throws CustomException {
//
//		thrown.expect(CustomException.class);
//		thrown.expectMessage("Book Id cannot be null.");
//
//		Set<Author> al = new HashSet<>();
//		Author aa = new Author(BookshopUtils.getUUID(), "Bert Bates");
//		al.add(aa);
//		Book b = new Book(null, "Java Programming", al, 500.0, 0, 0);
//		article.addBook(b, 0, 0);
//
//	}
//
//	@Test
//	public void testI_AddingNewBookWithBookNull() throws CustomException {
//
//		thrown.expect(CustomException.class);
//		thrown.expectMessage("Book cannot be null.");
//
//		article.addBook(null, 1, 1);
//
//	}
//
//	@Test
//	public void testJ_IncreaseQuantityOfBooks() {
//
//		int quantityBefore =
//				article.getBooks().stream().filter(b1 -> b1.getBookId().equals("1234"))
//						.collect(Collectors.toList()).get(0).getNoOfCopies();
//
//		article.increaseQuantityOfBook("1234", 1);
//
//		int quantityAfter = article.getBooks().stream().filter(b1 -> b1.getBookId().equals("1234"))
//				.collect(Collectors.toList()).get(0).getNoOfCopies();
//
//		Assert.assertEquals(quantityBefore + 1, quantityAfter);
//
//	}
//
//	@Test
//	public void testK_RemovingOfBookMethod() throws CustomException {
//
//		// First Add a new Book
//		int noOfBooksBeforeAdding = article.getBooks().size();
//
//		String bookIdToAdd = BookshopUtils.getUUID();
//		Set<Author> al = new HashSet<>();
//		Author aa = new Author(BookshopUtils.getUUID(), "William Shakespeare");
//		al.add(aa);
//		Book b = new Book(bookIdToAdd, "Hamlet", al, 500.0, 2, 0);
//		article.getClass(b, 2, 0);
//
//		int noOfBooksAfterAdding = article.getBooks().size();
//
//		Assert.assertEquals(noOfBooksBeforeAdding + 1, noOfBooksAfterAdding);
//		Assert.assertEquals("Hamlet",
//				article.getBooks().stream().filter(b1 -> b1.getBookId().equals(bookIdToAdd))
//						.collect(Collectors.toList()).get(0).getBookName());
//
//
