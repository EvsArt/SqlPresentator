package com.artevseev.sqlcrudpresentation.data.model.stat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestStat {

    private Long id;
    private String name;
    private Integer orderCount;

}
