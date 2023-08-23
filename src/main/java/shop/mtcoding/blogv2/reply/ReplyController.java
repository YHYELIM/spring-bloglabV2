package shop.mtcoding.blogv2.reply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.board.BoardRequest;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    private HttpSession session;

    private Object replyServicsaveDTO;

    // @PostMapping("/reply/save")
    // public String Save(ReplyRequest.SaveDTO saveDTO) {

    // User sessionUser = (User) session.getAttribute("sessionUser");
    // replyService.댓글등록(saveDTO, sessionUser.getId());

    // return "redirect:/";

    // }

    @PostMapping("/api/reply/save")
    public @ResponseBody ApiUtil<String> save(@RequestBody ReplyRequest.SaveDTO saveDTO) {
        // 여기서 중요한건 데이터를 잘 받아야한다
        // json데이터를 잘 받아야함
        // 보드아이디와 코멘트 키값 받을라면 디티오 만들어야함
        // System.out.println("boardId:" + saveDTO.getBoardId());
        // System.out.println("comment:" + saveDTO.getComment());

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new MyApiException("인증되지 않았습니다");

            // return new ApiUtil<String>(false, "인증이 되지 않았습니다");
            // 에러는 익셉션 핸들러로 다 던져주기로 함
        }
        replyService.댓글쓰기(saveDTO, sessionUser.getId());

        // return "ok";// 평문으로 리턴해주는거
        // 이렇게 할순없으니까 응답의 객체를 하나 만든다
        return new ApiUtil<String>(true, "댓글쓰기 성공");
        // 이것만 응답 받는다고 치면 댓글 내용만 그릴수 있음
        // 삭제에 리플리 아이디를 못한다 클라이언트 사이드 랜더링을 못한다
        // 응답 받지 못하면 클라이언트 사이드 랜더링 못한다
        // 만약에 T자리에 리플리 객체를 넣어주면 클라이언트 사이드 랜더링이 가능하다
        // 리플리 정보가 ㄷ다 들어있기 때문

    }

    @DeleteMapping("/api/reply/delete")
    public @ResponseBody ApiUtil<String> delete(@PathVariable Integer id) {
        // 1. 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new MyApiException("인증되지 않았습니다");
        }

        // 2. 핵심로직
        replyService.댓글삭제(id, sessionUser.getId());

        // 3. 응답
        return new ApiUtil<String>(false, null);
    }
}
