package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.StraightCreateDto;
import main.loantrackingbackend.dto.StraightResponseDto;
import main.loantrackingbackend.entity.ImageProof;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.entity.StraightExpense;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.EntryMapper;
import main.loantrackingbackend.repository.EntryRepository;
import main.loantrackingbackend.repository.PersonRepository;
import main.loantrackingbackend.service.EntryService;
import main.loantrackingbackend.service.ImageProofService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class EntryServiceImpl implements EntryService {

    private EntryRepository entryRepository;
    private PersonRepository personRepository;
    private ImageProofService imageProofService;

    @Override
    public StraightResponseDto createStraightExpense(StraightCreateDto seCreateDto) throws IOException {

        StraightExpense straightExpense = EntryMapper.mapToStraightExpense(seCreateDto);

        Person borrower = personRepository.findById(seCreateDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        ImageProof imageProof = imageProofService.saveImageFile(seCreateDto.getImageFile());

        //TODO: Create logic for referenceID
        //straightExpense.setReferenceId(referenceId);

        straightExpense.setImageProof(imageProof);
        straightExpense.setPersonBorrower(borrower);

        StraightExpense savedExpense = entryRepository.save(straightExpense);


        return EntryMapper.mapToStraightExpenseDto(savedExpense);
    }
}
