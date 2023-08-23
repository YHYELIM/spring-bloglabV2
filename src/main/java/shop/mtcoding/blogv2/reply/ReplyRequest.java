package shop.mtcoding.blogv2.reply;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blogv2.board.Board;

public class ReplyRequest {

    // @Getter
    // @Setter
    // public static class SaveDTO {
    // private String comment;
    // private Board boardId;

    // }
    @Setter
    @Getter
    public static class SaveDTO {
        private Integer boardId;
        private String comment;
    }
}