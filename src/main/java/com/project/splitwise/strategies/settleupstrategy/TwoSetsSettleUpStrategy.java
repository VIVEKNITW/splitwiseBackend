package com.project.splitwise.strategies.settleupstrategy;

import com.project.splitwise.repositories.ExpenseUserRepository;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.User;
import com.project.splitwise.models.ExpenseUser;
import com.project.splitwise.models.UserExpenseType;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.*;

@Component("twoSetsSettleUpStrategy")
public class TwoSetsSettleUpStrategy implements SettleUpStrategy {
    private ExpenseUserRepository expenseUserRepository;

    @Autowired
    public TwoSetsSettleUpStrategy(ExpenseUserRepository expenseUserRepository) {
        this.expenseUserRepository = expenseUserRepository;
    }

    @Override
    public List<Transaction> settle(List<Expense> expenses, long id) {
        // for all expenses that I have to settle, who paid how much and who had to pay how much
        List<ExpenseUser> allUserExpenseForTheseExpenses = expenseUserRepository.findAllByExpenseIn(expenses);

        int count = 1;
        System.out.println("List of expense users");
        for(ExpenseUser eu:allUserExpenseForTheseExpenses){
            System.out.println("expenseUser "+count+" user="+eu.getUser().getName()+" amount="+eu.getAmount());
        }

        HashMap<User, Double> moneyPaidExtra = new HashMap<>();
        System.out.println("Inside settle");
        // I went through all of the expenses and found out who has paid how much extra or less
        for (ExpenseUser userExpense: allUserExpenseForTheseExpenses) {
            User user = userExpense.getUser();
            double currentExtraPaid = 0;

            if (moneyPaidExtra.containsKey(user)) {
                currentExtraPaid = moneyPaidExtra.get(user);
            }


            moneyPaidExtra.put(user, currentExtraPaid+ userExpense.getAmount());
        }

        System.out.println("I went through all of the expenses and found out who has paid how much extra or less");
        moneyPaidExtra.forEach((k,v)->{
            System.out.println("key= "+k.getName()+" val= "+v);
        });

        System.out.println("priority queue definition");

        PriorityQueue<Pair> pqLessPaid = new PriorityQueue<>((a,b)->a.val.compareTo(b.val));
        PriorityQueue<Pair> pqExtraPaid = new PriorityQueue<>((a,b)->b.val.compareTo(a.val));


        for (Map.Entry<User, Double> userAmount: moneyPaidExtra.entrySet()) {
            if (userAmount.getValue() < 0) {
                pqLessPaid.add(new Pair(userAmount.getKey(), userAmount.getValue()));
            } else {
                pqExtraPaid.add(new Pair(userAmount.getKey(), userAmount.getValue()));
            }

        }


        List<Transaction> transactions = new ArrayList<>();

        while (!pqLessPaid.isEmpty()) {
            Pair lessPaidPair = pqLessPaid.remove(); // get and remove the first value
            Pair extraPaidPair = pqExtraPaid.remove();

            Transaction t = new Transaction();
            t.setFrom(lessPaidPair.user);
            t.setTo(extraPaidPair.user);

            if (Math.abs(lessPaidPair.val) < extraPaidPair.val) { // Vikram : -100 // Deepika: +200
                t.setAmount(Math.abs(lessPaidPair.val));

                if (!(extraPaidPair.val - Math.abs(lessPaidPair.val) == 0)) {
                    pqExtraPaid.add(new Pair(extraPaidPair.user, extraPaidPair.val - Math.abs(lessPaidPair.val)));
                }
            } else { // Vikram : -200 // Deepika: 100
                t.setAmount(extraPaidPair.val);

                if (!(lessPaidPair.val + extraPaidPair.val == 0)) {
                    pqLessPaid.add(new Pair(lessPaidPair.user, lessPaidPair.val + extraPaidPair.val));
                }
            }

            transactions.add(t);
        }

        System.out.println("Here are the transactions needed for settle: ");
        count = 0;
        for(Transaction T:transactions){
            System.out.println("Transaction: "+count);
            System.out.println("from: "+T.getFrom().getName()+ "to: " + T.getTo().getName()+ "amount= "+ T.getAmount());
            count++;
        }
        System.out.println("returning transaction list from settle strategy class");
        return transactions;

     }
}
