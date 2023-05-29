package com.scaler.blogapi.users;

import com.scaler.blogapi.security.TokenService;
import com.scaler.blogapi.users.dtos.CreateUserResponseDTO;
import com.scaler.blogapi.users.dtos.UserLoginDTO;
import com.scaler.blogapi.users.dtos.UserResponseDTO;
import com.scaler.blogapi.users.dtos.UserSignupDTO;
import org.apache.el.parser.Token;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    public UsersController(
            @Autowired UsersService usersService,
            @Autowired ModelMapper modelMapper,
            @Autowired TokenService tokenService
            ) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    ResponseEntity<CreateUserResponseDTO> signupUser(@RequestBody UserSignupDTO userSignupDTO) {
        // TODO 01:
        //  1. create a UserSignupDTO (containing username, email, password)
        //  2. call usersService.createUser() with those details
        //  3. respond with 202 ACCEPTED if user is created successfully
        var savedUser = usersService.createUser(
                userSignupDTO.getUsername(),
                userSignupDTO.getPassword(),
                userSignupDTO.getEmail()
        );
        var userResponse = modelMapper.map(savedUser, CreateUserResponseDTO.class);
        userResponse.setToken(tokenService.createAuthToken(savedUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<CreateUserResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        // TODO 03:
        //  1. create a UserLoginDTO (containing username, password)
        //  2. call usersService.loginUser() with those details
        //  3. respond with 202 ACCEPTED if user is logged in successfully
        var savedUser = usersService.loginUser(
                userLoginDTO.getUsername(),
                userLoginDTO.getPassword()
        );
        var userResponse = modelMapper.map(savedUser, CreateUserResponseDTO.class);
        userResponse.setToken(tokenService.createAuthToken(savedUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @PatchMapping("/{id}")
    ResponseEntity<UserResponseDTO> updateUser() {
        // TODO 04:
        //  1. create a UserUpdateDTO (containing email, password, bio)
        //  2. call usersService.updateUser() with those details
        //  3. check that client sends a token which validates this user
        //  4. respond with 202 ACCEPTED if user is logged in successfully
        return null;
    }
}
