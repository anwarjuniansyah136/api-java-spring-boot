package com.utsjava.utsanwar.services;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsjava.utsanwar.dto.PersonalTransactionLogDto;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDto;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDtoDaily;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDtoDeviation;
import com.utsjava.utsanwar.models.PersonalTransactionLog;
import com.utsjava.utsanwar.repositories.PersonalTransactionLogRepository;

@Service
public class PersonalTransactionLogServiceImpl implements PersonalTransactionLogService {

    @Autowired
    PersonalTransactionLogRepository personalTransactionLogRepository;

    @Override
    public String add(PersonalTransactionLogDto personalTransactionLogDto) {
        List<PersonalTransactionLog> tr = personalTransactionLogRepository
                .findByAccountNumber(personalTransactionLogDto.getAccountNumber());
        PersonalTransactionLog personal = tr.get(0);
        if (personal.getCustomerName().equals(personalTransactionLogDto.getCustomerName()) || tr == null) {
            PersonalTransactionLog transaction = new PersonalTransactionLog();
            transaction.setAccountNumber(personalTransactionLogDto.getAccountNumber());
            transaction.setAmount(personalTransactionLogDto.getAmount());
            transaction.setCustomerName(personalTransactionLogDto.getCustomerName());
            transaction.setDescription(personalTransactionLogDto.getDescription());
            transaction.setTransactionDate(personalTransactionLogDto.getTransactionDate());
            transaction.setTransactionType(personalTransactionLogDto.getTransactionType());
            personalTransactionLogRepository.save(transaction);
            return "succes";
        } else {
            return "failed";
        }
    }

    @Override
    public String update(PersonalTransactionLogDto personalTransactionLogDto,
            String id) {
        PersonalTransactionLog tr = personalTransactionLogRepository.findAccountById(id);
        if (tr == null) {
            return "failed";
        }
        PersonalTransactionLog transaction = new PersonalTransactionLog();
        transaction.setId(tr.getId());
        transaction.setAccountNumber(personalTransactionLogDto.getAccountNumber());
        transaction.setAmount(personalTransactionLogDto.getAmount());
        transaction.setCustomerName(personalTransactionLogDto.getCustomerName());
        transaction.setDescription(personalTransactionLogDto.getDescription());
        transaction.setTransactionDate(personalTransactionLogDto.getTransactionDate());
        transaction.setTransactionType(personalTransactionLogDto.getTransactionType());
        personalTransactionLogRepository.save(transaction);
        return "succes";
    }

    @Override
    public String delete(String id) {
        PersonalTransactionLog transaction = personalTransactionLogRepository.findAccountById(id);
        if (transaction == null) {
            return "failed";
        }
        personalTransactionLogRepository.delete(transaction);
        return "succes";
    }

    @Override
    public List<PersonalTransactionLogDto> view() {
        return personalTransactionLogRepository.findAllOrderByAccountNumberUsingHql()
                .stream()
                .map(this::toPersonalTransactionLogDto)
                .collect(Collectors.toList());
    }

    public PersonalTransactionLogDto toPersonalTransactionLogDto(PersonalTransactionLog personalTramsactionLog) {
        return PersonalTransactionLogDto.builder()
                .id(personalTramsactionLog.getId())
                .accountNumber(personalTramsactionLog.getAccountNumber())
                .customerName(personalTramsactionLog.getCustomerName())
                .transactionType(personalTramsactionLog.getTransactionType())
                .transactionDate(personalTramsactionLog.getTransactionDate())
                .amount(personalTramsactionLog.getAmount())
                .description(personalTramsactionLog.getDescription())
                .build();
    }

    @Override
    public PersonalTransactionLogResponseDto responseExpense(String accountNumber, Date startDate, Date endDate) {
        List<PersonalTransactionLog> transaction = personalTransactionLogRepository
                .findByAccountNumberAndByTransactionDateBetween(accountNumber, convertDateToLocalDateTime(startDate),
                        convertDateToLocalDateTime(endDate));
        PersonalTransactionLogResponseDto responseDto = new PersonalTransactionLogResponseDto();
        responseDto.setAccountNumber(accountNumber);
        responseDto.setCustomerName(transaction.get(0).getCustomerName());
        responseDto.setTotalExpense(expense(transaction));
        responseDto.setTotalIncome(income(transaction));
        return responseDto;
    }

    public int expense(List<PersonalTransactionLog> log) {
        int totalExpese = 0;
        for (PersonalTransactionLog personalTransactionLog : log) {
            if (personalTransactionLog.getTransactionType().equals("Debit")) {
                totalExpese += personalTransactionLog.getAmount();
            }
        }
        return totalExpese;
    }

    public int income(List<PersonalTransactionLog> log) {
        int totalIncome = 0;
        for (PersonalTransactionLog personalTransactionLog : log) {
            if (personalTransactionLog.getTransactionType().equals("Credit")) {
                totalIncome += personalTransactionLog.getAmount();
            }
        }
        return totalIncome;
    }

    @Override
    public PersonalTransactionLogResponseDtoDaily responseDaily(String accountNumber) {
        List<PersonalTransactionLog> transaction = personalTransactionLogRepository
                .findByAccountNumber(accountNumber);
        PersonalTransactionLogResponseDtoDaily responseDto = new PersonalTransactionLogResponseDtoDaily();
        responseDto.setAccountNumber(accountNumber);
        responseDto.setCustomerName(transaction.get(0).getCustomerName());
        responseDto.setPeriode(formatterDaily(transaction.get(0).getTransactionDate()));
        responseDto.setTotalExpense(expense(transaction));
        responseDto.setTotalIncome(income(transaction));
        return responseDto;
    }

    @Override
    public PersonalTransactionLogResponseDtoDaily responseMonthly(String accountNumber) {
        List<PersonalTransactionLog> transaction = personalTransactionLogRepository
                .findByAccountNumber(accountNumber);
        PersonalTransactionLogResponseDtoDaily responseDto = new PersonalTransactionLogResponseDtoDaily();
        responseDto.setAccountNumber(accountNumber);
        responseDto.setCustomerName(transaction.get(0).getCustomerName());
        responseDto.setPeriode(formatterMonth(transaction.get(0).getTransactionDate()));
        responseDto.setTotalExpense(expense(transaction));
        responseDto.setTotalIncome(income(transaction));
        return responseDto;
    }

    @Override
    public PersonalTransactionLogResponseDtoDaily responseAnnual(String accountNumber) {
        List<PersonalTransactionLog> transaction = personalTransactionLogRepository
                .findByAccountNumber(accountNumber);
        PersonalTransactionLogResponseDtoDaily responseDto = new PersonalTransactionLogResponseDtoDaily();
        responseDto.setAccountNumber(accountNumber);
        responseDto.setCustomerName(transaction.get(0).getCustomerName());
        responseDto.setPeriode(formatterYear(transaction.get(0).getTransactionDate()));
        responseDto.setTotalExpense(expense(transaction));
        responseDto.setTotalIncome(income(transaction));
        return responseDto;
    }

    public String formatterDaily(LocalDateTime time) {
        DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("EEEE,dd MMMM yyyy", Locale.ENGLISH);
        String date = time.format(formatterr);
        return date;
    }

    public String formatterMonth(LocalDateTime time) {
        DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        String date = time.format(formatterr);
        return date;
    }

    public String formatterYear(LocalDateTime time) {
        DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("yyyy");
        String date = time.format(formatterr);
        return date;
    }

    @Override
    public PersonalTransactionLogResponseDtoDeviation responseDeviation(String accountNumber, Date startDate,
            Date endDate) {
        List<PersonalTransactionLog> transaction = personalTransactionLogRepository
                .findByAccountNumberAndByTransactionDateBetween(accountNumber, convertDateToLocalDateTime(startDate),
                        convertDateToLocalDateTime(endDate));
        PersonalTransactionLogResponseDtoDeviation responseDto = new PersonalTransactionLogResponseDtoDeviation();
        responseDto.setAccountNumber(accountNumber);
        responseDto.setCustomerName(transaction.get(0).getCustomerName());
        responseDto.setTotalExpense(expense(transaction));
        responseDto.setTotalIncome(income(transaction));
        responseDto.setDeviation(income(transaction) - expense(transaction));
        responseDto.setConclussion(conslussion(income(transaction) - expense(transaction)));
        return responseDto;
    }

    public String conslussion(int total) {
        if (total > 0) {
            return "good";
        } else if (total == 0) {
            return "warning";
        } else {
            return "danger";
        }
    }

    @Override
    public List<PersonalTransactionLogResponseDtoDeviation> view(Date startDate, Date endDate) {
        List<PersonalTransactionLog> transaction = personalTransactionLogRepository
                .findByTransactionDateBetween(convertDateToLocalDateTime(startDate),
                        convertDateToLocalDateTime(endDate));
        HashSet<String> accountNumbers = new HashSet<>();

        List<PersonalTransactionLog> personal = new ArrayList<>();
        List<PersonalTransactionLogResponseDtoDeviation> dev = new ArrayList<>();
        PersonalTransactionLogResponseDtoDeviation dv = new PersonalTransactionLogResponseDtoDeviation();
        for (PersonalTransactionLog log : transaction) {
            String accountNumber = log.getAccountNumber();
            if (!accountNumbers.contains(accountNumber)) {
                personal.add(log);
                accountNumbers.add(accountNumber);
            }
        }
        for (PersonalTransactionLog log : personal) {
            dv.setAccountNumber(log.getAccountNumber());
            dv.setCustomerName(log.getCustomerName());
            dev.add(dv);
        }
        for (PersonalTransactionLogResponseDtoDeviation log : dev) {
            for (PersonalTransactionLog log2 : transaction) {
                if (log.getAccountNumber().equals(log2.getAccountNumber())) {
                    if (log2.getTransactionType().equals("Debit")) {
                        log.setTotalExpense(log.getTotalExpense() + log2.getAmount());
                    } else {
                        log.setTotalIncome(log.getTotalIncome() + log2.getAmount());
                    }
                }
            }
        }
        for (PersonalTransactionLogResponseDtoDeviation log : dev) {
            log.setDeviation(log.getTotalIncome() - log.getTotalExpense());
            log.setConclussion(conslussion(log.getTotalIncome() - log.getTotalExpense()));
        }
        return dev;
    }

    public LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
