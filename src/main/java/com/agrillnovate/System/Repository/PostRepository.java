package com.agrillnovate.System.Repository;

import com.agrillnovate.System.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByThreadThreadId(Long threadId);
    void deleteByThreadThreadId(Long threadId);  // Deletes posts by thread ID

}
