package com.canway.manager.service;

import com.canway.manager.pojo.Department;

public interface DepartmentService {

    Object findAll();

    Object delete(int id);

    Object add(Department department);

}
