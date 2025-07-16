package com.sweetshop.repositories;

import com.sweetshop.entities.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetRepository extends JpaRepository<Sweet , Long> {
}
