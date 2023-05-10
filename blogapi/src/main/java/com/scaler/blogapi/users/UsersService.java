package com.scaler.blogapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(@Autowired UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserEntity createUser(String username, String password, String email) {
        // TODO 02:
        //  1. create a UserEntity object with given details
        //  2. save the user using usersRepository.save()
        //    a. validate username min/max length
        //    b. hash the password
        //    c. validate the email format
        //  3. return the saved user
    }
}