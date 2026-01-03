package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.entity.*;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.EntryMapper;
import main.loantrackingbackend.repository.EntryRepository;
import main.loantrackingbackend.repository.PersonRepository;
import main.loantrackingbackend.service.EntryService;
import main.loantrackingbackend.service.ImageProofService;
import main.loantrackingbackend.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EntryServiceImpl implements EntryService {

    private EntryRepository entryRepository;
    private PersonRepository personRepository;
    private ImageProofService imageProofService;
    private PaymentService paymentService;

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

        if (entry instanceof StraightExpense straightEntry) {

            handleStraightExpenseUpdate(straightEntry, updateDto);
            entryRepository.save(straightEntry);
            return EntryMapper.mapToStraightResponseDto(straightEntry);

        } else if (entry instanceof InstallmentExpense  installmentEntry) {

            handleInstallmentExpenseUpdate(installmentEntry, updateDto);
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

        if (updateDto.getDeletedImageIds() != null && !updateDto.getDeletedImageIds().isEmpty()) {
            List<ImageProof> toRemove = new ArrayList<>();

            for (Long id : updateDto.getDeletedImageIds()) {
                entry.getImageProofFiles().stream()
                        .filter(img -> img.getId().equals(id))
                        .findFirst()
                        .ifPresent(toRemove::add);
            }

            for (ImageProof img : toRemove) {
                entry.getImageProofFiles().remove(img);
                imageProofService.deleteImageFile(img.getId());
            }
        }

        List<MultipartFile> newFiles = updateDto.getImageFiles();
        if (newFiles != null && !newFiles.isEmpty()) {

            List<ImageProof> newProofs = imageProofService.saveImageFilesList(entry, newFiles);
            entry.getImageProofFiles().addAll(newProofs);
        }
    }

    private void handleStraightExpenseUpdate(StraightExpense entry, EntryUpdateDto updateDto) {
        if (updateDto.getPersonBorrowedId() != null && !paymentService.getPaymentsByEntry(entry.getId()).isEmpty()) {
            Person personBorrower = personRepository.findById(updateDto.getPersonBorrowedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + updateDto.getPersonBorrowedId()));
            entry.setPersonBorrower(personBorrower);
        }
    }

    private void handleInstallmentExpenseUpdate(InstallmentExpense entry, EntryUpdateDto updateDto) {
        if (updateDto.getPersonBorrowedId() != null && !paymentService.getPaymentsByEntry(entry.getId()).isEmpty()) {
            Person personBorrower = personRepository.findById(updateDto.getPersonBorrowedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + updateDto.getPersonBorrowedId()));
            entry.setPersonBorrower(personBorrower);
        }

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

        List<ImageProof> imageProofs= imageProofService.saveImageFilesList(savedExpense, seCreateDto.getImageFiles());

        if (!imageProofs.isEmpty()) {
            savedExpense.getImageProofFiles().addAll(imageProofs);
        }

        savedExpense.setReferenceId(getReferenceId(savedExpense));
        savedExpense = entryRepository.save(savedExpense);

        return EntryMapper.mapToStraightResponseDto(savedExpense);
    }

    public static String getReferenceId(Entry entry) {
        String lender = entry.getPersonLender().getFirstName() + " " + entry.getPersonLender().getLastName();
        String borrower = "";

        switch (entry) {
            case StraightExpense straightExpense -> borrower = straightExpense.getPersonBorrower().getFirstName()
                    + " " + straightExpense.getPersonBorrower().getLastName();
            case InstallmentExpense installment -> borrower = installment.getPersonBorrower().getFirstName()
                    + " " + installment.getPersonBorrower().getLastName();
            //TODO: case GroupExpense groupExpense -> borrower = groupExpense.getGroupBorrower().getGroupName();
            default -> {
            }
        }
        return getInitials(borrower) + "_" + getInitials(lender);
    }

    /**
     * Helper method for initials, free to transfer to another class
     *
     * @param name derived from First Name & Last Name or Group Name
     * @return initials
     */
    public static String getInitials(String name) {
        if (name == null || name.isBlank()) return "";

        String[] words = name.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                initials.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        return initials.toString();
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

        List<ImageProof> imageProofs = imageProofService.saveImageFilesList(savedExpense, installmentCreateDto.getImageFiles());

        if (!imageProofs.isEmpty()) {
            savedExpense.getImageProofFiles().addAll(imageProofs);
        }

        savedExpense.setReferenceId(getReferenceId(savedExpense));
        savedExpense = entryRepository.save(savedExpense);

        return EntryMapper.mapToInstallmentResponseDto(savedExpense);
    }

}