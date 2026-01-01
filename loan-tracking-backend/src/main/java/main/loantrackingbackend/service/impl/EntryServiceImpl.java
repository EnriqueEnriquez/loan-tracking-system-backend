package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.entity.*;
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
    public EntryResponseDto getEntryById(UUID entryId) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Entry not found"));

        if (entry instanceof StraightExpense) {
            return EntryMapper.mapToStraightResponseDto((StraightExpense) entry);
        } else if (entry instanceof InstallmentExpense) {
            return EntryMapper.mapToInstallmentResponseDto((InstallmentExpense) entry);
        } else {
            throw new ResourceNotFoundException("Entry not found");
        }

        //TODO: add if statement of instance of Group Expense
    }

    @Override
    public StraightResponseDto createStraightExpense(StraightCreateDto seCreateDto) throws IOException {

        StraightExpense straightExpense = EntryMapper.mapToStraightExpense(seCreateDto);

        Person borrower = personRepository.findById(seCreateDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        straightExpense.setPersonBorrower(borrower);
        StraightExpense savedExpense = entryRepository.save(straightExpense);

        if (seCreateDto.getImageFile() != null && !seCreateDto.getImageFile().isEmpty()) {
            ImageProof imageProof = imageProofService.saveImageFile(savedExpense, seCreateDto.getImageFile());
            savedExpense.setImageProof(imageProof);
            savedExpense = entryRepository.save(savedExpense);
        }

        //TODO: Create logic for referenceID
        //straightExpense.setReferenceId(referenceId);

        return EntryMapper.mapToStraightResponseDto(savedExpense);
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

            ImageProof newImage = imageProofService.saveImageFile(se, seUpdatedDto.getImageFile());
            se.setImageProof(newImage);
        } else {
            se.setImageProof(null);
        }

        Person borrower = personRepository.findById(seUpdatedDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        se.setPersonBorrower(borrower);

        StraightExpense savedExpense = entryRepository.save(se);

        return EntryMapper.mapToStraightResponseDto(savedExpense);
    }

    @Override
    public void deleteEntry(UUID entryID) throws IOException {
        Entry entry = entryRepository.findById(entryID)
                .orElseThrow(() -> new ResourceNotFoundException("Expense does not exist with id: " + entryID));

        entryRepository.delete(entry);
    }

    @Override
    public InstallmentResponseDto createInstallmentExpense(InstallmentCreateDto installmentCreateDto) throws IOException {
        InstallmentExpense installmentExpense = EntryMapper.mapToInstallmentExpense(installmentCreateDto);

        Person borrower = personRepository.findById(installmentCreateDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        installmentExpense.setPersonBorrower(borrower);
        InstallmentExpense savedExpense = entryRepository.save(installmentExpense);

        if (installmentCreateDto.getImageFile() != null && !installmentCreateDto.getImageFile().isEmpty()) {
            ImageProof imageProof = imageProofService.saveImageFile(savedExpense, installmentCreateDto.getImageFile());
            savedExpense.setImageProof(imageProof);

            savedExpense = entryRepository.save(savedExpense);
        }

        return EntryMapper.mapToInstallmentResponseDto(savedExpense);
    }
}
