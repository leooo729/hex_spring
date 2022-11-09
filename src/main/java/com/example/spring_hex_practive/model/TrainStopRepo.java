package com.example.spring_hex_practive.model;

import com.example.spring_hex_practive.model.entity.TrainStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainStopRepo extends JpaRepository<TrainStop, String> {

    @Query(value = "SELECT SEQ FROM TRAIN_STOP WHERE NAME=?1 AND TRAIN_UUID=?2 ",nativeQuery = true)
    int findTrainStopSeq(String name,String uuid);

    @Query(value = "SELECT TRAIN_STOP.NAME,TRAIN_STOP.TIME,TRAIN.TRAIN_KIND FROM TRAIN_STOP INNER JOIN TRAIN ON TRAIN_STOP.TRAIN_UUID=TRAIN.UUID WHERE TRAIN.TRAIN_NO=?1 ",nativeQuery = true)
    List<Map<String,Object>> findTrainNameAndTime(Integer trainNo);
}
//    List<TrainStop> findByTrainUuid(String trainUuid);
//    @Query(value = "SELECT TRAIN_UUID FROM TRAIN_STOP WHERE NAME=?1 ORDER BY TIME ASC",nativeQuery = true)
//    List<String> findTrainUuidByName(String name);