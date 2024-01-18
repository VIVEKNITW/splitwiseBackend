package com.project.splitwise;

import com.project.splitwise.exceptions.UserAlreadyExistsException;
import com.project.splitwise.models.User;
import com.project.splitwise.models.UserStatus;
import com.project.splitwise.repositories.UserRepository;
import com.project.splitwise.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testRegisterUser() throws UserAlreadyExistsException{
        String userName = "vivek";
        String phoneNumber = "123456789";
        String password = "password";

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Existing user");
        existingUser.setPhone(phoneNumber);
        existingUser.setPassword("exisitng password");
        existingUser.setUserStatus(UserStatus.ACTIVE);

        when(userRepository.findByPhone(phoneNumber)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(InvocationOnMock::getArguments);

        // act
        User registerUser = userService.registerUser(userName, phoneNumber, password);

        assertNotNull(registerUser);
        assertEquals(userName, registerUser.getName());
        assertEquals(phoneNumber, registerUser.getPhone());
        assertEquals(password, registerUser.getPassword());
        assertEquals(UserStatus.ACTIVE, registerUser.getUserStatus());

        verify(userRepository, times(1)).findByPhone(phoneNumber);
        verify(userRepository, times(1)).save(any(User.class));
    }

}
