package com.example.wizerassessment.mapper.mappers;

import com.example.wizerassessment.mapper.DTOS.BookDTO;
import com.example.wizerassessment.model.Books;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO bookToBookDTO(Books book);

    Books bookDTOToBook(BookDTO bookDTO);
}
