package com.polarbookshop.catalogservice.domain;

import org.springframework.stereotype.Service;

@Service//Стереотипная аннотация, которая отмечает класс как службу, управляемую Spring.
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;//BookRepository предоставляется посредством автоматического подключения конструктора.
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));//При попытке просмотреть несуществующую книгу выдается специальное исключение.
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {//При добавлении одной и той же книги в каталог несколько раз выдается специальное исключение.
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(//При редактировании книги можно обновить все поля «Книга», кроме кода ISBN, поскольку это идентификатор объекта.
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price());
                    return bookRepository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));//При изменении сведений о книге, которой еще нет в каталоге, создайте новую книгу.

    }
}