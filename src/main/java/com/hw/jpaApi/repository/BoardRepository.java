package com.hw.jpaApi.repository;

import com.hw.jpaApi.domain.Board;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    @Cacheable(cacheNames = "findBoardsCache", unless = "#result == null")
//    @Query(value = "select distinct board" +
//            " from Board board" +
//            " left join fetch board.likeInfs")
//    List<Board> findBoards();

    @Cacheable(cacheNames = "findBoardsCache", unless = "#result == null")
    @Query(value = "select board" +
            " from Board board" +
            " join fetch board.member",
    countQuery = "select count(board.boardId)" +
            " from Board board")
    Page<Board> findBoards(Pageable pageable);

    Optional<Board> findByBoardId(Long aLong);
}
