package com.rashmy.library.repository;

import com.rashmy.library.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByReturnedFalseAndDueDateBefore(LocalDate date);

    List<Transaction> findByMemberIdAndReturnedFalseAndDueDateBefore(Long memberId, LocalDate date);
}
