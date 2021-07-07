package com.canway.manager.service;

import com.canway.manager.dao.DepartmentDao;
import com.canway.manager.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;


    @Override
    public Object findAll() {
        return departmentDao.selectList(null);
    }

    @Override
    public Object delete(int id) {
        return departmentDao.deleteById(id);
    }

    @Override
    public Object add(Department department) {
        return departmentDao.insert(department);
    }


}
