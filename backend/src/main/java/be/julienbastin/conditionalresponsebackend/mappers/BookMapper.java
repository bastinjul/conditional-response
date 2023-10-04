package be.julienbastin.conditionalresponsebackend.mappers;

import be.julienbastin.conditionalresponsebackend.generated.dto.BookDto;
import be.julienbastin.conditionalresponsebackend.models.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    BookDto toDto(Book book);

    Book fromDto(BookDto bookDto);
}
