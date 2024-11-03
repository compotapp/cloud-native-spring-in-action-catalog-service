package com.polarbookshop.catalogservice.web;

import javax.validation.Valid;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController//Стереотипная аннотация, обозначающая класс как компонент Spring и источник обработчиков для конечных точек REST.
@RequestMapping("books")//Идентифицирует URI сопоставления корневого пути, для которого класс предоставляет обработчики («/books»).
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping//Сопоставляет HTTP-запросы GET с конкретным методом обработчика.
    public Iterable<Book> get() {
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}")//Переменная шаблона URI, добавленная к URI сопоставления корневого пути ("/books/{isbn}").
    public Book getByIsbn(@PathVariable String isbn) {//@PathVariable привязывает параметр метода к переменной шаблона URI ({isbn})
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping//Сопоставляет HTTP-запросы POST с конкретным методом обработчика
    @ResponseStatus(HttpStatus.CREATED)//Возвращает статус 201, если книга создана успешно.
    public Book post(@Valid @RequestBody Book book) {//@RequestBody привязывает параметр метода к телу веб-запроса.
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}")//Сопоставляет HTTP-запросы DELETE с конкретным методом обработчика.
    @ResponseStatus(HttpStatus.NO_CONTENT)//Возвращает статус 204, если книга успешно удалена
    public void delete(@PathVariable String isbn) {
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}")//Сопоставляет запросы HTTP PUT с конкретным методом обработчика.
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
        return bookService.editBookDetails(isbn, book);
    }

}