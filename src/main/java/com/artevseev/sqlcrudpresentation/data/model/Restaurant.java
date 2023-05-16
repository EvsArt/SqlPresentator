package com.artevseev.sqlcrudpresentation.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Restaurant {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double rating;

}
