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
import java.util.UUID;

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

    @Override
    public StraightResponseDto getStraightExpenseByID(UUID entryID) throws IOException {
        StraightExpense straightExpense = (StraightExpense) entryRepository.findById(entryID)
                .orElseThrow(() -> new ResourceNotFoundException("Straight Expense does not exist with id: " + entryID));

        return EntryMapper.mapToStraightExpenseDto(straightExpense);
    }

    @Override
    public StraightResponseDto updateStraightExpense(UUID entryID, StraightCreateDto seUpdatedDto) throws IOException {
        StraightExpense se = (StraightExpense) entryRepository.findById(entryID)
                .orElseThrow(() -> new ResourceNotFoundException("Straight Expense does not exist with id: " + entryID));

        se.setEntryName(seUpdatedDto.getEntryName());
        se.setDescription(seUpdatedDto.getDescription());
        se.setDateBorrowed(seUpdatedDto.getDateBorrowed());
        se.setDateFullyPaid(seUpdatedDto.getDateFullyPaid());
        se.setBorrowerName(seUpdatedDto.getBorrowerName());
        se.setLenderName(seUpdatedDto.getLenderName());
        se.setAmountBorrowed(seUpdatedDto.getAmountBorrowed());
        se.setAmountRemaining(seUpdatedDto.getAmountRemaining());
        se.setStatus(seUpdatedDto.getStatus());
        se.setNotes(seUpdatedDto.getNotes());

        //TODO: Create logic for updating referenceID

        if (seUpdatedDto.getImageFile() != null && !seUpdatedDto.getImageFile().isEmpty()) {

            ImageProof oldImage = se.getImageProof();
            if (oldImage != null) {
                imageProofService.deleteImageFile(oldImage.getId());
            }

            ImageProof newImage = imageProofService.saveImageFile(seUpdatedDto.getImageFile());
            se.setImageProof(newImage);
        }

        Person borrower = personRepository.findById(seUpdatedDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        se.setPersonBorrower(borrower);

        StraightExpense savedExpense = entryRepository.save(se);

        return EntryMapper.mapToStraightExpenseDto(savedExpense);
    }

    @Override
    public void deleteStraightExpense(UUID entryID) throws IOException {
        StraightExpense se = (StraightExpense) entryRepository.findById(entryID)
                .orElseThrow(() -> new ResourceNotFoundException("Straight Expense does not exist with id: " + entryID));

        entryRepository.delete(se);
    }
}
