package PracticeProblems.Splitwise.model;

import PracticeProblems.Splitwise.strategy.SplitStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Expense {
    private String expenseId;
    private String expenseName;
    private double amount;
    private final SplitStrategy splitStrategy;
    private List<User> participants;
    private Group group;
    private User paidBy;
    private Map<User , Double> exactShare;

    public Expense(String expenseId, double amount, SplitStrategy splitStrategy,
                   List<User> participants, Group group, User paidBy) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.splitStrategy = splitStrategy;
        this.participants = participants;
        this.group = group;
        this.paidBy = paidBy;
    }

    public Expense(String expenseId, double amount, SplitStrategy splitStrategy, List<User> participants, Group group, User paidBy, Map<User, Double> exactShare) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.splitStrategy = splitStrategy;
        this.participants = participants;
        this.group = group;
        this.paidBy = paidBy;
        this.exactShare = exactShare;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public SplitStrategy getSplitStrategy() {
        return splitStrategy;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public Map<User, Double> getExactShare() {
        return exactShare;
    }

    public void setExactShare(Map<User, Double> exactShare) {
        this.exactShare = exactShare;
    }
}
