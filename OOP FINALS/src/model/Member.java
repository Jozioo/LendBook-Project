package model;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private List<Loan> loans = new ArrayList<>();

    public Member(String id, String username, String password) {
        super(id, username, password);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
