package com.canway.manager.controller;

import com.canway.manager.pojo.Department;
import com.canway.manager.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/find_all")
    public Object findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/delete")
    public Object delete(@RequestParam(value = "id") int id) {
        return departmentService.delete(id);
    }

    @PostMapping("/add")
    public Object add(@RequestBody Department department) {
        return departmentService.add(department);
    }

}
