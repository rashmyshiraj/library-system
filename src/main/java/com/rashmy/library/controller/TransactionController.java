package com.rashmy.library.controller;

import com.rashmy.library.dto.TransactionDTO;
import com.rashmy.library.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<TransactionDTO> borrowBook(
            @RequestParam Long memberId,
            @RequestParam Long bookId
    ) {
        return ResponseEntity.ok(transactionService.borrowBook(memberId, bookId));
    }

    @PostMapping("/return")
    public ResponseEntity<TransactionDTO> returnBook(
            @RequestParam Long transactionId
    ) {
        return ResponseEntity.ok(transactionService.returnBook(transactionId));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
    }

    @GetMapping("/overdue")
    public ResponseEntity<Page<TransactionDTO>> getOverdue(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getOverdueTransactions(pageable));
    }

    @GetMapping("/overdue/member/{memberId}")
    public ResponseEntity<Page<TransactionDTO>> getOverdueByMember(
            @PathVariable Long memberId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                transactionService.getOverdueTransactionsByMember(memberId, pageable)
        );
    }
}
