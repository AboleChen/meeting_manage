package com.canway.manager.controller;

import com.canway.manager.pojo.MeetingRecord;
import com.canway.manager.service.MeetingRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/meetingRecord")
public class MeetingRecordController {

    @Autowired
    private MeetingRecordService meetingRecordService;

    @GetMapping("/search")
    public Object search(@RequestParam(value = "name") String name, int page_index, int page_size) {
        return meetingRecordService.search(name, page_index, page_size);
    }

    @PostMapping("/add")
    public Object add(@RequestBody  MeetingRecord meetingRecord) {
        return meetingRecordService.add(meetingRecord);
    }

    @GetMapping("/filter_room")
    public Object filter_room(@RequestParam(value = "begin_time") String begin_time, String end_time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = sdf.parse(begin_time);
        Date end = sdf.parse(end_time);
        return meetingRecordService.filter_room(begin, end);
    }

    @GetMapping("/filter_equip")
    public Object filter_equip(@RequestParam(value = "begin_time") String begin_time, String end_time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = sdf.parse(begin_time);
        Date end = sdf.parse(end_time);
        return meetingRecordService.filter_equip(begin, end);
    }
}
