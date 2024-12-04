package com.agrillnovate.System.Repository;

import com.agrillnovate.System.model.ResearchImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchImageRepository extends JpaRepository<ResearchImage, Long> {
}
