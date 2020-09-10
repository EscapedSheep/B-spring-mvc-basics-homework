package com.thoughtworks.capacity.gtb.mvc.repository;

import com.thoughtworks.capacity.gtb.mvc.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private List<User> userList;
    private AtomicInteger idCount;

    public UserRepository() {
        this.userList = new ArrayList<>();;
        this.idCount = new AtomicInteger(0);
    }

    public void addUser(User user) {
        user.setId(idGenerator());
        userList.add(user);
    }

    public Boolean isUserNameExisted(String name) {
        List<User> findUser = userList.stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        if (findUser.size() > 0) {
            return true;
        }
        return false;
    }

    public Optional<User> findUserByNameAndPassword(String name, String password) {
        List<User> findUser = userList.stream().filter(user -> user.getName().equals(name) && user.getPassword().equals(password)).collect(Collectors.toList());
        if (findUser.size() > 0) {
            return Optional.of(findUser.get(0));
        }
        return Optional.empty();
    }

    public List<User> getUserList() {
        return userList;
    }

    private int idGenerator() {
        return idCount.incrementAndGet();
    }
}
