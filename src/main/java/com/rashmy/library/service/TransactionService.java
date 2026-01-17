package com.rashmy.library.service;

import com.rashmy.library.dto.TransactionDTO;
import com.rashmy.library.entity.Book;
import com.rashmy.library.entity.Member;
import com.rashmy.library.entity.Transaction;
import com.rashmy.library.repository.BookRepository;
import com.rashmy.library.repository.MemberRepository;
import com.rashmy.library.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              MemberRepository memberRepository,
                              BookRepository bookRepository) {
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    // Convert Transaction -> TransactionDTO
    private TransactionDTO toDTO(Transaction t) {
        Book b = t.getBook();

        return new TransactionDTO(
                t.getId(),
                t.getBorrowedAt(),
                t.getReturnedAt(),
                t.getDueDate(),

                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getIsbn(),
                b.getImageUrl(),

                t.isReturned(),
                t.getNormalFee(),
                t.getOverdueFee(),
                t.getTotalFee()
        );
    }

    // Borrow book
    public TransactionDTO borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Transaction transaction = new Transaction(member, book);
        transaction.setNormalFee(5.0); // default normal fee

        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    // Return book
    public TransactionDTO returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setReturned(true);
        transaction.setReturnedAt(LocalDateTime.now());

        // calculate fees
        long daysLate = LocalDate.now().toEpochDay() - transaction.getDueDate().toEpochDay();
        if (daysLate > 0) {
            double normal = transaction.getNormalFee();
            double overdue = normal * 0.5 * daysLate; // 50% of normal per day late
            transaction.setOverdueFee(overdue);
            transaction.setTotalFee(normal + overdue);
        } else {
            transaction.setOverdueFee(0);
            transaction.setTotalFee(transaction.getNormalFee());
        }

        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    // Get all transactions (paginated)
    public Page<TransactionDTO> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(this::toDTO);
    }

    // Get overdue transactions (paginated)
    public Page<TransactionDTO> getOverdueTransactions(Pageable pageable) {
        return transactionRepository.findAllByReturnedFalseAndDueDateBefore(LocalDate.now(), pageable)
                .map(this::toDTO);
    }

    // Get overdue by member
    public Page<TransactionDTO> getOverdueTransactionsByMember(Long memberId, Pageable pageable) {
        return transactionRepository.findAllByMemberIdAndReturnedFalseAndDueDateBefore(
                        memberId, LocalDate.now(), pageable)
                .map(this::toDTO);
    }
}
