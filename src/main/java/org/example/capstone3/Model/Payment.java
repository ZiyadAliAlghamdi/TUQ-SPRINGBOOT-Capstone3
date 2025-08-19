package org.example.capstone3.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String name;
    private String number;
    private String cvc;
    private String month;
    private String year;
    private Double amount;
    private String currency;
}
