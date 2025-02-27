package graduate.cinemabackend.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import graduate.cinemabackend.board.dao.BoardMapper;
import graduate.cinemabackend.common.dto.ResponseDTO;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper boardMapper;

    @Override
    @Transactional
    public ResponseDTO notiList() { // 공지사항 목록
        ResponseDTO res = new ResponseDTO();

        List<Map<String, Object>> notiList = boardMapper.notiList();

        if (!notiList.isEmpty()) {
            res.setResCode(200);
            res.setResMsg("공지사항 목록 가져오기 성공");
            res.setData("notiList", notiList);
        } else {
            res.setResCode(300);
            res.setResMsg("공지사항 목록 가져오기 실패");
        }
        return res;
    }

    @Override
    @Transactional
    public ResponseDTO detailNoti(String noti_no) { // 공지사항 상세보기
        ResponseDTO res = new ResponseDTO();

        int updateRow = boardMapper.increaseCnt(noti_no); // 조회수 증가
        if (updateRow > 0) {
            Map<String, Object> detailNoti = boardMapper.detailNoti(noti_no);

            if (!detailNoti.isEmpty()) {
                res.setResCode(200);
                res.setResMsg("공지사항 상세보기 가져오기 성공");
                res.setData("detailNoti", detailNoti);
            } else {
                res.setResCode(300);
                res.setResMsg("공지사항 상세보기 가져오기 실패");
            }
        } else {
            res.setResCode(300);
            res.setResMsg("조회수 증가 실패");
        }
        return res;
    }

    @Override
    @Transactional
    public ResponseDTO qnaList() { // 문의사항 목록
        ResponseDTO res = new ResponseDTO();

        List<Map<String, Object>> qnaList = boardMapper.qnaList();

        if (!qnaList.isEmpty()) {
            res.setResCode(200);
            res.setResMsg("문의사항 목록 가져오기 성공");
            res.setData("qnaList", qnaList);
        } else {
            res.setResCode(300);
            res.setResMsg("문의사항 목록 가져오기 실패");
        }

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO detailQna(String qna_no) { // 문의사항 상세보기
        ResponseDTO res = new ResponseDTO();

        Map<String, Object> detailQna = boardMapper.detailQna(qna_no);

        if (!detailQna.isEmpty()) {
            res.setResCode(200);
            res.setResMsg("문의사항 상세보기 가져오기 성공");
            res.setData("detailQna", detailQna);
        } else {
            res.setResCode(300);
            res.setResMsg("문의사항 상세보기 가져오기 실패");
        }
        return res;
    }

    @Override
    @Transactional
    public ResponseDTO createQna(Map<String, Object> reqMap) { // 문의사항 등록
        ResponseDTO res = new ResponseDTO();

        int insertRow = boardMapper.createQna(reqMap);

        if (insertRow > 0) {
            res.setResCode(200);
            res.setResMsg("문의사항 등록 성공");
        } else {
            res.setResCode(300);
            res.setResMsg("문의사항 등록 실패");
        }

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO deleteQna(Map<String, Object> reqMap) { // 문의사항 삭제
        ResponseDTO res = new ResponseDTO();

        int deleteRow = boardMapper.deleteQna(reqMap);

        if (deleteRow > 0) {
            res.setResCode(200);
            res.setResMsg("문의사항 삭제 성공");
        } else {
            res.setResCode(300);
            res.setResMsg("문의사항 삭제 실패");
        }

        return res;
    }
}
