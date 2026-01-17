package com.rashmy.library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private LocalDate dueDate;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;

    private boolean returned;

    // FEES
    private double normalFee;
    private double overdueFee;
    private double totalFee;

    public Transaction() {}

    public Transaction(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.borrowedAt = LocalDateTime.now();
        this.dueDate = LocalDate.now().plusDays(14);
        this.returned = false;
        this.normalFee = 0;
        this.overdueFee = 0;
        this.totalFee = 0;
    }

    // GETTERS
    public Long getId() { return id; }
    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public LocalDate getDueDate() { return dueDate; }
    public Member getMember() { return member; }
    public Book getBook() { return book; }
    public boolean isReturned() { return returned; }
    public double getNormalFee() { return normalFee; }
    public double getOverdueFee() { return overdueFee; }
    public double getTotalFee() { return totalFee; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setBorrowedAt(LocalDateTime borrowedAt) { this.borrowedAt = borrowedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setMember(Member member) { this.member = member; }
    public void setBook(Book book) { this.book = book; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public void setNormalFee(double normalFee) { this.normalFee = normalFee; }
    public void setOverdueFee(double overdueFee) { this.overdueFee = overdueFee; }
    public void setTotalFee(double totalFee) { this.totalFee = totalFee; }
}
