package shop.mtcoding.blogv2.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    // @Modifying // executeUpdate
    // @Query(value = "insert into reply_tb(created_at, comment, board_id, user_id)
    // values(nonw(), :comment, :boardId, :userId)", nativeQuery = true)
    // void mSave(@Param("comment") String comment, @Param("board_id ") Integer
    // boardId, @Param("user_id") Integer userId);
}
