package com.canway.manager.service;

import com.canway.manager.pojo.MeetingRoom;

import java.util.ArrayList;

public interface MeetingRoomService {

    Object find_all();

    Object search(String name, int page_index, int page_size);

    Object add(MeetingRoom meetingRoom);

    Object delete(int id);
}
