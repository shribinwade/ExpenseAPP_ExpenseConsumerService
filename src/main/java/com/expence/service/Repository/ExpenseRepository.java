package com.expence.service.Repository;

import com.expence.service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

  Optional<List<Expense>> findByUserId(String userId);

  Optional<List<Expense>> findByUserIdAndCreatedAtBetween(String userId, Timestamp startTime, Timestamp endTime);

  Optional<Expense> findByUserIdAndExternalId(String userId,String externalId);

}
