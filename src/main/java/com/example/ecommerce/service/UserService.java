package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.RegisterRequest;
import com.example.ecommerce.dto.response.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
}
