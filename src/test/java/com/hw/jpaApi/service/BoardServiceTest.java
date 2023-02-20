package com.hw.jpaApi.service;

import com.hw.jpaApi.domain.Board;
import com.hw.jpaApi.dto.BoardDto;
import com.hw.jpaApi.exception.NonPermissionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    BoardService boardService;

    @Test
    void 글목록조회() {
        //given
        String auth = "Lessee 562";

        //when
        List<BoardDto> boards = boardService.getBoards(auth, 1);

        //then
        boards.forEach(b -> System.out.println(b));
    }

    @Test
    void 글작성() {
        try {
            //given
            String auth = "Lessor 21";
            Board board = new Board("안녕하세요.");

            //when
            boardService.createBoard(board, auth);
            List<BoardDto> boards = boardService.getBoards(auth, 15);

            //then
            boards.forEach(b -> System.out.println(b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void 글수정() {
        try {
            //given
            String auth = "Lessor 21";
            Board board = new Board("그럼 이만");

            //when
            /*boardService.modifyBoard(board)
            List<BoardDto> boards = boardService.getBoards(member);*/

            //then
            assertThrows(NonPermissionException.class, () -> boardService.modifyBoard(4L, board, auth));
//            boards.forEach(b -> System.out.println(b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void 좋아요토글() {
        try {
            //given
            long boardId = 5L;
            String auth = "Lessee 562";

            //when
            boardService.likeToggle(boardId, auth);
            List<BoardDto> boards = boardService.getBoards(auth, 15);

            //then
            boards.forEach(b -> System.out.println(b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}