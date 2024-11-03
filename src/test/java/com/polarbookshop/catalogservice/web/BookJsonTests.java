package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest//Идентифицирует тестовый класс, ориентированный на сериализацию JSON.
class BookJsonTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JacksonTester<Book> json;//Служебный класс для подтверждения сериализации и десериализации JSON.

    @Test
    void testSerialize() throws Exception {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        var jsonContent = json.write(book);//Проверка синтаксического анализа Java в JSON с использованием формата JsonPath для навигации по объекту JSON. Определяет.
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90
                }
                """;//объект JSON с использованием функции текстового блока Java.
        assertThat(json.parse(content))//Проверяет синтаксический анализ JSON в Java
                .usingRecursiveComparison()
                .isEqualTo(new Book("1234567890", "Title", "Author", 9.90));
    }

}