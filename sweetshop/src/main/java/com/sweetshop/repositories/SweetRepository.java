package com.sweetshop.repositories;

import com.sweetshop.entities.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SweetRepository extends JpaRepository<Sweet , Long> {
    List<Sweet> findByName(String name);
    List<Sweet> findByCategory(String category);

}
