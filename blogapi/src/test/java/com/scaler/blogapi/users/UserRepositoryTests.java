package com.scaler.blogapi.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired private UsersRepository usersRepository;

    @Test
    public void createUser() {
        UserEntity userEntity = new UserEntity(
                "bhanuprakash123",
                "bhanuelidindi@gmail.com",
                "hyderabadfasntastic#2@1",
                "bhanu is a nice guy!!!"
        );
        var user = usersRepository.save(userEntity);
        System.out.println(user.toString());

        System.out.println(usersRepository.findByEmail("bhanuelidindi@gmail.com").toString() + "\n" + "Ok, Bye!");
    }
}
