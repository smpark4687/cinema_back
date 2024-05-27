package graduate.cinemabackend.schedule.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graduate.cinemabackend.common.dto.ResponseDTO;
import graduate.cinemabackend.schedule.service.ScheduleService;



@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    
    @Autowired
    ScheduleService scheduleService;

    //영화 스케줄 비교 - test
    @GetMapping(value="/compare")
    public ResponseDTO compare(@RequestBody Map<String, Object> reqMap) {
        ResponseDTO res = scheduleService.compare(reqMap);

        return res;
    }
    
}
