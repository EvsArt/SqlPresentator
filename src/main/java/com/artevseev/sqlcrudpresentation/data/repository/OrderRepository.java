package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order, Long> {

    List<Order> findUnfinishedOrders();
    List<Order> findFinishedOrders();
    List<Order> findAllByFoodName(String name);
    Optional<Order> finish(Long id);

}
