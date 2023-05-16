package com.artevseev.sqlcrudpresentation;

import com.artevseev.sqlcrudpresentation.data.model.Food;
import com.artevseev.sqlcrudpresentation.data.model.Post;
import com.artevseev.sqlcrudpresentation.data.model.Restaurant;
import com.artevseev.sqlcrudpresentation.data.model.Worker;
import com.artevseev.sqlcrudpresentation.data.repository.FoodRepository;
import com.artevseev.sqlcrudpresentation.data.repository.PostRepository;
import com.artevseev.sqlcrudpresentation.data.repository.RestaurantRepository;
import com.artevseev.sqlcrudpresentation.data.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    public Config(FoodRepository foodRepository, RestaurantRepository restaurantRepository, WorkerRepository workerRepository, PostRepository postRepository){
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.workerRepository = workerRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner fillFoodTable(){
        return (args) -> {
            foodRepository.deleteAll(dbUrl, username, password, "food");

            foodRepository.save(new Food("Греча с мясом"));
            foodRepository.save(new Food("Картошка с мясом"));
            foodRepository.save(new Food("Рис с мясом"));
            foodRepository.save(new Food("Мясо с мясом"));
            foodRepository.save(new Food("Греча без мяса"));
            foodRepository.save(new Food("Картошка без мяса"));
            foodRepository.save(new Food("Рис без мяса"));
            foodRepository.save(new Food("Мясо без мяса"));

        };
    }

    @Bean CommandLineRunner fillRestaurantsTable(){
        return (args) -> {
            restaurantRepository.deleteAll(dbUrl, username, password, "restaurant");

            restaurantRepository.save(new Restaurant("Клоб-моне", 5.0));
            restaurantRepository.save(new Restaurant("Радуга", 4.7));
            restaurantRepository.save(new Restaurant("Сицилия", 3.2));
            restaurantRepository.save(new Restaurant("ДодоПицца", 5.0));

        };
    }

    @Bean CommandLineRunner fillPostsTable(){
        return (args) -> {
            postRepository.deleteAll(dbUrl, username, password, "restaurant");

            postRepository.save(new Post("Уборщик"));
            postRepository.save(new Post("Посудомойщик"));
            postRepository.save(new Post("Повар"));
            postRepository.save(new Post("Су-шеф"));
            postRepository.save(new Post("Шеф"));
            postRepository.save(new Post("Администратор"));
            postRepository.save(new Post("Нагиев"));

        };
    }

    @Bean CommandLineRunner fillWorkersTable(){
        return (args) -> {
            workerRepository.deleteAll(dbUrl, username, password, "worker");

            workerRepository.save(new Worker(
                    "Айнура",
                    postRepository.findByName("Уборщик").get().getId(),
                    restaurantRepository.findByName("Клоб-моне").get().getId()
            ));

            workerRepository.save(new Worker(
                    "Макс",
                    postRepository.findByName("Повар").get().getId(),
                    restaurantRepository.findByName("Клоб-моне").get().getId()
            ));

            workerRepository.save(new Worker(
                    "Лёва",
                    postRepository.findByName("Су-шеф").get().getId(),
                    restaurantRepository.findByName("Клоб-моне").get().getId()
            ));

            workerRepository.save(new Worker(
                    "Виктор Петрович",
                    postRepository.findByName("Шеф").get().getId(),
                    restaurantRepository.findByName("Клоб-моне").get().getId()
            ));

            workerRepository.save(new Worker(
                    "Дмитрий Нагиев",
                    postRepository.findByName("Нагиев").get().getId(),
                    restaurantRepository.findByName("Клоб-моне").get().getId()
            ));

        };
    }

}
