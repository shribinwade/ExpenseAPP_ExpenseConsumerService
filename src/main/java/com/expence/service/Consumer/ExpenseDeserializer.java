package com.expence.service.Consumer;

import com.expence.service.DTOs.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class ExpenseDeserializer implements Deserializer<ExpenseDTO> {

    @Override
    public ExpenseDTO deserialize(String topic, byte[] data) {
        ObjectMapper objectMapper = new ObjectMapper();
        ExpenseDTO expenseDTO = null;
        try{
            if (data == null || data.length == 0) {
                return null;
            }
            System.out.println("Incoming payload: " + new String(data));
            expenseDTO= objectMapper.readValue(data, ExpenseDTO.class);
        }catch (Exception ex){
          ex.printStackTrace();
        }
        return expenseDTO;
    }
}
