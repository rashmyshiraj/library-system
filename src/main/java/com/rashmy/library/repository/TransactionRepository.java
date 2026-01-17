package com.rashmy.library.repository;

import com.rashmy.library.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByReturnedFalseAndDueDateBefore(LocalDate date, Pageable pageable);

    Page<Transaction> findAllByMemberIdAndReturnedFalseAndDueDateBefore(
            Long memberId, LocalDate date, Pageable pageable);
}
