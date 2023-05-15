package data.repository;

import data.model.Worker;

import java.util.Optional;

public interface WorkerRepository extends BaseRepository<Worker, Long> {

    Optional<Worker> findByName(String name);

}
