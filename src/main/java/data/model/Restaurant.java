package data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Restaurant {

    private Long id;
    private String name;
    private Double rating;

}
