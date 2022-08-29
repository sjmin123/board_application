package com.pproject.board.sevice;

import com.pproject.board.dto.IdDto;
import com.pproject.board.entity.Idd;
import com.pproject.board.repository.IdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service //서비스 선언. (서비스 객체를 스프링 부트에 생성)
public class IdService {

    //여기를 static으로 하는게 맞나?
    @Autowired
    private IdRepository idRepository;


    public Idd createId(IdDto idDto) {
        Idd idd=idDto.toEntity();
        log.info(idd.toString());
        log.info(idDto.toString());
//        아이디를 입력하지 않았을 떄
        if(idd.getId()==null || idd.getPassword()==null){
            log.info("id를 입력하지 않았음!");
            return null;
        }
        //이미 아이디가 있을 때
        if(idRepository.findById(idd.getId()).isPresent()){
            log.info("아이디가 이미 있음!");
            return null;
        }
        log.info("조건들 확인 완료!");
        idRepository.save(idd);
        log.info("db에 저장 완료!");
        return idd;
    }

    public Idd connect(IdDto idDto){
        Idd idd=idDto.toEntity();
        //db에서 id찾기
        Idd compareIdd=idRepository.findById(idd.getId()).orElse(null);
        //id와 그 id의 비밀번호가 같은지 확인
        if(compareIdd!=null && idd.getPassword().equals(compareIdd.getPassword())){
                log.info("아이디/비번을 찾았습니다!!");
                return idd;
        }
        log.info("아이디/비번을 못찾았습니다.");
        return null;

//        if(null!=idRepository.findById(idDto.getId()).orElse(null)){
//            if(null!=idRepository.findById(idDto.getPassword()).orElse(null)){
//                return idRepository.findById(idDto.getId()).orElse(null);
//            };
//        }
//        return null;
    }


}
