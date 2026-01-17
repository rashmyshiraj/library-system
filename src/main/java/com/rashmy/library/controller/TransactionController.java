package com.rashmy.library.controller;

import com.rashmy.library.entity.Transaction;
import com.rashmy.library.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<Transaction> borrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        return ResponseEntity.ok(transactionService.borrowBook(memberId, bookId));
    }

    @PostMapping("/return")
    public ResponseEntity<Transaction> returnBook(@RequestParam Long transactionId) {
        return ResponseEntity.ok(transactionService.returnBook(transactionId));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // NEW: list overdue transactions
    @GetMapping("/overdue")
    public ResponseEntity<List<Transaction>> getOverdue() {
        return ResponseEntity.ok(transactionService.getOverdueTransactions());
    }

    // NEW: overdue transactions for a member
    @GetMapping("/overdue/member/{memberId}")
    public ResponseEntity<List<Transaction>> getOverdueForMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(transactionService.getOverdueTransactionsByMember(memberId));
    }
}
