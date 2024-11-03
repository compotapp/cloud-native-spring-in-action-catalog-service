package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(//это служебный класс, который позволяет тестировать конечные точки веб-сайта без загрузки сервера, такого как Tomcat. Такой тест, естественно, легче
        BookController.class//Идентифицирует тестовый класс, ориентированный на компоненты Spring MVC, явно нацеленный на класс
)
class BookControllerMvcTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")//Если вы используете IntelliJ IDEA, вы можете получить предупреждение о том, что MockMvc не может быть автоматически подключено. Не волнуйся. Это ложный результат. Вы можете избавиться от предупреждения, аннотировав поле с помощью
    private MockMvc mockMvc;//BookController Utility для тестирования веб-слоя в макетной среде.

    @MockBean
    private BookService bookService;//Добавляет макет BookService в контекст приложения Spring. Определяет

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);//ожидаемое поведение макетного компонента BookService. MockMvc используется
        mockMvc
                .perform(get("/books/" + isbn))//для выполнения HTTP-запроса GET и проверки результата. Ожидает, что ответ
                .andExpect(status().isNotFound());//будет иметь статус «404 не найден».
    }

}