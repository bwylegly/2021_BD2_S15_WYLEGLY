package pl.polsl.s15.library.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.s15.library.dtos.BookDTO;
import pl.polsl.s15.library.service.BookService;

@AllArgsConstructor
@Validated
@RequestMapping("/library/api/books")
@RestController
public class BookController {

    private BookService bookService;

    @Operation(summary = "Get full info about books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All information about the books was returned"),
            @ApiResponse(code = 400, message = "Cannot return all info about books")
    })
    @GetMapping(value = "/full")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookDTO> findAllFull(@PageableDefault Pageable pageable) {
        return bookService.findAllFull(pageable);
    }


}
