package com.artem.crudchad.repository;

import com.artem.crudchad.dao.Posts;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {
  List<Posts> findByPublished(boolean published);

  List<Posts> findByTitleContainingIgnoreCase(String title);
}
