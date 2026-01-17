package com.rashmy.library.service;

import com.rashmy.library.entity.Book;
import com.rashmy.library.entity.Member;
import com.rashmy.library.entity.Transaction;
import com.rashmy.library.exception.BadRequestException;
import com.rashmy.library.exception.NotFoundException;
import com.rashmy.library.repository.BookRepository;
import com.rashmy.library.repository.MemberRepository;
import com.rashmy.library.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    // CONFIG (admin-controlled later)
    private double normalFeePerDay = 10.0;
    private double overdueMultiplier = 0.5;

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
            throw new BadRequestException("Book already borrowed");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        return transactionRepository.save(new Transaction(member, book));
    }

    public Transaction returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (transaction.isReturned()) {
            throw new BadRequestException("Book already returned");
        }

        transaction.setReturned(true);
        transaction.setReturnedAt(LocalDateTime.now());

        // 🔹 NORMAL FEE
        long borrowedDays = ChronoUnit.DAYS.between(
                transaction.getBorrowedAt().toLocalDate(),
                transaction.getReturnedAt().toLocalDate()
        );
        borrowedDays = Math.max(1, borrowedDays);
        double normalFee = borrowedDays * normalFeePerDay;

        // 🔹 OVERDUE FEE (INCREASING)
        long overdueDays = ChronoUnit.DAYS.between(
                transaction.getDueDate(),
                transaction.getReturnedAt().toLocalDate()
        );
        overdueDays = Math.max(0, overdueDays);

        double overdueFee = normalFeePerDay * overdueMultiplier
                * (overdueDays * (overdueDays + 1) / 2.0);

        transaction.setNormalFee(normalFee);
        transaction.setOverdueFee(overdueFee);
        transaction.setTotalFee(normalFee + overdueFee);

        Book book = transaction.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getOverdueTransactions() {
        return transactionRepository.findByReturnedFalseAndDueDateBefore(LocalDate.now());
    }

    public List<Transaction> getOverdueTransactionsByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundException("Member not found");
        }
        return transactionRepository.findByMemberIdAndReturnedFalseAndDueDateBefore(memberId, LocalDate.now());
    }
}
