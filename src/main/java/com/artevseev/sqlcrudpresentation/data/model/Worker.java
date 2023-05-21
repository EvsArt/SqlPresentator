package com.artevseev.sqlcrudpresentation.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@AllArgsConstructor
//@RequiredArgsConstructor
public class Worker {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Integer postId;
    @NotNull
    private Long restaurantId;

    private Date employmentDate;

}
