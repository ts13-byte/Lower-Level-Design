package PracticeProblems.Splitwise.strategy;

import PracticeProblems.Splitwise.model.Expense;
import PracticeProblems.Splitwise.model.User;

import java.util.HashMap;
import java.util.Map;

public class EqualSplitStrategy implements SplitStrategy{
    @Override
    public Map<User, Double> splitExpense(Expense expense) {
        if (expense.getParticipants() == null || expense.getParticipants().isEmpty()) {
            throw new IllegalArgumentException("Cannot split an expense with no participants!");
        }
       Map<User , Double> result = new HashMap<>();
       double totalAmount = expense.getAmount();
       int participantSize = expense.getParticipants().size();
       double share = totalAmount / participantSize;
       for(User user : expense.getParticipants()) {
           result.put(user , share);
       }
       return result;
    }
}
