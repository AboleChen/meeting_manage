package com.canway.manager.controller;

import com.canway.manager.pojo.MeetingRoom;
import com.canway.manager.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/meetingRoom")
public class MeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @GetMapping("/search")
    public Object search(@RequestParam(value = "name") String name, int page_index, int page_size) {
        return meetingRoomService.search(name, page_index, page_size);
    }

    @PostMapping("/add")
    public Object add(@RequestBody  MeetingRoom meetingRoom) {
        return meetingRoomService.add(meetingRoom);
    }

    @GetMapping("/delete")
    public Object add(@RequestParam(value = "id") int id) {
        return meetingRoomService.delete(id);
    }

    @GetMapping("/find_all")
    public Object find_all() {
        return meetingRoomService.find_all();
    }
}
