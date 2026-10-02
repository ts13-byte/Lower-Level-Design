package PracticeProblems.Splitwise.strategy;

import PracticeProblems.Splitwise.model.Expense;
import PracticeProblems.Splitwise.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExactAmountSplitStrategy implements SplitStrategy{
    public static final double EPSILON = 0.01;
    @Override
    public Map<User, Double> splitExpense(Expense expense) {
        double sum = 0;
        if (expense.getExactShare().isEmpty()) {
            throw new IllegalArgumentException("Exact Amount Split cannot be applied here !");
        }
        for (Double value : expense.getExactShare().values()) {
            sum += value;
        }
        if (Math.abs(sum - expense.getAmount()) > EPSILON) {
            throw new IllegalArgumentException("Individual amounts do not add up to full amount !");
        }
        return expense.getExactShare();
    }
}
