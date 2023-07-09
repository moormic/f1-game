package com.moormic.f1.model;

import lombok.Data;

@Data
public class Event {
    private Integer id;
    private Competition competition;
    private Circuit circuit;
    private String timezone;
    private String date;
    private String type;
    private String status;
}
