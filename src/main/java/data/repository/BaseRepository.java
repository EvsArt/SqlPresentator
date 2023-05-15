package data.repository;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    Optional<T> save(T obj);

}
