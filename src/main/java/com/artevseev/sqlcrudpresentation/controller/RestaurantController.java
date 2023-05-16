package com.artevseev.sqlcrudpresentation.controller;

import com.artevseev.sqlcrudpresentation.data.model.Restaurant;
import com.artevseev.sqlcrudpresentation.data.model.stat.RestStat;
import com.artevseev.sqlcrudpresentation.data.repository.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/save")
    public Restaurant save(Restaurant restaurant){
        return restaurantRepository.save(restaurant).orElse(null);
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable Long id){
        return restaurantRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        restaurantRepository.deleteById(id);
    }

    @GetMapping("/all")
    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }

    @GetMapping("/free")
    public List<Restaurant> findWithoutActiveOrders(){
        return restaurantRepository.findWithoutActiveOrders();
    }

    @GetMapping("/top")
    public List<RestStat> findTopRestaurants(){
        return restaurantRepository.getTopRestaurants();
    }

}
