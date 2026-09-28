package com.expence.service.Consumer;

import com.expence.service.DTOs.ExpenseDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpenseDeserializer implements Deserializer<ExpenseDTO> {

    private static final Logger log = LoggerFactory.getLogger(ExpenseDeserializer.class);

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public ExpenseDTO deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            return MAPPER.readValue(data, ExpenseDTO.class);
        } catch (Exception ex) {
            log.error("Failed to deserialize message from topic {}", topic, ex);
            throw new SerializationException("Failed to deserialize ExpenseDTO", ex);
        }
    }
}