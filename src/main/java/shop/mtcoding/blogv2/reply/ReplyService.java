package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRequest;
import shop.mtcoding.blogv2.board.BoardRequest.SaveDTO;
import shop.mtcoding.blogv2.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {
    @Autowired
    ReplyRepository replyRepository;

    @Transactional
    public void 삭제하기(Integer id) {
        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("을 찾을 수 없습니다");
        }
    }

    @Transactional
    public void 댓글쓰기(shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO saveDTO, Integer sessionId) {
        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionId).build();
        // inser into reply_tb(comment, board_id, user_id)value()
        // 레파지토리에서 굳이 쓸일이 없다
        Reply reply = Reply.builder() // 내가 new해서 비영속 객체
                .comment(saveDTO.getComment())
                .board(null)
                .user(null)
                .build();
        replyRepository.save(reply);// entity: reply 객체
        // 영속 객체

    }

    @Transactional
    public void 댓글삭제(Integer id, Integer sessionUserId) {
        Optional<Reply> replyOP = replyRepository.findById(id);

        if (replyOP.isEmpty()) {
            throw new MyApiException("삭제할 댓글이 없습니다");
        }

        Reply reply = replyOP.get();
        if (reply.getUser().getId() != sessionUserId) {
            throw new MyApiException("해당 댓글을 삭제할 권한이 없습니다");
        }

        replyRepository.deleteById(id);
    }
}
