package com.stockanalysis.service;

import com.stockanalysis.dto.UserDTO;
import java.util.Optional;

public interface UserService {
    UserDTO register(String username, String email, String password);
    UserDTO findByUsername(String username);
    UserDTO findById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
