package com.rashmy.library.repository;

import com.rashmy.library.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // PAGINATED: all transactions
    Page<Transaction> findAll(Pageable pageable);

    // PAGINATED: overdue transactions
    Page<Transaction> findByReturnedFalseAndDueDateBefore(LocalDate date, Pageable pageable);

    // PAGINATED: overdue transactions by member
    Page<Transaction> findByMemberIdAndReturnedFalseAndDueDateBefore(
            Long memberId,
            LocalDate date,
            Pageable pageable
    );
}
