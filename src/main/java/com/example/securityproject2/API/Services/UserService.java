package com.example.securityproject2.API.Services;

import com.example.securityproject2.API.Entity.User;
import com.example.securityproject2.API.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Instância do repositório para acesso ao banco de dados
    private final UserRepository userRepository;

    // Injeção de dependência via construtor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Retorna todos os usuários cadastrados
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Busca um usuário pelo ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Busca um usuário pelo nome de usuário (username)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Busca um usuário pelo email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Salva ou atualiza uma entidade User diretamente
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Cria um novo User recebendo as variáveis individuais e persistindo
    public User createUser(String username, String password, String email) {
        User newUser = new User(username, password, email);
        return userRepository.save(newUser);
    }

    // Exemplo de como puxar as informações e variáveis de um User existente
    public String getUserInfo(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Extraindo as variáveis da entidade User
            Long userId = user.getId();
            String username = user.getUsername();
            String email = user.getEmail();

            return String.format("ID: %d | Username: %s | Email: %s", userId, username, email);
        }

        return "Usuário não encontrado";
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (updatedUser.getUsername() != null) {
                user.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null) {
                user.setPassword(updatedUser.getPassword());
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    public User updateUser(Long id, String newUsername, String newEmail) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUsername);
            user.setEmail(newEmail);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    // Deleta um usuário por ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
