package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.vo.MyParh;
import shop.mtcoding.blogv2._core.vo.MyPath;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(JoinDTO joinDTO) {

        UUID uuid = UUID.randomUUID();
        // uuid: 전세계 딱 하나-> 식별자
        // 그래서 충돌날 일이 없다
        String fileName = uuid + "" + joinDTO.getPic().getOriginalFilename();
        // 확장자 때문에 무조건 파일네임 뒤로 가야함
        // System.out.println("");

        // 프로젝트 실행
        // ./: 현재 폴더: springboot-blog-v5
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);// 파일 경로 잡기
        try {
            Files.write(filePath, joinDTO.getPic().getBytes());//
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .picUrl(fileName)// 경로 저장-> 근데 이렇게 넣는건 위험함 파일명을 바꾸는순간 디비를 다 바꿔야하니까
                // 사진경로를 저장하지말고 사진명만 저장
                .build();
        userRepository.save(user); // em.persist
    }

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 1. 유저네임 검증
        if (user == null) {
            throw new MyException("유저네임이 없습니다");
            // 아이디 비번 못찾았는데 굳이 null 응답 필요 없다-> 익셉션으로 던져 버려
        }

        // 2. 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            return null;
        }

        // 3. 로그인 성공
        return user;
    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User 회원수정(UpdateDTO updateDTO, Integer id) {

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "" + updateDTO.getPic().getOriginalFilename();

        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);// 파일 경로 잡기
        try {
            Files.write(filePath, updateDTO.getPic().getBytes());//
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        // 1. 조회 (영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        user.setPassword(updateDTO.getPassword());
        user.setPicUrl(fileName);// 사진 변경

        return user;
    } // 3. flush

    public void 유저네임(String username) {
        // 1. 유저테이블에 유저네임을 가지고온다

        // 2. 가지고 온 유저네임과 프론트에서 받은 유저네임을 비교한다
        User user = userRepository.findByUsername(username);
        if (user.getUsername() == null) {// 유저네임이 없다

            throw new MyApiException("유저네임이 없습니다");
        } else {
            throw new MyApiException("유저네임 중복됐습니다");
        }

    }

}
