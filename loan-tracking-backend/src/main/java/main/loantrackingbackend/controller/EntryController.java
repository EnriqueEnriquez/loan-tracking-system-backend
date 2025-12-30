package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.EntryResponseDto;
import main.loantrackingbackend.dto.StraightCreateDto;
import main.loantrackingbackend.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/entry")
public class EntryController {
    private EntryService entryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntryResponseDto> createStraightExpense(StraightCreateDto seCreateDto) throws IOException {
        EntryResponseDto entryResponseDto = entryService.createStraightExpense(seCreateDto);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.CREATED);
    }




}
