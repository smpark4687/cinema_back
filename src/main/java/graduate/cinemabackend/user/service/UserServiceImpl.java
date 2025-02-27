package graduate.cinemabackend.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import graduate.cinemabackend.common.dto.ResponseDTO;
import graduate.cinemabackend.user.dao.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public ResponseDTO login(Map<String, String> reqMap, HttpServletRequest httpServletRequest) {
        ResponseDTO res = new ResponseDTO();

        Map<String, String> resMap = userMapper.selectUserInfo(reqMap);

        if (resMap != null) {
            String pwd = reqMap.get("pwd");

            if (pwd.equals(resMap.get("mem_pwd"))) {
                res.setResCode(200);
                res.setResMsg("Login Success");
                // 수정 시작한 부분
                // 세션 삭제
                httpServletRequest.getSession().invalidate();
                // 세션 초기화 및 Attr 설정
                HttpSession session = httpServletRequest.getSession();
                session.setMaxInactiveInterval(1800);
                session.setAttribute("mem_no", resMap.get("mem_no"));
                session.setAttribute("mem_name", resMap.get("mem_name"));
                session.setAttribute("mem_id", resMap.get("mem_id"));
                session.setAttribute("mem_class", resMap.get("mem_class"));
                res.setData("resMap", resMap);
            } else {
                res.setResCode(300);
                res.setResMsg("ID 또는 PW가 일치하지 않습니다.");
            }
        } else {
            res.setResCode(300);
            res.setResMsg("ID 또는 PW가 일치하지 않습니다.");
        }
        return res;
    }

    @Override
    @Transactional
    public ResponseDTO signup(Map<String, Object> reqMap) { // 회원가입
        ResponseDTO res = new ResponseDTO();

        int result = userMapper.signup(reqMap);

        if (result == 1) {
            res.setResCode(200);
            res.setResMsg("회원가입 회원 정보 등록");
        } else {
            res.setResCode(300);
            res.setResMsg("회원가입 회원 정보 등록에 실패했습니다.");
        }

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO idCheck(String mem_id) { // id중복체크
        ResponseDTO res = new ResponseDTO();

        boolean isDuplicate = userMapper.idCheck(mem_id);

        if (!isDuplicate) {
            res.setResCode(200);
            res.setResMsg("사용가능한 아이디입니다.");
        } else {
            res.setResCode(300);
            res.setResMsg("이미 사용중인 아이디입니다.");
        }

        return res;
    }

    // 로그아웃 관련 코드
    @Override
    @Transactional
    public ResponseDTO logout(HttpServletRequest httpServletRequest) {
        ResponseDTO res = new ResponseDTO();

        httpServletRequest.getSession().invalidate();
        res.setResCode(200);
        res.setResMsg("logout successful");

        return res;

    }

    // 로그인 여부 확인
    public ResponseDTO auth(HttpServletRequest httpServletRequest) {
        ResponseDTO res = new ResponseDTO();

        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            res.setResCode(300);
            res.setResMsg("can't find user");
        } else {
            res.setResCode(200);
            res.setResMsg("Welcome User");
            res.setData("mem_no", session.getAttribute("mem_no"));
            res.setData("mem_name", session.getAttribute("mem_name"));
            res.setData("mem_class", session.getAttribute("mem_class"));
            res.setData("mem_id", session.getAttribute("mem_id"));
        }
        return res;
    }

    // 회원정보 조회
    @Override
    @Transactional
    public ResponseDTO userInfo(HttpServletRequest httpServletRequest) {
        ResponseDTO res = new ResponseDTO();

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            String mem_no = (String) session.getAttribute("mem_no");
            Map<String, Object> userInfo = userMapper.userInfo(mem_no);

            if (!userInfo.isEmpty()) {
                res.setResCode(200);
                res.setResMsg("회원정보 조회 성공");
                res.setData("userInfo", userInfo);
            } else {
                res.setResCode(300);
                res.setResMsg("회원정보 조회 실패");
            }
        } else {
            res.setResCode(300);
            res.setResMsg("로그인 후 이용해 주세요.");
        }
        return res;
    }

    // 회원 정보 수정
    @Override
    @Transactional
    public ResponseDTO modifyUserInfo(Map<String, Object> reqMap, HttpServletRequest httpServletRequest) {
        ResponseDTO res = new ResponseDTO();

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            String mem_no = (String) session.getAttribute("mem_no"); // 현재 아이디

            reqMap.put("mem_no", mem_no);

            int updateRow = userMapper.modifyUserInfo(reqMap);

            if (updateRow > 0) {
                res.setResCode(200);
                res.setResMsg("회원정보 수정 성공");
            } else {
                res.setResCode(300);
                res.setResMsg("회원정보 수정 실패");
            }
        } else {
            res.setResCode(300);
            res.setResMsg("로그인 후 이용해 주세요.");
        }
        return res;
    }

    // 회원탈퇴
    @Override
    @Transactional
    public ResponseDTO deleteAccount(HttpServletRequest httpServletRequest) {
        ResponseDTO res = new ResponseDTO();

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            String mem_no = (String) session.getAttribute("mem_no"); // 현재 아이디

            int deleteRow = userMapper.deleteAccount(mem_no);

            if (deleteRow > 0) {
                httpServletRequest.getSession().invalidate();
                res.setResCode(200);
                res.setResMsg("회원 탈퇴 성공");
            } else {
                res.setResCode(300);
                res.setResMsg("회원 탈퇴 실패");
            }
        } else {
            res.setResCode(300);
            res.setResMsg("로그인 후 이용해 주세요.");
        }
        return res;
    }

    // 선호 영화 조회
    @Override
    @Transactional
    public ResponseDTO likeMovie(HttpServletRequest httpServletRequest) {
        ResponseDTO res = new ResponseDTO();

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            String mem_no = (String) session.getAttribute("mem_no"); // 현재 아이디

            List<Map<String, Object>> likeMovieList = userMapper.likeMovie(mem_no);

            if (!likeMovieList.isEmpty()) {
                res.setResCode(200);
                res.setResMsg("선호 영화 리스트 조회 성공");
                res.setData("likeMovieList", likeMovieList);
            } else {
                res.setResCode(300);
                res.setResMsg("선호 영화 리스트 조회 실패");
            }

        } else {
            res.setResCode(300);
            res.setResMsg("로그인 후 이용해 주세요.");
        }
        return res;
    }
}
