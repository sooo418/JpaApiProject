package com.hw.jpaApi.repository;

import com.hw.jpaApi.domain.Board;
import com.hw.jpaApi.domain.LikeInf;
import com.hw.jpaApi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeInfRepository extends JpaRepository<LikeInf, Long> {

    @Query(value = "select likeInf " +
            "from LikeInf likeInf " +
            "where likeInf.board in :boards " +
            "and likeInf.member = :member")
    List<LikeInf> findLikeInfs(@Param("boards") List<Board> boards, @Param("member") Member member);

    List<LikeInf> findByBoardAndLikeYn(Board board, String likeYn);

    Optional<LikeInf> findByMember_IdAndBoard_BoardId(Long id, Long boardId);
}
