package graduate.cinemabackend.detail.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import graduate.cinemabackend.common.dto.ResponseDTO;
import graduate.cinemabackend.detail.dao.DetailMapper;

@Service
public class DetailServiceImpl implements DetailService{
    
    @Autowired
    DetailMapper detailMapper;

    @Override
    @Transactional
    public ResponseDTO getDetailInfo(String mov_no) {
        ResponseDTO res = new ResponseDTO();

        Map<String, Object> detailInfo = detailMapper.getDetailInfo(mov_no);

        if(!detailInfo.isEmpty()){
            res.setResCode(200);
            res.setResMsg("상세정보 가져오기 성공");
            res.setData("detailInfo", detailInfo);
        } else{
            res.setResCode(300);
            res.setResMsg("상세정보 가져오기 실패");
        }

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO getReview(String mov_no) {
        ResponseDTO res = new ResponseDTO();

        List<Map<String, Object>> reviewList = detailMapper.getReview(mov_no);

        if(!reviewList.isEmpty()){
            res.setResCode(200);
            res.setResMsg("영화 리뷰 가져오기 성공");
            res.setData("reviewList", reviewList);
        } else{
            res.setResCode(300);
            res.setResMsg("영화 리뷰 가져오기 실패");
        }

        return res;
    }
}
