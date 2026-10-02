package PracticeProblems.Splitwise.strategy;

import PracticeProblems.Splitwise.model.Expense;
import PracticeProblems.Splitwise.model.User;

import java.util.Map;

public interface SplitStrategy {
    Map<User, Double> splitExpense(Expense expense);
}
