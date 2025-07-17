package com.sweetshop.repositories;

import com.sweetshop.entities.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Repository interface for accessing Sweet entity data from the database.
//Extends JpaRepository to provide basic CRUD operations.
//Also includes custom query methods based on name, category, and price range.
public interface SweetRepository extends JpaRepository<Sweet , Long> {

    //Retrieves a list of sweets matching the given name.
    List<Sweet> findByName(String name);

    //Retrieves a list of sweets that belong to the given category.
    List<Sweet> findByCategory(String category);

    //Retrieves a list of sweets whose price falls within the given range.
    List<Sweet> findByPriceBetween(double minPrice, double maxPrice);
}
