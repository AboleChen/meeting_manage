package com.canway.manager.service;

import com.canway.manager.pojo.Equipment;

public interface EquipmentService {

    Object delete(int id);

    Object add(Equipment equipment);

    Object findAll();
}
