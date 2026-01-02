package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

public class EntryMapper {

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

    private static void mapToCommonDtoFields(Entry source, EntryResponseDto target) {
        target.setId(source.getId());
        target.setEntryName(source.getEntryName());
        target.setDescription(source.getDescription());
        target.setTransactionType(source.getTransactionType());
        target.setDateBorrowed(source.getDateBorrowed());
        target.setDateFullyPaid(source.getDateFullyPaid());
        target.setBorrowerName(source.getBorrowerName());
        target.setLenderName(source.getLenderName());
        target.setAmountBorrowed(source.getAmountBorrowed());
        target.setAmountRemaining(source.getAmountRemaining());
        target.setStatus(source.getStatus());
        target.setNotes(source.getNotes());
        target.setReferenceId(getReferenceId(source));

        if(source.getImageProofFiles() != null) {
            target.setImageUrls(
                    source.getImageProofFiles()
                            .stream()
                            .map(ImageProof::getImageUrl)
                            .collect(Collectors.toList())
            );
        }

        if (source.getPayments() != null) {
            target.setPayments(
                    source.getPayments()
                            .stream()
                            .map(PaymentMapper::mapToPaymentResponseDto)
                            .collect(Collectors.toList())
            );
        }

    }

    public static StraightResponseDto mapToStraightResponseDto(StraightExpense straightExpense) {
        StraightResponseDto dto = new StraightResponseDto();

        mapToCommonDtoFields(straightExpense, dto);
        dto.setBorrowerId(straightExpense.getPersonBorrower().getPersonId());

        return dto;
    }

    public static InstallmentResponseDto mapToInstallmentResponseDto (InstallmentExpense installmentExpense) {
        InstallmentResponseDto dto = new InstallmentResponseDto();

        mapToCommonDtoFields(installmentExpense, dto);

        dto.setBorrowerId(installmentExpense.getPersonBorrower().getPersonId());
        dto.setStartDate(installmentExpense.getStartDate());
        dto.setPaymentFrequency(installmentExpense.getPaymentFrequency());
        dto.setPaymentTerms(installmentExpense.getPaymentTerms());
        dto.setPaymentAmountPerTerm(installmentExpense.getPaymentAmountPerTerm());

        return dto;
    }

    private static void mapCommonEntityFields(EntryCreateDto source, Entry target) {
        target.setId(source.getId());
        target.setEntryName(source.getEntryName());
        target.setDescription(source.getDescription());
        target.setTransactionType(source.getTransactionType());
        target.setDateBorrowed(source.getDateBorrowed());
        target.setDateFullyPaid(source.getDateFullyPaid());

        target.setAmountBorrowed(source.getAmountBorrowed());
        target.setAmountRemaining(source.getAmountRemaining());
        target.setStatus(source.getStatus());
        target.setNotes(source.getNotes());
    }

    public static StraightExpense mapToStraightExpense(StraightCreateDto dto) {
        StraightExpense straightExpense = new StraightExpense();
        mapCommonEntityFields(dto, straightExpense);

        return straightExpense;
    }

    public static InstallmentExpense mapToInstallmentExpense(InstallmentCreateDto dto) {
        InstallmentExpense installmentExpense = new InstallmentExpense();
        mapCommonEntityFields(dto, installmentExpense);

        installmentExpense.setStartDate(dto.getStartDate());
        installmentExpense.setPaymentFrequency(dto.getPaymentFrequency());
        installmentExpense.setPaymentTerms(dto.getPaymentTerms());
        installmentExpense.setPaymentAmountPerTerm(dto.getPaymentAmountPerTerm());

        return installmentExpense;
    }

}
