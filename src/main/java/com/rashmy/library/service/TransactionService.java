package com.rashmy.library.service;

import com.rashmy.library.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    TransactionDTO borrowBook(Long memberId, Long bookId);
    TransactionDTO returnBook(Long transactionId);

    Page<TransactionDTO> getAllTransactions(Pageable pageable);
    Page<TransactionDTO> getOverdueTransactions(Pageable pageable);
    Page<TransactionDTO> getOverdueTransactionsByMember(Long memberId, Pageable pageable);
}
