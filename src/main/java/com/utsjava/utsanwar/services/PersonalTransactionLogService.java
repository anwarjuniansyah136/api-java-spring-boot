package com.utsjava.utsanwar.services;

import java.sql.Date;
import java.util.List;

import com.utsjava.utsanwar.dto.PersonalTransactionLogDto;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDto;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDtoDaily;
import com.utsjava.utsanwar.dto.PersonalTransactionLogResponseDtoDeviation;

public interface PersonalTransactionLogService {
        public String add(PersonalTransactionLogDto personalTransactionLogDto);

        public String update(PersonalTransactionLogDto personalTransactionLogDto,
                        String id);

        public String delete(String id);

        public List<PersonalTransactionLogDto> view();

        public PersonalTransactionLogResponseDto responseExpense(String accountNumber, Date startDate, Date enDate);

        public PersonalTransactionLogResponseDtoDaily responseDaily(String accountNumber);

        public PersonalTransactionLogResponseDtoDaily responseMonthly(String accountNumber);

        public PersonalTransactionLogResponseDtoDaily responseAnnual(String accountNumber);

        public PersonalTransactionLogResponseDtoDeviation responseDeviation(String accountNumber, Date startDate,
                        Date enDate);

        public List<PersonalTransactionLogResponseDtoDeviation> view(Date startDate, Date endDate);
}