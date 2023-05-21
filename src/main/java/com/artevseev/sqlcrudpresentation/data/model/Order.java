package com.artevseev.sqlcrudpresentation.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Order {

    private Long id;
    @NotNull
    private Integer type;
    private String comment;
    private Timestamp orderTime;
    private Boolean isFinished;
    @NotNull
    private Long restaurantId;

}
