package com.artevseev.sqlcrudpresentation.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@AllArgsConstructor
public class Order {

    private Long id;
    @NotNull
    private Integer type;
    private String comment;
    private Date orderTime;
    private Boolean isFinished;
    @NotNull
    private Long restaurantId;

}
