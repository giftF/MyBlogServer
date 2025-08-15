package org.gift.PersistantObject.entity;


import lombok.Data;

@Data
public class Card {
    private int id;
    private String dc;
    private String zy;
    private String fy;
    private int correct;
    private int error;
    private int continuous;
}
