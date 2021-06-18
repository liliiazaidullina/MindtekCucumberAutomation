package pojos.bankpojos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {
    private Account account;
    private Double amount;
    private Integer transactionId;
    private String transactionName;
    private String date;


}
