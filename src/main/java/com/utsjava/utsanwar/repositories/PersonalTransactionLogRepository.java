package com.utsjava.utsanwar.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utsjava.utsanwar.models.PersonalTransactionLog;

public interface PersonalTransactionLogRepository extends JpaRepository<PersonalTransactionLog, String> {
    List<PersonalTransactionLog> findByAccountNumber(String accountNumber);

    PersonalTransactionLog findAccountById(String id);

    @Query("select ptl from PersonalTransactionLog ptl order by ptl.accountNumber asc")
    List<PersonalTransactionLog> findAllOrderByAccountNumberUsingHql();

    PersonalTransactionLog findOneByAccountNumber(String accountNumber);

    @Query("select ptl from PersonalTransactionLog ptl where ptl.accountNumber = :accountNumber and ptl.transactionDate between :startDate and :endDate")
    List<PersonalTransactionLog> findByAccountNumberAndByTransactionDateBetween(String accountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate);

    @Query("select ptl from PersonalTransactionLog ptl where ptl.accountNumber = :accountNumber and ptl.customerName = :customerName")
    PersonalTransactionLog findByAccountNumberAndCustomerName(@Param("accountNumber") String accountNummber,
            @Param("customerName") String customerName);

    @Query("select ptl from PersonalTransactionLog ptl where ptl.transactionDate between :startDate and :endDate")
    List<PersonalTransactionLog> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
