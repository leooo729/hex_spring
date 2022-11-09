package com.example.spring_hex_practive.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRAIN_STOP")
public class TrainStop {
    @Id
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "TRAIN_UUID")
    private String trainUuid;
    @Column(name = "SEQ")
    private Integer seq;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TIME")
    private LocalTime time;
    @Column(name = "DELETE_FLAG")
    private String deleteFlag;
}
