package com.artevseev.sqlcrudpresentation.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Order {

    private Long id;
    private Integer type;
    private String comment;
    private Date orderTime;
    private Boolean isFinished;
    private Long restaurantId;

}
