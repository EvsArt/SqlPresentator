package data.repository;

import data.model.Post;

import java.util.Optional;

public interface PostRepository extends BaseRepository<Post, Integer> {

    Optional<Post> findByName(String name);

}