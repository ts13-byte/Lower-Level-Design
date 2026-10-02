package PracticeProblems.Splitwise.service;


import PracticeProblems.Splitwise.model.Expense;
import PracticeProblems.Splitwise.model.Transaction;
import PracticeProblems.Splitwise.model.User;

import java.util.*;

public class SplitwiseSystem {

    Map<User , Map<User, Double>> balances;

    public SplitwiseSystem() {
        this.balances = new HashMap<>();
    }

    public void addExpense(Expense expense) {
       // apply chosen strategy on expense.
       Map<User , Double> shares = expense.getSplitStrategy().splitExpense(expense);
       // build the balance map.
        User payer = expense.getPaidBy();

        for(Map.Entry<User , Double> entry : shares.entrySet()) {
            User participant = entry.getKey();
            double share = entry.getValue();

            if(participant.equals(payer)) {
                continue;
            }

            updateBalance(participant , payer , share);
        }

    }

    /**
     * To solve the lost update problem , suppose two threads are trying to update balance for ower what they owe to owedTo
     * the last update must not override the previous one.
     * @param ower
     * @param owedTo
     * @param amount
     */
    private synchronized void updateBalance(User ower, User owedTo, double amount) {
        balances.computeIfAbsent(ower , k -> new HashMap<>())
                .merge(owedTo , amount , Double::sum);
    }

    /**
     * net = (what a owes b) - (what b owes a)
     * @param a
     * @param b
     * @return
     */
    public double getNetBalance(User a , User b) {
        double aOwesB = 0.0;
        double bOwesA = 0.0;

        // What a owes to b
        Map<User , Double> aDebts = balances.get(a);
        if(aDebts != null) {
            aOwesB = aDebts.getOrDefault(b , 0.0);
        }
        // what b owes a
        Map<User , Double> bDebts = balances.get(b);
        if(bDebts != null) {
            bOwesA = bDebts.getOrDefault(a , 0.0);
        }

        return aOwesB - bOwesA;
    }

    /**
     * update the debts on both side to be 0.0
     * @param a
     * @param b
     */
    public synchronized void settleUp(User a, User b) {
        Map<User, Double> aDebts = balances.get(a);
        if (aDebts != null) {
            aDebts.put(b, 0.0);
        }

        Map<User, Double> bDebts = balances.get(b);
        if (bDebts != null) {
            bDebts.put(a, 0.0);
        }
    }

    /**
     * print the netBalances of all the users
     */
    public void printBalances() {
        for (User a : balances.keySet()) {
            for (User b : balances.get(a).keySet()) {
                double net = getNetBalance(a, b);
                if (net > 0) {
                    System.out.println(a.getUserName() + " owes " + b.getUserName() + ": " + net);
                }
            }
        }
    }

    /**
     * compute the net positions of each person how much they owe + how much owed to
     * @return
     */
    private Map<User , Double> computeNetPositions() {
        Map<User , Double> net = new HashMap<>();

        for(User a : balances.keySet()) {
            for(Map.Entry<User , Double> entry : balances.get(a).entrySet()) {
                User b = entry.getKey();
                double amount = entry.getValue();

                net.merge(a , -amount , Double::sum); // a owes b -> so a collects debt so -ve
                net.merge(b , amount , Double::sum); // b credits a -> so +ve
            }
        }

        return net;
    }


    public List<Transaction> simplifyDebts() {
        Map<User , Double> net = computeNetPositions();

        // max-heap of creditors -> highest creditors at the top.
        PriorityQueue<Map.Entry<User , Double>> creditors = new PriorityQueue<>((x,y) -> Double.compare(y.getValue(),x.getValue()));

        // max-heap of debtors -> highest debtors at the top.(uses absolute values)
        PriorityQueue<Map.Entry<User , Double>> debtors = new PriorityQueue<>((x,y) -> Double.compare(y.getValue() , x.getValue()));

        double epsilon = 0.01;

        for(Map.Entry<User,Double> entry : net.entrySet()) {
            // +ve sign
            if(entry.getValue() > epsilon) {
                creditors.add(entry);
            } else if(entry.getValue() < -epsilon) {
                //-ve sign
                debtors.add(Map.entry(entry.getKey(), -entry.getValue()));
            }
            // no need to put the zero values
        }

        List<Transaction> transactions = new ArrayList<>();

        while(!creditors.isEmpty() && !debtors.isEmpty()) {
            // take the highest creditor and debtor
            Map.Entry<User,Double> creditor = creditors.poll();
            Map.Entry<User,Double> debtor = debtors.poll();
            // settle the amount between them
            double settleAmount = Math.min(creditor.getValue(), debtor.getValue());
            transactions.add(new Transaction(debtor.getKey(),creditor.getKey(),settleAmount));

            double remainingCredit = creditor.getValue() - settleAmount;
            double remainingDebt = debtor.getValue() - settleAmount;

            // if there is remainingCredit or remainingDebt-> put the remainingAmount in their respective max-heaps
            if (remainingCredit > epsilon) {
                creditors.add(Map.entry(creditor.getKey(), remainingCredit));
            }
            if (remainingDebt > epsilon) {
                debtors.add(Map.entry(debtor.getKey(), remainingDebt));
            }
        }

        return transactions;
    }
}
