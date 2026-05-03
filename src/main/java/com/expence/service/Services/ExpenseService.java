package com.expence.service.Services;

import com.expence.service.DTOs.ExpenseDTO;
import com.expence.service.Repository.ExpenseRepository;
import com.expence.service.entity.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper=new ObjectMapper();

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public boolean createExpense(ExpenseDTO expenseDTO){
        setCurrency(expenseDTO);
        try{
            expenseRepository.save(objectMapper.convertValue(expenseDTO, Expense.class));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateExpense(ExpenseDTO expenseDTO){
        Optional<Expense> expenseFound = expenseRepository.findByUserIdAndExternalId(expenseDTO.getUserId(), expenseDTO.getExternalId());
        if (expenseFound.isEmpty()){
            return false;
        }
        Expense expense = expenseFound.get();
        expense.setCurrency(Strings.isNotBlank(expenseDTO.getCurrency())? expenseDTO.getCurrency(): expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDTO.getMerchant())? expenseDTO.getMerchant(): expense.getMerchant());
        expense.setAmount(expenseDTO.getAmount());
        expenseRepository.save(expense);
        return true;
    }

    public List<ExpenseDTO> getExpenses(String userId) {
        Optional<List<Expense>> expensesOptional = expenseRepository.findByUserId(userId);
        if (expensesOptional.isEmpty()) {
            return Collections.emptyList(); // return empty list if no expenses found
        }

        List<Expense> expenses = expensesOptional.get();

//        return expenses.stream()
//                .map(expense -> new ExpenseDTO(
//                        expense.getExternalId(),
//                        expense.getAmount(),
//                        expense.getUserId(),
//                        expense.getMerchant(),
//                        expense.getCurrency(),
//                        expense.getCreatedAt()
//                ))
//                .toList();
        return objectMapper.convertValue(expenses, new TypeReference<List<ExpenseDTO>>() {});
    }


    private void setCurrency(ExpenseDTO expenseDTO){
        if(Objects.isNull(expenseDTO.getCurrency())){
            expenseDTO.setCurrency("inr");
        }
    }

}
