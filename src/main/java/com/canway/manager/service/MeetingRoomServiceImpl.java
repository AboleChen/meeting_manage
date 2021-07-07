package com.canway.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canway.manager.dao.MeetingRoomDao;
import com.canway.manager.pojo.MeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    private MeetingRoomDao meetingRoomDao;

    @Override
    public Object search(String name, int page_index, int page_size) {
        Page<MeetingRoom> page = new Page<>(page_index, page_size);
        QueryWrapper<MeetingRoom> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        IPage<MeetingRoom> iPage = this.meetingRoomDao.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public Object add(MeetingRoom meetingRoom) {
        return meetingRoomDao.insert(meetingRoom);
    }

    @Override
    public Object delete(int id) {
        return meetingRoomDao.deleteById(id);
    }

    @Override
    public Object find_all() {
        return meetingRoomDao.selectList(null);
    }
}
