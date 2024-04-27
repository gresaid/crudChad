package com.artem.crudchad.controllers.posts;

import com.artem.crudchad.dao.Posts;
import com.artem.crudchad.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PostsController {

  final PostRepository postRepository;

  public PostsController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @GetMapping("/posts")
  public ResponseEntity<List<Posts>> getAllPosts(@RequestParam(required = false) String title) {
    try {
      List<Posts> Posts = new ArrayList<>();

      if (title == null) {
        Posts.addAll(postRepository.findAll());
      } else {
        Posts.addAll(postRepository.findByTitleContainingIgnoreCase(title));
      }

      if (Posts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(Posts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<Posts> getPostsById(@PathVariable("id") long id) {
    Optional<Posts> PostsData = postRepository.findById(id);

    return PostsData.map(posts -> new ResponseEntity<>(posts, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/posts")
  public ResponseEntity<Posts> createPosts(@RequestBody Posts posts) {
    try {
      Posts _posts = postRepository.save(
          new Posts(posts.getTitle(), posts.getDescription(), false));
      return new ResponseEntity<>(_posts, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/posts/{id}")
  public ResponseEntity<Posts> updatePosts(@PathVariable("id") long id, @RequestBody Posts posts) {
    Optional<Posts> PostsData = postRepository.findById(id);

    if (PostsData.isPresent()) {
      Posts _posts = PostsData.get();
      _posts.setTitle(posts.getTitle());
      _posts.setDescription(posts.getDescription());
      _posts.setPublished(posts.isPublished());
      return new ResponseEntity<>(postRepository.save(_posts), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/posts/{id}")
  public ResponseEntity<HttpStatus> deletePosts(@PathVariable("id") long id) {
    try {
      postRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/posts")
  public ResponseEntity<HttpStatus> deleteAllPosts() {
    try {
      postRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/posts/published")
  public ResponseEntity<List<Posts>> findByPublished() {
    try {
      List<Posts> Posts = postRepository.findByPublished(true);

      if (Posts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(Posts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
