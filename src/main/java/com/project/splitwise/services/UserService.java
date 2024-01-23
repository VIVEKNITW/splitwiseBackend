package com.project.splitwise.services;

import com.project.splitwise.exceptions.UserAlreadyExistsException;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.repositories.UserRepository;
import com.project.splitwise.models.User;
import com.project.splitwise.models.UserStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String userName, String phoneNumber,
                             String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByPhone(phoneNumber);

        if (userOptional.isPresent()) {
            System.out.println("isPresent is true");
            if (userOptional.get().getUserStatus().equals(UserStatus.ACTIVE)) {
                System.out.println("isPresent is true & Active");
                throw new UserAlreadyExistsException();
            } else {
                User user = userOptional.get();
                user.setUserStatus(UserStatus.ACTIVE);
                user.setName(userName);
                user.setPassword(password);
                return userRepository.save(user);
            }
        }

        User user = new User();
        user.setPhone(phoneNumber);
        user.setName(userName);
        user.setPassword(password);
        user.setUserStatus(UserStatus.ACTIVE);

        return userRepository.save(user);
    }

    public User updateUser(long id, String userName, String phoneNumber,
                           String password) throws UserDoesntExistException{
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserDoesntExistException("user with id= "+id+"doesn't exist. So can't update");
        }

        User user = userOptional.get();
        user.setName(userName);
        user.setPhone(phoneNumber);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User findUser(long id) throws UserDoesntExistException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserDoesntExistException("User with id= "+id+"doesn't exist");
        }
        return userOptional.get();
    }

    public void deleteUser(long id) throws UserDoesntExistException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserDoesntExistException("User with id= "+id+"doesn't exist");
        }
        userRepository.delete(userOptional.get());
    }
}

