package model;

import java.time.LocalDate;

public class Loan {

    private String loanId;
    private String memberId;
    private String title;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(String loanId, String memberId, String title) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.title = title;
        this.loanDate = LocalDate.now();
        this.returnDate = loanDate.plusDays(7);
        this.returned = false;
    }

    // ===== GETTER =====
    public String getLoanId() { return loanId; }
    public String getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return returned; }

    // ===== SETTER (UNTUK FILE LOAD) =====
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void markReturned() {
        this.returned = true;
    }

    public void returnBook() {
        this.returned = true;
    }
}
