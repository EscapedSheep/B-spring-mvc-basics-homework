package com.thoughtworks.capacity.gtb.mvc.repository;

import com.thoughtworks.capacity.gtb.mvc.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    private int idGenerator() {
        return idCount.incrementAndGet();
    }
}
