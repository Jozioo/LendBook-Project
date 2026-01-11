package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Fine {

    private static final int MAX_BORROW_DAYS = 7;
    private static final long DAILY_FINE = 1000;

    private String id;
    private Loan loan;
    private long amount;

    public Fine(String id, Loan loan) {
        this.id = id;
        this.loan = loan;
        this.amount = calculateAmount();
    }

    public String getId() {
        return id;
    }

    public Loan getLoan() {
        return loan;
    }

    public long getAmount() {
        return amount;
    }

    private long calculateAmount() {
        if (!loan.isReturned()) return 0;

        LocalDate dueDate = loan.getLoanDate().plusDays(MAX_BORROW_DAYS);
        LocalDate returnedDate = loan.getReturnDate();

        long lateDays = ChronoUnit.DAYS.between(dueDate, returnedDate);
        if (lateDays <= 0) return 0;

        return lateDays * DAILY_FINE;
    }
}
