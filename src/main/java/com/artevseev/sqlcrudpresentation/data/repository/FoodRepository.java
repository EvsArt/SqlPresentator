package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Food;
import com.artevseev.sqlcrudpresentation.data.model.stat.FoodStat;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends BaseRepository<Food, Integer>{

    Optional<Food> findByName(String name);
    List<String> findAllNames();
    List<FoodStat> getTopFood();
    boolean existByName(String name);

}
