package com.kane.repository.transaction;

import com.kane.model.Operation;

import java.util.List;
import java.util.Map;


public interface OperationRepository {

    void addOperation(Operation operation);

    List<Operation> getAllByUserId(long userId);

    void updateOperation(Operation operation);

    void deleteOperation(long id);

    Operation getById(long id);

    List<Operation> getAllByUserId(long userId, int limit);


    Map<String, Double> getUserStats(long userId);

    List<Operation> getByCategoryId(long categoryId);
}
