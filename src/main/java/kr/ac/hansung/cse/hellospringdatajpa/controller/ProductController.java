package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping({"", "/"})
    public String listProducts(Model model) {
        try {
            logger.info("Attempting to fetch all products");
            List<Product> products = productRepository.findAll();
            logger.info("Successfully fetched {} products", products.size());
            
            if (products.isEmpty()) {
                logger.info("No products found in the database");
                model.addAttribute("message", "등록된 상품이 없습니다.");
            }
            
            model.addAttribute("products", products);
            return "products/index";
        } catch (DataAccessException e) {
            logger.error("Database error while fetching products: ", e);
            model.addAttribute("error", "데이터베이스 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            model.addAttribute("products", Collections.emptyList());
            return "products/index";
        } catch (Exception e) {
            logger.error("Unexpected error while fetching products: ", e);
            model.addAttribute("error", "상품 목록을 불러오는 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            model.addAttribute("products", Collections.emptyList());
            return "products/index";
        }
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/new";
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                redirectAttributes.addFlashAttribute("error", "가격은 0 이상이어야 합니다.");
                return "redirect:/products/new";
            }
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("success", "상품이 등록되었습니다.");
            return "redirect:/products";
        } catch (Exception e) {
            logger.error("Error creating product: ", e);
            redirectAttributes.addFlashAttribute("error", "상품 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/products/new";
        }
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        try {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
            model.addAttribute("product", product);
            return "products/edit";
        } catch (Exception e) {
            logger.error("Error fetching product for edit: ", e);
            return "redirect:/products";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, 
                              RedirectAttributes redirectAttributes) {
        try {
            if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                redirectAttributes.addFlashAttribute("error", "가격은 0 이상이어야 합니다.");
                return "redirect:/products/edit/" + id;
            }
            product.setId(id);
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("success", "상품이 수정되었습니다.");
            return "redirect:/products";
        } catch (Exception e) {
            logger.error("Error updating product: ", e);
            redirectAttributes.addFlashAttribute("error", "상품 수정 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/products/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "상품이 삭제되었습니다.");
            return "redirect:/products";
        } catch (Exception e) {
            logger.error("Error deleting product: ", e);
            redirectAttributes.addFlashAttribute("error", "상품 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/products";
        }
    }
}
