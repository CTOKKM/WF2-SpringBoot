package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping({"", "/"})
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products/index";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/new";
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        if (product.getPrice() < 0) {
            redirectAttributes.addFlashAttribute("error", "가격은 0 이상이어야 합니다.");
            return "redirect:/products/new";
        }
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("success", "상품이 등록되었습니다.");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, 
                              RedirectAttributes redirectAttributes) {
        if (product.getPrice() < 0) {
            redirectAttributes.addFlashAttribute("error", "가격은 0 이상이어야 합니다.");
            return "redirect:/products/edit/" + id;
        }
        product.setId(id);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("success", "상품이 수정되었습니다.");
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "상품이 삭제되었습니다.");
        return "redirect:/products";
    }
}
