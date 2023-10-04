package be.julienbastin.conditionalresponsebackend.services;

import be.julienbastin.conditionalresponsebackend.models.Book;
import be.julienbastin.conditionalresponsebackend.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Mono<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

}
