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
    public EntryResponseDto updateEntryDetails(UUID entryId, EntryUpdateDto updateDto) throws IOException {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Entry not found with id: " + entryId));

        updateCommonFields(entry, updateDto);

        if (entry instanceof StraightExpense  straightEntry) {
            if (!(updateDto instanceof StraightUpdateDto)) {
                throw new IllegalArgumentException("DTO type mismatch for StraightExpense");
            }

            handleStraightExpenseUpdate(straightEntry, (StraightUpdateDto) updateDto);
            entryRepository.save(straightEntry);
            return EntryMapper.mapToStraightResponseDto(straightEntry);
        } else if (entry instanceof InstallmentExpense  installmentEntry) {
            if (!(updateDto instanceof InstallmentUpdateDto)) {
                throw new IllegalArgumentException("DTO type mismatch for InstallmentExpense");
            }
            handleInstallmentExpenseUpdate(installmentEntry, (InstallmentUpdateDto) updateDto);
            entryRepository.save(installmentEntry);
            return EntryMapper.mapToInstallmentResponseDto(installmentEntry);
        }

        throw new IllegalArgumentException("Unknown Entry Type");
    }

    private void updateCommonFields(Entry entry, EntryUpdateDto updateDto) throws IOException {
        if (updateDto.getEntryName() != null) entry.setEntryName(updateDto.getEntryName());
        if (updateDto.getDescription() != null) entry.setDescription(updateDto.getDescription());
        if (updateDto.getNotes() != null) entry.setNotes(updateDto.getNotes());

        if (updateDto.getLenderId() != null) {
            Person lender = personRepository.findById(updateDto.getLenderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + updateDto.getLenderId()));
            entry.setPersonLender(lender);
        }

        if (updateDto.getImageFiles() != null && !updateDto.getImageFiles().isEmpty()) {
            for (var file : entry.getImageProofFiles()) {
                imageProofService.deleteImageFile(file.getId());
            }
            entry.getImageProofFiles().clear();

            for (var file : updateDto.getImageFiles()) {
                entry.getImageProofFiles().add(imageProofService.saveImageFile(entry, file));
            }

        }
    }

    private void handleStraightExpenseUpdate(StraightExpense entry, StraightUpdateDto updateDto) {
        if (updateDto.getPersonBorrowedId() != null) {
            Person personBorrower = personRepository.findById(updateDto.getPersonBorrowedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + updateDto.getPersonBorrowedId()));
            entry.setPersonBorrower(personBorrower);
        }
    }

    private void handleInstallmentExpenseUpdate(InstallmentExpense entry, InstallmentUpdateDto updateDto) {
        if (updateDto.getPersonBorrowedId() != null) {
            Person personBorrower = personRepository.findById(updateDto.getPersonBorrowedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + updateDto.getPersonBorrowedId()));
            entry.setPersonBorrower(personBorrower);
        }

        //TODO: Decide if start date is also included in editable
    }

    @Override
    public StraightResponseDto createStraightExpense(StraightCreateDto seCreateDto) throws IOException {

        StraightExpense straightExpense = EntryMapper.mapToStraightExpense(seCreateDto);

        Person lender = personRepository.findById(seCreateDto.getLenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Lender not found"));

        straightExpense.setLenderName(lender.getFirstName() + " " + lender.getLastName());
        straightExpense.setPersonLender(lender);

        Person borrower = personRepository.findById(seCreateDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        straightExpense.setBorrowerName(borrower.getFirstName() + " " + borrower.getLastName());
        straightExpense.setPersonBorrower(borrower);
        StraightExpense savedExpense = entryRepository.save(straightExpense);

        if(seCreateDto.getImageFiles() != null && !seCreateDto.getImageFiles().isEmpty()) {
            for (var file : seCreateDto.getImageFiles()) {
                ImageProof proof = imageProofService.saveImageFile(savedExpense, file);
                savedExpense.getImageProofFiles().add(proof);
            }

            savedExpense = entryRepository.save(savedExpense);
        }

        //TODO: Create logic for referenceID
        //straightExpense.setReferenceId(referenceId);

        return EntryMapper.mapToStraightResponseDto(savedExpense);
    }

    //TODO: Delete this after checking if updateEntryDetails is working successfully
    @Override
    public StraightResponseDto updateStraightExpense(UUID entryID, StraightCreateDto seUpdatedDto) throws IOException {
        StraightExpense se = (StraightExpense) entryRepository.findById(entryID)
                .orElseThrow(() -> new ResourceNotFoundException("Straight Expense does not exist with id: " + entryID));

        se.setEntryName(seUpdatedDto.getEntryName());
        se.setDescription(seUpdatedDto.getDescription());
        se.setDateBorrowed(seUpdatedDto.getDateBorrowed());
        se.setDateFullyPaid(seUpdatedDto.getDateFullyPaid());

        Person lender = personRepository.findById(seUpdatedDto.getLenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Lender not found"));
        se.setLenderName(lender.getFirstName() + " " + lender.getLastName());

        se.setAmountBorrowed(seUpdatedDto.getAmountBorrowed());
        se.setAmountRemaining(seUpdatedDto.getAmountRemaining());
        se.setStatus(seUpdatedDto.getStatus());
        se.setNotes(seUpdatedDto.getNotes());

        //TODO: Create logic for updating referenceID

        for (var file : se.getImageProofFiles()) {
            imageProofService.deleteImageFile(file.getId());
        }

        if(seUpdatedDto.getImageFiles() != null && !seUpdatedDto.getImageFiles().isEmpty()) {

            for (var file : seUpdatedDto.getImageFiles()) {
                    ImageProof proof = imageProofService.saveImageFile(se, file);
                    se.getImageProofFiles().add(proof);

            }
        } else {
            se.getImageProofFiles().clear();
        }

        Person borrower = personRepository.findById(seUpdatedDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        se.setBorrowerName(borrower.getFirstName() + " " + borrower.getLastName());
        se.setPersonBorrower(borrower);

        StraightExpense savedExpense = entryRepository.save(se);

        return EntryMapper.mapToStraightResponseDto(savedExpense);
    }

    @Override
    public void deleteEntry(UUID entryID) throws IOException {
        Entry entry = entryRepository.findById(entryID)
                .orElseThrow(() -> new ResourceNotFoundException("Expense does not exist with id: " + entryID));

        for (var file : entry.getImageProofFiles()) {
            imageProofService.deleteImageFile(file.getId());
        }

        entryRepository.delete(entry);
    }

    @Override
    public InstallmentResponseDto createInstallmentExpense(InstallmentCreateDto installmentCreateDto) throws IOException {
        InstallmentExpense installmentExpense = EntryMapper.mapToInstallmentExpense(installmentCreateDto);

        Person borrower = personRepository.findById(installmentCreateDto.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        installmentExpense.setBorrowerName(borrower.getFirstName() + " " + borrower.getLastName());
        installmentExpense.setPersonBorrower(borrower);
        InstallmentExpense savedExpense = entryRepository.save(installmentExpense);

        if(installmentCreateDto.getImageFiles() != null && !installmentCreateDto.getImageFiles().isEmpty()) {
            for (var file : installmentCreateDto.getImageFiles()) {
                ImageProof proof = imageProofService.saveImageFile(savedExpense, file);
                savedExpense.getImageProofFiles().add(proof);
            }

            savedExpense = entryRepository.save(savedExpense);
        }

        return EntryMapper.mapToInstallmentResponseDto(savedExpense);
    }
}
