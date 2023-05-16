package com.artevseev.sqlcrudpresentation.data.repository;

import com.artevseev.sqlcrudpresentation.data.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends BaseRepository<Post, Integer> {

    Optional<Post> findByName(String name);
    List<String> findAllPostNames();

}