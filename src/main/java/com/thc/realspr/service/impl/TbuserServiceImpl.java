package com.thc.realspr.service.impl;

import com.thc.realspr.domain.TbKaFeed;
import com.thc.realspr.domain.Tbuser;
import com.thc.realspr.dto.DefaultDto;
import com.thc.realspr.dto.GoogleLoginRequest;
import com.thc.realspr.dto.GoogleLoginResponse;
import com.thc.realspr.dto.TbuserDto;
import com.thc.realspr.exception.NoAuthorizationException;
import com.thc.realspr.repository.TbuserRepository;
import com.thc.realspr.service.GoogleAuthService;
import com.thc.realspr.service.TbuserService;
import com.thc.realspr.util.TokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.thc.realspr.util.TokenFactory;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TbuserServiceImpl implements TbuserService {

    @Autowired
    private final TbuserRepository tbuserRepository;
    private final GoogleAuthService googleAuthService;


    public TbuserServiceImpl(TbuserRepository tbuserRepository, GoogleAuthService googleAuthService) {
        this.tbuserRepository = tbuserRepository;
        this.googleAuthService = googleAuthService;
    }


    @Override
    public TbuserDto.DetailResDto detail(DefaultDto.DetailReqDto param) {
        // TODO: implement detail
        return null;
    }

    @Override
    public Map<String, Object> create(Map<String, Object> params) {

        String email = (String) params.get("email");
        String uuid = (String) params.get("uuid");
        String name = (String) params.get("name");
        LocalDateTime lastLoginTime = (LocalDateTime) params.get("last_login_time");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        System.out.println(params);
        Tbuser tbuser = Tbuser.of(email, uuid, name, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        tbuserRepository.save(tbuser);
        returnMap.put("id", tbuser.getId());
        return returnMap;
    }


    @Override
    public TbuserDto.CreateResDto access(String param) throws Exception {
        TokenFactory tokenFactory = new TokenFactory();
        String tbuserId = tokenFactory.issueAccessToken(param);

        return null;
    }


    @Override
    public TbuserDto.CreateResDto loginWithGoogle(String credential) {
        String[] out = googleAuthService.verifyGoogleToken(credential);
        String email = out[0];
        String name = out[1];

        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString().replace("-", "");

        System.out.println("email string is " + email);
        System.out.println("email name is " + name);

        // 유저 객체 생성


        // 유저가 @handong.ac.kr 이메일이 아니면 예외 처리
//        if (email == null || !email.endsWith("@handong.ac.kr")) {
//            throw new NoAuthorizationException("Unauthorized user");
//        }

        Tbuser tbuser = tbuserRepository.findByEmail(email);

        if (tbuser != null) {
            // 유저가 있으면 이메일 출력
            System.out.println("user " + tbuser.getEmail());
            tbuser.setLast_login_time(now);
        } else {
            tbuser = Tbuser.of(email, uuid, name, now, now, now);
            // 유저가 없는 경우 신규 가입
        }
        tbuserRepository.save(tbuser);

//        Map<String, Object> params = new HashMap<>();
//        params.put("email", email);
//        params.put("uuid", uuid);
//        params.put("name", name);
//
//        Map<String, Object> createResult = create(params);

        return TbuserDto.CreateResDto.builder().uid(tbuser.getId()).email(email).build();
    }


}