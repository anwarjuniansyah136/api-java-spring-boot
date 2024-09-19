package com.utsjava.utsanwar.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utsjava.utsanwar.dto.PersonalTransactionLogDto;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDto;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDtoDaily;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDtoDeviation;
import com.utsjava.utsanwar.services.PersonalTransactionLogService;

@RestController
@RequestMapping("/personal-transaction")
public class PersonalTransactionLogController {

    @Autowired
    private PersonalTransactionLogService personalTransactionLogService;

    @PostMapping
    public String add(@RequestBody PersonalTransactionLogDto dto) {
        return personalTransactionLogService.add(dto);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody PersonalTransactionLogDto dto) {
        return personalTransactionLogService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return personalTransactionLogService.delete(id);
    }

    @GetMapping
    public List<PersonalTransactionLogDto> view() {
        return personalTransactionLogService.view();
    }

    @GetMapping("/personal-expenses-income")
    public PersonalTransactionLogResponseDto datePersonalTransactinLogRequestDto(
            @RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String accountNumber) {
        return personalTransactionLogService.responseExpense(accountNumber, startDate, endDate);
    }

    @GetMapping("/personal-expenses-income/daily")
    public PersonalTransactionLogResponseDtoDaily daily(@RequestParam String accountNumber) {
        return personalTransactionLogService.responseDaily(accountNumber);
    }

    @GetMapping("/personal-expenses-income/monthly")
    public PersonalTransactionLogResponseDtoDaily monthly(@RequestParam String accountNumber) {
        return personalTransactionLogService.responseMonthly(accountNumber);
    }

    @GetMapping("/personal-expenses-income/annual")
    public PersonalTransactionLogResponseDtoDaily annual(@RequestParam String accountNumber) {
        return personalTransactionLogService.responseAnnual(accountNumber);
    }

    @GetMapping("/personal-deviation")
    public PersonalTransactionLogResponseDtoDeviation deviation(@RequestParam String accountNumber,
            @RequestParam Date startDate, @RequestParam Date endDate) {
        return personalTransactionLogService.responseDeviation(accountNumber, startDate, endDate);
    }

    @GetMapping("/personal-deviation/all-customers")
    public List<PersonalTransactionLogResponseDtoDeviation> res(@RequestParam Date startDate,
            @RequestParam Date endDate) {
        return personalTransactionLogService.view(startDate, endDate);
    }
}
