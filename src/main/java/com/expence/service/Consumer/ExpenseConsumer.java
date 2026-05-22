package com.expence.service.Consumer;

import com.expence.service.DTOs.ExpenseDTO;
import com.expence.service.Services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseConsumer {

    private ExpenseService expenseService;

    @Autowired
    ExpenseConsumer(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDTO expenseDTO){
        try{
            //TODO: Make it transactionl, and check if duplicate event (handle idempotency)
             expenseService.createExpense(expenseDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception is listing the event");
        }
    }

}
