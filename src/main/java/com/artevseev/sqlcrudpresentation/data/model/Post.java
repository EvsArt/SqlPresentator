package com.artevseev.sqlcrudpresentation.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Post {

    private Integer id;
    @NotNull
    private String name;

}
