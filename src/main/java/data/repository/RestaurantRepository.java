package data.repository;

import data.model.Restaurant;

import java.util.Optional;

public interface RestaurantRepository extends BaseRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String name);

}
