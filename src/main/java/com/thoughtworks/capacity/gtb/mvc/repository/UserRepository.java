package com.thoughtworks.capacity.gtb.mvc.repository;

import com.thoughtworks.capacity.gtb.mvc.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private List<User> userList;
    private AtomicInteger idCount;

    public UserRepository() {
        this.userList = new ArrayList<>();;
        this.idCount = new AtomicInteger(0);
    }

    public User addUser(User user) {
        user.setId(idGenerator());
        userList.add(user);
        return user;
    }

    public Boolean isUserNameExisted(String name) {
        return userList.stream().anyMatch(user -> user.getName().equals(name));

    }

    public Optional<User> findUserByNameAndPassword(String name, String password) {
        return userList.stream().filter(user -> user.getName().equals(name)).filter(user -> user.getPassword().equals(password)).findFirst();

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    private int idGenerator() {
        return idCount.incrementAndGet();
    }
}
