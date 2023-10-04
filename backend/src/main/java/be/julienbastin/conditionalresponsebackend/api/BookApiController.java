package be.julienbastin.conditionalresponsebackend.api;

import be.julienbastin.conditionalresponsebackend.generated.api.BookApi;
import be.julienbastin.conditionalresponsebackend.generated.dto.BookDto;
import be.julienbastin.conditionalresponsebackend.mappers.BookMapper;
import be.julienbastin.conditionalresponsebackend.services.BookService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class BookApiController implements BookApi {

    private final BookService bookService;
    private final BookMapper bookMapper;


    public BookApiController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Override
    public Mono<Flux<BookDto>> booksGet(ServerWebExchange exchange) {
        return Mono.just(bookService.getAllBooks().map(bookMapper::toDto));
    }

    @Override
    public Mono<BookDto> booksIdGet(UUID id, ServerWebExchange exchange) {
        return bookService.getBookById(id).map(bookMapper::toDto);
    }
}
