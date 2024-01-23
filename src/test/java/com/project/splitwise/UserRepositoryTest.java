package com.project.splitwise;


import com.project.splitwise.models.User;
import com.project.splitwise.models.UserStatus;
import com.project.splitwise.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByPhone(){
        User user = new User();
        user.setName("vivek");
        user.setPhone("123456789");
        user.setPassword("password");
        user.setUserStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        // act
        Optional<User> foundUser = userRepository.findByPhone("123456789");

        // assert
        assertTrue(foundUser.isPresent());
        assertEquals("vivek", foundUser.get().getName());
        assertEquals("123456789", foundUser.get().getPhone());
        assertEquals("password", foundUser.get().getPassword());
        assertEquals(UserStatus.ACTIVE, foundUser.get().getUserStatus());

    }
}
