package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.stat.RestStat;
import com.artevseev.sqlcrudpresentation.data.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends BaseRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String name);
    List<Restaurant> findWithoutActiveOrders();
    List<RestStat> getTopRestaurants();

}
