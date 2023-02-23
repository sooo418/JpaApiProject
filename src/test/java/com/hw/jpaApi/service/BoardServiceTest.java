package com.hw.jpaApi.service;

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
        List<BoardDto> boards = (List<BoardDto>) boardService.getBoards(auth, 1).getData();

        //then
        boards.forEach(b -> System.out.println(b));
    }

    @Test
    void 글작성() {
        try {
            //given
            String auth = "Lessor 21";
            BoardDto boardDto = new BoardDto();
            boardDto.setContent("허리가 휘었어요");

            //when
            boardService.createBoard(boardDto, auth);
            List<BoardDto> boards = (List<BoardDto>) boardService.getBoards(auth, 15).getData();

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
            BoardDto boardDto = new BoardDto();
            boardDto.setContent("그럼 이만");

            //when
            /*boardService.modifyBoard(board)
            List<BoardDto> boards = boardService.getBoards(member);*/

            //then
            assertThrows(NonPermissionException.class, () -> boardService.modifyBoard(4L, boardDto, auth));
//            boards.forEach(b -> System.out.println(b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void 좋아요토글() {
        try {
            //given
            long boardId = 43L;
            String auth = "Lessee 562";

            //when
            boardService.likeToggle(boardId, auth);
            List<BoardDto> boards = (List<BoardDto>) boardService.getBoards(auth, 15).getData();

            //then
            boards.forEach(b -> System.out.println(b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}