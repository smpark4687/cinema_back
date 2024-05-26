package graduate.cinemabackend.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graduate.cinemabackend.common.dto.ResponseDTO;
import graduate.cinemabackend.common.service.CommonService;

@CrossOrigin
@RestController
@RequestMapping("common")
public class CommonController { //공통으로 처리할거

    @Autowired
    CommonService commonService;

    @GetMapping("/crawling") // 크롤링으로 영화 정보 DB에 넣기
    public ResponseDTO movieCrawling() {
       ResponseDTO res = commonService.movieCrawling();

       return res;
    }
    
}
