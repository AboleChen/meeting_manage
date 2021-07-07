package com.canway.manager.controller;

import com.canway.manager.pojo.Equipment;
import com.canway.manager.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    @GetMapping("/find_all")
    public Object findAll() {
        return equipmentService.findAll();
    }

    @GetMapping("/delete")
    public Object delete(@RequestParam(value = "id") int id) {
        return equipmentService.delete(id);
    }

    @PostMapping("/add")
    public Object add(@RequestBody Equipment equipment) {
        return equipmentService.add(equipment);
    }
}
