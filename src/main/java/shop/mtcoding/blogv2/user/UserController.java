package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.reply.ReplyRequest;

@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 브라우저 GET /logout 요청을 함 (request 1)
    // 서버는 / 주소를 응답의 헤더에 담음 (Location), 상태코드 302
    // 브라우저는 GET / 로 재요청을 함 (request 2)
    // index 페이지 응답받고 렌더링함
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // C - V
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        // System.out.println(joinDTO.getPic().getOriginalFilename());
        // System.out.println(joinDTO.getPic().getSize());
        // System.out.println(joinDTO.getPic().getContentType());

        userService.회원가입(joinDTO);
        return "user/loginForm"; // persist 초기화
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        if (sessionUser == null) {
            throw new MyException("로그인 실패");
            // return Script.back("로그인 실패");//이렇게 하는거 아님
        }

        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        // 1. 회원수정 (서비스)
        // 2. 세션동기화
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user); // 유저 정보 담았으니까 머스태치 파일에서 빼낼 수 있다
        return "redirect:/";
    }

    @GetMapping("/api/user/check")
    public @ResponseBody ApiUtil<String> check(String username) {
        // 앞에서 json값 받음
        // 서비스 호출(유저네임 비교)
        System.out.println("1234");

        userService.유저네임(username);
        System.out.println("5678");

        return new ApiUtil<String>(false, "유저네임 중복");

    }
}
