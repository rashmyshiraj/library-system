package com.rashmy.library.service;

import com.rashmy.library.entity.Book;
import com.rashmy.library.entity.Member;
import com.rashmy.library.entity.Transaction;
import com.rashmy.library.exception.BadRequestException;
import com.rashmy.library.exception.NotFoundException;
import com.rashmy.library.repository.BookRepository;
import com.rashmy.library.repository.MemberRepository;
import com.rashmy.library.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    // fee per overdue day (admin-changeable later)
    private double feePerDay = 10.0;

    public TransactionService(TransactionRepository transactionRepository,
                              MemberRepository memberRepository,
                              BookRepository bookRepository) {
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public Transaction borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (!book.isAvailable()) {
            throw new BadRequestException("Book is already borrowed");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        Transaction transaction = new Transaction(member, book);
        transaction.setBorrowedAt(LocalDateTime.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setReturned(false);

        return transactionRepository.save(transaction);
    }

    public Transaction returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (transaction.isReturned()) {
            throw new BadRequestException("Book already returned");
        }

        transaction.setReturned(true);
        transaction.setReturnedAt(LocalDateTime.now());

        LocalDate dueDate = transaction.getDueDate();
        LocalDate returnedDate = transaction.getReturnedAt().toLocalDate();

        if (returnedDate.isAfter(dueDate)) {
            long overdueDays = ChronoUnit.DAYS.between(dueDate, returnedDate);
            transaction.setOverdueFee(overdueDays * feePerDay);
        } else {
            transaction.setOverdueFee(0);
        }

        Book book = transaction.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    // 🔥 PAGINATION: all transactions
    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    // 🔥 PAGINATION: overdue transactions
    public Page<Transaction> getOverdueTransactions(Pageable pageable) {
        return transactionRepository
                .findByReturnedFalseAndDueDateBefore(LocalDate.now(), pageable);
    }

    // 🔥 PAGINATION: overdue transactions by member
    public Page<Transaction> getOverdueTransactionsByMember(Long memberId, Pageable pageable) {
        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundException("Member not found");
        }

        return transactionRepository
                .findByMemberIdAndReturnedFalseAndDueDateBefore(
                        memberId,
                        LocalDate.now(),
                        pageable
                );
    }
}
