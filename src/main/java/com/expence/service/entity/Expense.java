package com.expence.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name= "external_id")
    private String externalId;

    @Column(name= "user_id")
    private String userId;

    @Column(name= "amount")
    private String amount;

    @Column(name= "currency")
    private String currency;

    @Column(name= "created_at")
    private Timestamp createdAt;

    @Column(name= "merchant")
    private String merchant;

    @PrePersist
    private void generateExternalId(){
        if(this.externalId==null){
            this.externalId = UUID.randomUUID().toString();
        }
        if(this.createdAt==null){
            this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
    }




}
