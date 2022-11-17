package com.example.spring_hex_practive.infra;

import com.example.spring_hex_practive.domain.aggregate.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainRepo extends JpaRepository<Train, String> {
    Train findByTrainNo(int trainNo);

    Train findByUuid(String uuid);

    // TODO: Q2 多表查詢
    @Query(value = "SELECT TRAIN_NO,TRAIN_KIND FROM TRAIN INNER JOIN TRAIN_STOP ON TRAIN.UUID=TRAIN_STOP.TRAIN_UUID WHERE TRAIN_STOP.NAME=?1 ORDER BY TIME ASC", nativeQuery = true)
    List<Map<String, Object>> findTrainNoAndTrainKind(String stopName);

    @Query(value = "SELECT UUID FROM TRAIN WHERE TRAIN_NO=?1", nativeQuery = true)
    String findUuidByTrainNo(Integer trainNo);
}
