package com.example.secondteamproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "answerproductinquiry")
public class AnswerProductInquiry extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWERPRODUCTINQUIRY_ID")
    private Long id;
    private String sellername;
    private String contentAnswer;
    private String username;

    public AnswerProductInquiry(String sellername, String contentAnswer, String username) {
        this.sellername = sellername;
        this.contentAnswer = contentAnswer;
        this.username = username;
    }
}
