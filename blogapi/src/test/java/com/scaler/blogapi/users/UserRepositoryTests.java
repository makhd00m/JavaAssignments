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
        UserEntity userEntity = UserEntity.builder()
                .username("bhanuprakash123")
                .email("bhanuelidindi@gmail.com")
                .password("hyderabadfasntastic#2@1")
                .build();
        var user = usersRepository.save(userEntity);
        Assertions.assertNotNull(user.getId());

        System.out.println(user.toString());
        System.out.println(usersRepository.findByEmail("bhanuelidindi@gmail.com").toString() + "\n" + "Ok, Bye!");
    }

    @Test
    public void findByUsername() {
        UserEntity userEntity1 = UserEntity.builder()
                .username("bhanuprakash123")
                .email("bhanuelidindi@gmail.com")
                .password("hyderabadfasntastic#2@1")
                .bio("bhanu is a nice guy!!!")
                .build();
        UserEntity userEntity2 = new UserEntity(
                "hayatabbas225",
                "smhayat225@gmail.com",
                "delhimetro!12@1"
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
