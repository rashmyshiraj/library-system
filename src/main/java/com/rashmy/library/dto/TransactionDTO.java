package com.rashmy.library.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private LocalDate dueDate;

    // Book info (flattened for frontend)
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookIsbn;
    private String bookImageUrl;

    // Member info (flattened)
    private Long memberId;
    private String memberName;
    private String memberEmail;

    private boolean returned;

    // Fees
    private double normalFee;
    private double overdueFee;
    private double totalFee;

    public TransactionDTO() {}

    public TransactionDTO(Long id, LocalDateTime borrowedAt, LocalDateTime returnedAt,
                          LocalDate dueDate, Long bookId, String bookTitle, String bookAuthor,
                          String bookIsbn, String bookImageUrl,
                          Long memberId, String memberName, String memberEmail,
                          boolean returned,
                          double normalFee, double overdueFee, double totalFee) {

        this.id = id;
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.dueDate = dueDate;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookIsbn = bookIsbn;
        this.bookImageUrl = bookImageUrl;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.returned = returned;
        this.normalFee = normalFee;
        this.overdueFee = overdueFee;
        this.totalFee = totalFee;
    }

    // ---------- GETTERS ----------
    public Long getId() { return id; }
    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public LocalDate getDueDate() { return dueDate; }
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getBookAuthor() { return bookAuthor; }
    public String getBookIsbn() { return bookIsbn; }
    public String getBookImageUrl() { return bookImageUrl; }

    public Long getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public String getMemberEmail() { return memberEmail; }

    public boolean isReturned() { return returned; }
    public double getNormalFee() { return normalFee; }
    public double getOverdueFee() { return overdueFee; }
    public double getTotalFee() { return totalFee; }

    // ---------- SETTERS ----------
    public void setId(Long id) { this.id = id; }
    public void setBorrowedAt(LocalDateTime borrowedAt) { this.borrowedAt = borrowedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }
    public void setBookImageUrl(String bookImageUrl) { this.bookImageUrl = bookImageUrl; }

    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
    public void setMemberEmail(String memberEmail) { this.memberEmail = memberEmail; }

    public void setReturned(boolean returned) { this.returned = returned; }
    public void setNormalFee(double normalFee) { this.normalFee = normalFee; }
    public void setOverdueFee(double overdueFee) { this.overdueFee = overdueFee; }
    public void setTotalFee(double totalFee) { this.totalFee = totalFee; }
}
