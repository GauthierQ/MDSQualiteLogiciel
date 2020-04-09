package com.tactfactory.monprojetsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tactfactory.monprojetsb.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
