package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Worker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends BaseRepository<Worker, Long> {

    Optional<Worker> findByName(String name);
    List<Worker> findAllByPostName(String name);
    List<Worker> findAllInRestaurant(Long id);

}
