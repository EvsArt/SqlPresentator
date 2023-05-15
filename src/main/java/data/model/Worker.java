package data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Worker {

    private Long id;
    private String name;
    private Integer postId;
    private Long restaurantId;
    private Date employmentDate;

}
