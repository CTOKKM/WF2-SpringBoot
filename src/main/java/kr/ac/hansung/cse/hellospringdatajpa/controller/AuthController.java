package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repository.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String name,
                             RedirectAttributes redirectAttributes) {
        if (userRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "이미 사용 중인 이메일입니다.");
            return "redirect:/register";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        Role userRole = roleRepository.findByName("USER")
            .orElseThrow(() -> new RuntimeException("USER 역할을 찾을 수 없습니다."));
        user.addRole(userRole);

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "회원가입이 완료되었습니다. 로그인해주세요.");
        return "redirect:/login";
    }
} 