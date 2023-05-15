package com.scaler.blogapi.users;

import com.scaler.blogapi.users.dtos.UserResponseDTO;
import com.scaler.blogapi.users.dtos.UserSignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponseDTO> signupUser(
            @RequestBody UserSignupDTO userSignupDTO
    ) {
        // TODO 01:
        //  1. create a UserSignupDTO (containing username, email, password)
        //  2. call usersService.createUser() with those details
        //  3. respond with 202 ACCEPTED if user is created successfully
        UserEntity userEntity = usersService.createUser(
                userSignupDTO.getUsername(),
                userSignupDTO.getPassword(),
                userSignupDTO.getEmail()
        );
        UserResponseDTO userResponseDTO = new UserResponseDTO().createDTO(userEntity);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponseDTO> loginUser() {
        // TODO 03:
        //  1. create a UserLoginDTO (containing username, password)
        //  2. call usersService.loginUser() with those details
        //  3. respond with 202 ACCEPTED if user is logged in successfully
        return null;
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
