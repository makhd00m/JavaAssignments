package com.scaler.blogapi.users;

import org.junit.jupiter.api.Assertions;
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

        Assertions.assertNotNull(user.getId());

        System.out.println(user.toString());
        System.out.println(usersRepository.findByEmail("bhanuelidindi@gmail.com").toString() + "\n" + "Ok, Bye!");
    }

    @Test
    public void findByUsername() {
        UserEntity userEntity1 = new UserEntity(
                "bhanuprakash123",
                "bhanuelidindi@gmail.com",
                "hyderabadfasntastic#2@1",
                "bhanu is a nice guy!!!"
        );
        UserEntity userEntity2 = new UserEntity(
                "hayatabbas225",
                "smhayat225@gmail.com",
                "delhimetro!12@1",
                "hayat is a nice guy!!!"
        );
        usersRepository.save(userEntity1);
        usersRepository.save(userEntity2);
        var user1 = usersRepository.findByUsername("bhanuprakash123");
        var user2 = usersRepository.findByUsername("hayatabbas225");

        Assertions.assertEquals("bhanuelidindi@gmail.com", user1.email);
        Assertions.assertEquals("smhayat225@gmail.com", user2.email);

        System.out.println(user1.email);
        System.out.println(user2.email);
    }
}
