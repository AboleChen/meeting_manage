package com.canway.manager.service;

import com.canway.manager.pojo.MeetingRecord;

import java.util.Date;

public interface MeetingRecordService {


    Object search(String name, int page_index, int page_size);

    Object add(MeetingRecord meetingRecord);

    Object filter_room(Date begin, Date end);

    Object filter_equip(Date begin, Date end);
}
