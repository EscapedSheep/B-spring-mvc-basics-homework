package com.thoughtworks.capacity.gtb.mvc.service;

import com.thoughtworks.capacity.gtb.mvc.domain.User;
import com.thoughtworks.capacity.gtb.mvc.exception.UserNameAlreadyExistedException;
import com.thoughtworks.capacity.gtb.mvc.exception.UserNameOrPasswordNotValidException;
import com.thoughtworks.capacity.gtb.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        Boolean isUserNameExisted = userRepository.isUserNameExisted(user.getName());
        if (isUserNameExisted) {
            throw new UserNameAlreadyExistedException();
        }
        userRepository.addUser(user);
    }

    public User login(String name, String password) {
        Optional<User> findUser = userRepository.findUserByNameAndPassword(name, password);
        if (!findUser.isPresent()) {
            throw new UserNameOrPasswordNotValidException();
        }
        return findUser.get();
    }
}
