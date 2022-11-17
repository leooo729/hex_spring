package com.example.spring_hex_practive.infra;

import com.example.spring_hex_practive.domain.aggregate.entity.TrainTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTicketRepo extends JpaRepository<TrainTicket, String> {
    TrainTicket findByTicketNo(String ticketNo);
}
