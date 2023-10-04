package be.julienbastin.conditionalresponsebackend.repositories;

import be.julienbastin.conditionalresponsebackend.models.Book;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface BookRepository extends R2dbcRepository<Book, UUID> {
}
