package com.example.spring_hex_practive.infra;

import com.example.spring_hex_practive.domain.aggregate.entity.TrainStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainStopRepo extends JpaRepository<TrainStop, String> {
    TrainStop findByUuid(String uuid);

    @Query(value = "SELECT SEQ FROM TRAIN_STOP WHERE NAME=?1 AND TRAIN_UUID=?2 ", nativeQuery = true)
    int findTrainStopSeq(String name, String uuid);

    // TODO: Q2 多表查詢
    @Query(value = "SELECT TRAIN_STOP.NAME,TRAIN_STOP.TIME,TRAIN.TRAIN_KIND FROM TRAIN_STOP INNER JOIN TRAIN ON TRAIN_STOP.TRAIN_UUID=TRAIN.UUID WHERE TRAIN.TRAIN_NO=?1 ", nativeQuery = true)
    List<Map<String, Object>> findTrainNameAndTime(Integer trainNo);
}
