package com.artevseev.sqlcrudpresentation.data.model.stat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodStat {

    private Integer id;
    private String name;
    private Integer count;

}
