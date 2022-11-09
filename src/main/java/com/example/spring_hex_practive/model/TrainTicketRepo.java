package com.example.spring_hex_practive.model;

import com.example.spring_hex_practive.model.entity.TrainTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTicketRepo extends JpaRepository<TrainTicket, String> {
}
