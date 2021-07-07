package com.canway.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canway.manager.dao.UserDao;
import com.canway.manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public Object search(String name, int page_index, int page_size) {
        Page<User> page = new Page<>(page_index, page_size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        IPage<User> iPage = this.userDao.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public Object add(User user) {
        return userDao.insert(user);
    }

    @Override
    public Object delete(int id) {
        return userDao.deleteById(id);
    }

    @Override
    public boolean login(String name, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        wrapper.eq("password",password);
        List<User> userList = userDao.selectList(wrapper);
        int s = userList.size();
        if (userList.size()> 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object get_user(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return userDao.selectOne(wrapper);
    }

    @Override
    public Object update(User user) {
        int id = user.getId();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return userDao.update(user, wrapper);
    }

}
