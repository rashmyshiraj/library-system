package com.rashmy.library.controller;

import com.rashmy.library.entity.Transaction;
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
    public ResponseEntity<Transaction> borrowBook(
            @RequestParam Long memberId,
            @RequestParam Long bookId
    ) {
        return ResponseEntity.ok(transactionService.borrowBook(memberId, bookId));
    }

    @PostMapping("/return")
    public ResponseEntity<Transaction> returnBook(
            @RequestParam Long transactionId
    ) {
        return ResponseEntity.ok(transactionService.returnBook(transactionId));
    }

    // 🔥 PAGINATED: all transactions
    @GetMapping
    public ResponseEntity<Page<Transaction>> getAll(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
    }

    // 🔥 PAGINATED: overdue transactions
    @GetMapping("/overdue")
    public ResponseEntity<Page<Transaction>> getOverdue(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getOverdueTransactions(pageable));
    }

    // 🔥 PAGINATED: overdue transactions by member
    @GetMapping("/overdue/member/{memberId}")
    public ResponseEntity<Page<Transaction>> getOverdueByMember(
            @PathVariable Long memberId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                transactionService.getOverdueTransactionsByMember(memberId, pageable)
        );
    }
}
