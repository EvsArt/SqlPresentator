package com.artevseev.sqlcrudpresentation.controller;

import com.artevseev.sqlcrudpresentation.data.model.Food;
import com.artevseev.sqlcrudpresentation.data.model.stat.FoodStat;
import com.artevseev.sqlcrudpresentation.data.repository.FoodRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @PostMapping("/save")
    public Food save(Food food){
        return foodRepository.save(food).orElse(null);
    }

    @GetMapping("/{id}")
    public Food getById(@PathVariable Integer id){
        return foodRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        foodRepository.deleteById(id);
    }

    @GetMapping("/all")
    public List<Food> getAll(){
        return foodRepository.findAll();
    }

    @GetMapping("/names")
    public List<String> getNames(){
        return foodRepository.findAllNames();
    }

    @GetMapping("/topfood")
    public List<FoodStat> getTopFood(){
        return foodRepository.getTopFood();
    }

}
