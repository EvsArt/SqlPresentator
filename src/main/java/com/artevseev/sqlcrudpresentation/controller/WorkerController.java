package com.artevseev.sqlcrudpresentation.controller;

import com.artevseev.sqlcrudpresentation.data.model.Worker;
import com.artevseev.sqlcrudpresentation.data.repository.WorkerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class WorkerController {

    private final WorkerRepository workerRepository;

    public WorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @PostMapping("/save")
    public Worker save(Worker worker){
        worker.setEmploymentDate(new Date());
        return workerRepository.save(worker).orElse(null);
    }

    @GetMapping("/{id}")
    public Worker getById(@PathVariable Long id){
        return workerRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        workerRepository.deleteById(id);
    }

    @GetMapping("/all")
    public List<Worker> getAll(){
        return workerRepository.findAll();
    }

    @GetMapping("/getbyname/{name}")
    public Worker getByName(@PathVariable String name){
        return workerRepository.findByName(name).orElse(null);
    }

    @GetMapping("/getbypostname/{name}")
    public List<Worker> getByPostName(@PathVariable String name){
        return workerRepository.findAllByPostName(name);
    }

    @GetMapping("/getbyrestaurant/{id}")
    public List<Worker> geyByRestaurant(@PathVariable Long id){
        return workerRepository.findAllInRestaurant(id);
    }

}
