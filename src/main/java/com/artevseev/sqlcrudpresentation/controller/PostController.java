package com.artevseev.sqlcrudpresentation.controller;

import com.artevseev.sqlcrudpresentation.data.model.Post;
import com.artevseev.sqlcrudpresentation.data.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping("/save")
    public Post save(Post post){
        return postRepository.save(post).orElse(null);
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable Integer id){
        return postRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        postRepository.deleteById(id);
    }

    @GetMapping("/all")
    public List<Post> getAll(){
        return postRepository.findAll();
    }

    @GetMapping("/getbyname/{name}")
    public Post getByName(@PathVariable String name){
        return postRepository.findByName(name).orElse(null);
    }

    @GetMapping("/allnames")
    public List<String> getAllNames(){
        return postRepository.findAllPostNames();
    }

}
