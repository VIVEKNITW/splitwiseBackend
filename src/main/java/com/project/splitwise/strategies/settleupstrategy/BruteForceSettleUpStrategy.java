package com.project.splitwise.strategies.settleupstrategy;

import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.ExpenseUser;
import com.project.splitwise.models.User;
import com.project.splitwise.repositories.ExpenseUserRepository;
import com.project.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.StreamSupport;

@Component("bruteForceSettleUpStrategy")
public class BruteForceSettleUpStrategy implements SettleUpStrategy {
    private ExpenseUserRepository expenseUserRepository;
    private UserRepository userRepository;

    @Autowired
    public BruteForceSettleUpStrategy(ExpenseUserRepository expenseUserRepository, UserRepository userRepository) {
        this.expenseUserRepository = expenseUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Transaction> settle(List<Expense> expenses, long id) throws UserDoesntExistException {
        System.out.println("Inside bruteforce strategy");
        // for all expenses that I have to settle, who paid how much and who had to pay how much
        List<ExpenseUser> allUserExpenseForTheseExpenses = expenseUserRepository.findAllByExpenseIn(expenses);

        int count = 1;
        HashMap<Long, Double> total = new HashMap<>();
        System.out.println("List of expense users");
        for (ExpenseUser eu : allUserExpenseForTheseExpenses) {
            System.out.println("expenseUser " + count + " user=" + eu.getUser().getName() + " amount=" + eu.getAmount()+ "id="+ eu.getId());
            if(total.containsKey(eu.getUser().getId())){
                total.put(eu.getUser().getId(), total.get(eu.getUser().getId())+ eu.getAmount());
            }
            else{
                total.put(eu.getUser().getId(), eu.getAmount());
            }
        }

        total.forEach((u,v)->{
            System.out.println(u+" : "+v);
        });

        double amount = total.get(id);
        Optional<User> optionalSettleUpUser = userRepository.findById(id);
        User settleUpUser = optionalSettleUpUser.get();

        List<Transaction> transactions = new ArrayList<>();
        for(Map.Entry<Long, Double> entry:total.entrySet()){
            long u = entry.getKey();
            double v = entry.getValue();
            if(u!=id){
                Optional<User> optionalOtherUser = userRepository.findById(u);
                User otherUser = optionalOtherUser.get();
                Transaction T = new Transaction();
                if(amount<0 && v>0){
                    T.setFrom(settleUpUser);
                    T.setTo(otherUser);

                    double amountTransferred = Math.min(Math.abs(amount), Math.abs(v));
                    amount = amount+amountTransferred;
                    total.put(u, v-amountTransferred);
                    T.setAmount(amountTransferred);

                    transactions.add(T);
                }
                else if(amount>0 && v<0){
                    T.setFrom(otherUser);
                    T.setTo(settleUpUser);

                    double amountTransferred = Math.min(Math.abs(amount), Math.abs(v));
                    amount = amount-amountTransferred;
                    total.put(u, v+amountTransferred);
                    T.setAmount(amountTransferred);

                    transactions.add(T);
                }
            }
        }

        System.out.println("Here are the transactions needed to settle");
        for(Transaction t:transactions){
            System.out.println(t.getFrom().getName()+"->"+t.getTo().getName()+":"+t.getAmount());
        }

        return transactions;
    }
}
