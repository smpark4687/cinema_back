package graduate.cinemabackend.user.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import graduate.cinemabackend.common.dto.ResponseDTO;
import graduate.cinemabackend.user.dao.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public ResponseDTO login(Map<String, String> reqBody) {
        ResponseDTO res = new ResponseDTO();

        Map<String, String> resMap = userMapper.selectUserInfo(reqBody);

        if (resMap != null) {
            String pwd = reqBody.get("pwd");

            if (pwd.equals(resMap.get("mem_pwd"))) {
                res.setResCode(200);
                res.setResMsg("Login Success");
                res.setData("userInfo", resMap);
                resMap.remove("mem_pwd");
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
    public ResponseDTO join(Map<String, Object> reqBody) {
        ResponseDTO res = new ResponseDTO();
        try {
            int result = userMapper.join(reqBody);

            if (result == 1) {
                res.setResCode(200);
                res.setResMsg("회원가입 회원 정보 등록");
            } else {
                res.setResCode(300);
                res.setResMsg("회원가입 회원 정보 등록에 실패했습니다.");
            }
        } catch (DataIntegrityViolationException e) {
            res.setResCode(500);
            res.setResMsg("이미 등록되어 있는 정보 입니다.");
            
        }
        return res;
    }
}
