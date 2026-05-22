package com.expence.service.RestController;

import com.expence.service.DTOs.ExpenseDTO;
import com.expence.service.Services.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class ExpenseController {

  private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense/v1/")
    public ResponseEntity<List<ExpenseDTO>> getExpenses(@PathParam("user_id") @NonNull String userID){
        try{
            List<ExpenseDTO> expenseDTOList = expenseService.getExpenses(userID);
           return new ResponseEntity<>(expenseDTOList,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addExpense")
    public ResponseEntity<Boolean> addExpenses(@RequestHeader(value = "X-User-Id") @NonNull String userId,ExpenseDTO expenseDTO){
        try{
              expenseDTO.setUserId(userId);
              return new ResponseEntity<>(expenseService.createExpense(expenseDTO),HttpStatus.OK);
        }catch (Exception ex){
              return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }

}
