package data.repository;

import data.model.Food;

import java.util.Optional;

public interface FoodRepository extends BaseRepository<Food, Integer>{

    Optional<Food> findByName(String name);

}
