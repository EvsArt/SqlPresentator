package com.artevseev.sqlcrudpresentation.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Food {

    private Integer id;
    @NotNull
    private String name;

}
