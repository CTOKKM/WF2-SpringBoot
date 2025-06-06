package kr.ac.hansung.cse.hellospringdatajpa.repository;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Product findByName(String name);
    
    List<Product> findByNameContaining(String name, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    List<Product> searchByName(@Param("keyword") String keyword);
} 