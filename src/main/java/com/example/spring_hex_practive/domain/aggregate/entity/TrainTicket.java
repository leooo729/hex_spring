package com.example.spring_hex_practive.domain.aggregate.entity;

import com.example.spring_hex_practive.iface.dto.request.BuyTicketRequest;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TRAIN_TICKET")
public class TrainTicket {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "TICKET_NO")
    private String ticketNo;
    @Column(name = "TRAIN_UUID")
    private String trainUuid;
    @Column(name = "FROM_STOP")
    private String fromStop;
    @Column(name = "TO_STOP")
    private String toStop;
    @Column(name = "TAKE_DATE")
    private String takeDate;
    @Column(name = "PRICE")
    private Double price;
    //--------------------------------------------------------------------------------------
    public TrainTicket(BuyTicketRequest request,String ticketNo, String trainUuid,Double price) {

        this.ticketNo = ticketNo;
        this.trainUuid = trainUuid;
        this.fromStop = request.getFromStop();
        this.toStop = request.getToStop();
        this.takeDate = request.getTakeDate();
        this.price = price;
    }

    public static TrainTicket create(BuyTicketRequest request,String ticketNo, String trainUuid,Double price) {
        return new TrainTicket(request,ticketNo,trainUuid,price);
    }
    //--------------------------------------------------------------------------------------

}
