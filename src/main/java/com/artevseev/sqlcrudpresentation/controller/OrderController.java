package com.artevseev.sqlcrudpresentation.controller;

import com.artevseev.sqlcrudpresentation.data.model.Order;
import com.artevseev.sqlcrudpresentation.data.model.Order;
import com.artevseev.sqlcrudpresentation.data.model.stat.FoodStat;
import com.artevseev.sqlcrudpresentation.data.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/save")

    public Order save(Order order){
        return orderRepository.save(order).orElse(null);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id){
        return orderRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        orderRepository.deleteById(id);
    }

    @GetMapping("/all")
    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    @GetMapping("/unfinished")
    public List<Order> getUnfinishedOrders(){
        return orderRepository.findUnfinishedOrders();
    }

    @GetMapping("/finished")
    public List<Order> getFinishedOrders(){
        return orderRepository.findFinishedOrders();
    }

    @GetMapping("/getbyname/{name}")
    public List<Order> getByName(@PathVariable String name){
        return orderRepository.findAllByFoodName(name);
    }

    @PostMapping("/{id}/finish")
    public Order finishOrder(@PathVariable Long id){
        return orderRepository.finish(id).orElse(null);
    }

}
