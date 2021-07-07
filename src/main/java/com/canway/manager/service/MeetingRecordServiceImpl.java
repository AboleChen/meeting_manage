package com.canway.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canway.manager.dao.EquipmentDao;
import com.canway.manager.dao.MeetingRecordDao;
import com.canway.manager.dao.MeetingRoomDao;
import com.canway.manager.pojo.Equipment;
import com.canway.manager.pojo.MeetingRecord;
import com.canway.manager.pojo.MeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class MeetingRecordServiceImpl implements MeetingRecordService {

    @Autowired
    private MeetingRecordDao meetingRecordDao;
    private MeetingRoomDao meetingRoomDao;
    private EquipmentDao equipmentDao;

    public MeetingRecordServiceImpl(MeetingRoomDao meetingRoomDao, EquipmentDao equipmentDao) {
        this.meetingRoomDao = meetingRoomDao;
        this.equipmentDao = equipmentDao;
    }


    @Override
    public Object search(String name, int page_index, int page_size) {
        Page<MeetingRecord> page = new Page<>(page_index, page_size);
        QueryWrapper<MeetingRecord> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        IPage<MeetingRecord> iPage = this.meetingRecordDao.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public Object add(MeetingRecord meetingRecord) {
        return meetingRecordDao.insert(meetingRecord);
    }

    @Override
    public Object filter_room(Date begin, Date end) {
        List<MeetingRoom> meetingRoomList = this.meetingRoomDao.selectList(null);
        List<MeetingRoom> meeting_list = new ArrayList<MeetingRoom>();
        for (int i=0;i<meetingRoomList.size();i++) {
            QueryWrapper<MeetingRecord> wrapper = new QueryWrapper<>();
            wrapper.eq("room", meetingRoomList.get(i).getId());
            List<MeetingRecord> meetingRecordList = this.meetingRecordDao.selectList(wrapper);
            Date begin_time = null;
            Date end_time = null;
            int begin_1 = 0;
            int begin_2 = 0;
            int end_1 = 0;
            int end_2 = 0;
            Boolean no_time = false;
            for (int j=0;j<meetingRecordList.size();j++) {
                begin_time = meetingRecordList.get(j).getBegin();
                end_time = meetingRecordList.get(j).getEnd();
                begin_1 = begin_time.compareTo(begin);
                begin_2 = begin_time.compareTo(end);
                end_1 = begin.compareTo(end_time);
                end_2 = end.compareTo(end_time);
                if (begin_1 + begin_2 == 2 || end_1 + end_2 ==2) {
                    no_time = false;
                } else {
                    no_time = true;
                }
            }
            if (!no_time) {
                meeting_list.add(meetingRoomList.get(i));
            }
        }
        return meeting_list;
    }

    @Override
    public Object filter_equip(Date begin, Date end) {
        List<Equipment> equipmentList = this.equipmentDao.selectList(null);
        List<Equipment> equip_list = new ArrayList<Equipment>();
        for (int i=0;i<equipmentList.size();i++) {
            QueryWrapper<MeetingRecord> wrapper = new QueryWrapper<>();
            wrapper.eq("equipment", equipmentList.get(i).getId());
            List<MeetingRecord> meetingRecordList = this.meetingRecordDao.selectList(wrapper);
            Date begin_time = null;
            Date end_time = null;
            int begin_1 = 0;
            int begin_2 = 0;
            int end_1 = 0;
            int end_2 = 0;
            Boolean no_time = false;
            for (int j=0;j<meetingRecordList.size();j++) {
                begin_time = meetingRecordList.get(j).getBegin();
                end_time = meetingRecordList.get(j).getEnd();
                begin_1 = begin_time.compareTo(begin);
                begin_2 = begin_time.compareTo(end);
                end_1 = begin.compareTo(end_time);
                end_2 = end.compareTo(end_time);
                if (begin_1 + begin_2 == 2 || end_1 + end_2 ==2) {
                    no_time = false;
                } else {
                    no_time = true;
                }
            }
            if (!no_time) {
                equip_list.add(equipmentList.get(i));
            }
        }
        return equip_list;
    }

}
