package com.canway.manager.service;

import com.canway.manager.dao.EquipmentDao;
import com.canway.manager.pojo.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentDao equipmentDao;

    @Override
    public Object delete(int id) {
        return equipmentDao.deleteById(id);
    }

    @Override
    public Object add(Equipment equipment) {
        return equipmentDao.insert(equipment);
    }

    @Override
    public Object findAll() {
       return equipmentDao.selectList(null);
    }
}
