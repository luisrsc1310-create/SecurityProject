package com.example.securityproject2.API.Controller;

import com.example.securityproject2.API.Entity.User;
import com.example.securityproject2.API.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Injeção de dependência do serviço via construtor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint para buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok) // Respondendo Baseado na lógica que a gente tem do Response, ao invés de somente O User, Se não tivermos Exceptions claro.
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para buscar informações formatadas do usuário
    @GetMapping("/{id}/info")
    public ResponseEntity<String> getUserInfo(@PathVariable Long id) {
        String info = userService.getUserInfo(id);
        return ResponseEntity.ok(info);
    }

    // Endpoint para cadastrar um novo usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Endpoint para atualizar os dados de um usuário
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestParam String username,
            @RequestParam String email) {
        try {
            User updatedUser = userService.updateUser(id, username, email);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
