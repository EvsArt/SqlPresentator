package com.artevseev.sqlcrudpresentation;

import com.artevseev.sqlcrudpresentation.data.model.Food;
import com.artevseev.sqlcrudpresentation.data.model.Post;
import com.artevseev.sqlcrudpresentation.data.model.Restaurant;
import com.artevseev.sqlcrudpresentation.data.model.Worker;
import com.artevseev.sqlcrudpresentation.data.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class Config {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final WorkerRepository workerRepository;
    private final PostRepository postRepository;

    public Config(FoodRepository foodRepository,
                  RestaurantRepository restaurantRepository,
                  WorkerRepository workerRepository,
                  PostRepository postRepository) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.workerRepository = workerRepository;
        this.postRepository = postRepository;
    }

//    @Bean
//    @Transactional
//    public CommandLineRunner clearDataBase(){
//        return (args) -> {
//            workerRepository.deleteAll(dbUrl, username, password, "worker");
//            orderRepository.deleteAll(dbUrl, username, password, "\"order\"");
//            restaurantRepository.deleteAll(dbUrl, username, password, "restaurant");
//            postRepository.deleteAll(dbUrl, username, password, "post");
//            foodRepository.deleteAll(dbUrl, username, password, "food");
//
//        };
//    }

    @Bean
    @Transactional
    public CommandLineRunner fillFoodTable(){
        return (args) -> {
            if (!foodRepository.existByName("Греча с мясом"))
                foodRepository.save(new Food(null, "Греча с мясом"));
            if (!foodRepository.existByName("Картошка с мясом"))
                foodRepository.save(new Food(null, "Картошка с мясом"));
            if (!foodRepository.existByName("Рис с мясом"))
                foodRepository.save(new Food(null, "Рис с мясом"));
            if (!foodRepository.existByName("Мясо с мясом"))
                foodRepository.save(new Food(null, "Мясо с мясом"));
            if (!foodRepository.existByName("Греча без мяса"))
                foodRepository.save(new Food(null, "Греча без мяса"));
            if (!foodRepository.existByName("Картошка без мяса"))
                foodRepository.save(new Food(null, "Картошка без мяса"));
            if (!foodRepository.existByName("Рис без мяса"))
                foodRepository.save(new Food(null, "Рис без мяса"));
            if (!foodRepository.existByName("Мясо без мяса"))
                foodRepository.save(new Food(null, "Мясо без мяса"));

        };
    }

    @Bean
    @Transactional
    public CommandLineRunner fillRestaurantsTable(){
        return (args) -> {

            if (!restaurantRepository.existByName("Клоб-моне"))
                restaurantRepository.save(new Restaurant(null, "Клоб-моне", 5.0));
            if (!restaurantRepository.existByName("Радуга"))
                restaurantRepository.save(new Restaurant(null, "Радуга", 4.7));
            if (!restaurantRepository.existByName("Сицилия"))
                restaurantRepository.save(new Restaurant(null, "Сицилия", 3.2));
            if (!restaurantRepository.existByName("ДодоПицца"))
                restaurantRepository.save(new Restaurant(null, "ДодоПицца", 5.0));

        };
    }

    @Bean
    @Transactional
    public CommandLineRunner fillPostsTable(){
        return (args) -> {

            if (!postRepository.existByName("Уборщик"))
                postRepository.save(new Post(null, "Уборщик"));
            if (!postRepository.existByName("Посудомойщик"))
                postRepository.save(new Post(null, "Посудомойщик"));
            if (!postRepository.existByName("Повар"))
                postRepository.save(new Post(null, "Повар"));
            if (!postRepository.existByName("Су-шеф"))
                postRepository.save(new Post(null, "Су-шеф"));
            if (!postRepository.existByName("Шеф"))
                postRepository.save(new Post(null, "Шеф"));
            if (!postRepository.existByName("Администратор"))
                postRepository.save(new Post(null, "Администратор"));
            if (!postRepository.existByName("Нагиев"))
                postRepository.save(new Post(null, "Нагиев"));

        };
    }

    @Bean
    @Transactional
    public CommandLineRunner fillWorkersTable(){
        return (args) -> {

            if(!workerRepository.existByName("Айнура"))
                workerRepository.save(new Worker(
                        null,
                        "Айнура",
                        postRepository.findByName("Уборщик").get().getId(),
                        restaurantRepository.findByName("Клоб-моне").get().getId(),
                        null
                ));

            if(!workerRepository.existByName("Макс"))
                workerRepository.save(new Worker(
                        null,
                        "Макс",
                        postRepository.findByName("Повар").get().getId(),
                        restaurantRepository.findByName("Клоб-моне").get().getId(),
                        null
                ));

            if(!workerRepository.existByName("Лёва"))
                workerRepository.save(new Worker(
                        null,
                        "Лёва",
                        postRepository.findByName("Су-шеф").get().getId(),
                        restaurantRepository.findByName("Клоб-моне").get().getId(),
                        null
                ));

            if(!workerRepository.existByName("Виктор Петрович"))
                workerRepository.save(new Worker(
                        null,
                        "Виктор Петрович",
                        postRepository.findByName("Шеф").get().getId(),
                        restaurantRepository.findByName("Клоб-моне").get().getId(),
                        null
                ));

            if(!workerRepository.existByName("Дмитрий Нагиев"))
                workerRepository.save(new Worker(
                        null,
                        "Дмитрий Нагиев",
                        postRepository.findByName("Нагиев").get().getId(),
                        restaurantRepository.findByName("Клоб-моне").get().getId(),
                        null
                ));

        };
    }

}
