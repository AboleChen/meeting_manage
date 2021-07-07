package com.canway.manager.service;

import com.canway.manager.pojo.User;

public interface UserService {

    Object search(String name, int page_index, int page_size);

    Object add(User user);

    Object delete(int id);

    boolean login(String name, String password);

    Object get_user(String name);

    Object update(User user);
}
