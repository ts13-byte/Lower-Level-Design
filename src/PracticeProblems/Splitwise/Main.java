package PracticeProblems.Splitwise;

import PracticeProblems.Splitwise.model.*;
import PracticeProblems.Splitwise.service.SplitwiseSystem;
import PracticeProblems.Splitwise.strategy.EqualSplitStrategy;
import PracticeProblems.Splitwise.strategy.ExactAmountSplitStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        User alice = new User("U1", "Alice");
        User bob = new User("U2", "Bob");
        User carol = new User("U3", "Carol");
        User dave = new User("U4", "Dave");

        SplitwiseSystem system = new SplitwiseSystem();

        // Expense 1: Alice paid $80 lunch, split 4 ways (each owes $20)
        Expense lunch = new Expense(
                "E1", 80.0, new EqualSplitStrategy(),
                List.of(alice, bob, carol, dave), null, alice
        );
        system.addExpense(lunch);

        // Expense 2: Bob paid $40 coffee, split between Bob and Carol ($20 each)
        Expense coffee = new Expense(
                "E2", 40.0, new EqualSplitStrategy(),
                List.of(bob, carol), null, bob
        );
        system.addExpense(coffee);

        System.out.println("--- Raw balances ---");
        system.printBalances();

        System.out.println("--- Net balance checks ---");
        System.out.println("Alice-Bob net: " + system.getNetBalance(alice, bob)); // expect 0 (Bob owes 20, Carol's debt doesn't touch this pair)
        System.out.println("Carol-Alice net: " + system.getNetBalance(carol, alice)); // expect 20
        System.out.println("Dave-Alice net: " + system.getNetBalance(dave, alice)); // expect 20

        System.out.println("--- Simplified debts ---");
        List<Transaction> transactions = system.simplifyDebts();
        for (Transaction t : transactions) {
            System.out.println(t);
        }
        // Expected: Carol pays Alice 40, Dave pays Alice 20 (2 transactions total)

        // Test: settle up and confirm it zeroes out
        System.out.println("--- After settling Dave & Alice ---");
        system.settleUp(dave, alice);
        System.out.println("Dave-Alice net after settle: " + system.getNetBalance(dave, alice)); // expect 0


        System.out.println("--- Exact Amount Split Test ---");

// Expense 3: Dave paid $100 for a group trip expense,
// split unevenly: Alice $50, Bob $30, Carol $20
        Map<User, Double> exactShares = new HashMap<>();
        exactShares.put(alice, 50.0);
        exactShares.put(bob, 30.0);
        exactShares.put(carol, 20.0);

        Expense trip = new Expense(
                "E3", 100.0, new ExactAmountSplitStrategy(),
                List.of(alice, bob, carol), null, dave, exactShares
        );
        system.addExpense(trip);

        System.out.println("Alice-Dave net: " + system.getNetBalance(alice, dave)); // expect -50 (Alice owes Dave 50)
        System.out.println("Bob-Dave net: " + system.getNetBalance(bob, dave));     // expect -30
        System.out.println("Carol-Dave net: " + system.getNetBalance(carol, dave)); // expect -20

// Test: exact amounts that DON'T sum to total — should throw
        System.out.println("--- Exact Amount Validation Test (should throw) ---");
        try {
            Map<User, Double> badShares = new HashMap<>();
            badShares.put(alice, 40.0);
            badShares.put(bob, 30.0);
            badShares.put(carol, 20.0); // sums to 90, not 100

            Expense badExpense = new Expense(
                    "E4", 100.0, new ExactAmountSplitStrategy(),
                    List.of(alice, bob, carol), null, dave, badShares
            );
            system.addExpense(badExpense);
            System.out.println("ERROR: should have thrown but didn't!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }

// Test: epsilon tolerance — sums to 99.995, should NOT throw (within epsilon)
        System.out.println("--- Exact Amount Epsilon Tolerance Test ---");
        try {
            Map<User, Double> closeEnoughShares = new HashMap<>();
            closeEnoughShares.put(alice, 33.33);
            closeEnoughShares.put(bob, 33.33);
            closeEnoughShares.put(carol, 33.34); // sums to exactly 100.00

            Expense closeExpense = new Expense(
                    "E5", 100.0, new ExactAmountSplitStrategy(),
                    List.of(alice, bob, carol), null, dave, closeEnoughShares
            );
            system.addExpense(closeExpense);
            System.out.println("Correctly accepted valid exact split");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: incorrectly rejected: " + e.getMessage());
        }
    }
}
