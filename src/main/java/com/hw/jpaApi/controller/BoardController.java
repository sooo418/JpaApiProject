package com.hw.jpaApi.controller;

import com.hw.jpaApi.annotation.AuthCheck;
import com.hw.jpaApi.domain.Board;
import com.hw.jpaApi.dto.BoardDto;
import com.hw.jpaApi.service.BoardService;
import com.hw.jpaApi.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/zaritalk/api/v1/board")
public class BoardController {

    private BoardService boardService;
    private MemberService memberService;

    public BoardController(BoardService boardService, MemberService memberService) {
        this.boardService = boardService;
        this.memberService = memberService;
    }

    @RequestMapping(value = "/getList/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<BoardDto>> getBoards(@RequestHeader("authentication") String auth,
                                                    @PathVariable("page") int page) {
//        Member member = memberService.getMember(auth);
        List<BoardDto> boards = boardService.getBoards(auth, page);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @AuthCheck
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity createBoard(@RequestBody Board board,
                                      @RequestHeader("authentication") String auth) {
        boardService.createBoard(board, auth);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 글을 등록하였습니다.");
    }

    @AuthCheck
    @RequestMapping(value = "/edit/{board_id}", method = RequestMethod.PUT)
    public ResponseEntity updateBoard(@PathVariable("board_id") long boardId,
                                      @RequestBody Board board,
                                      @RequestHeader("authentication") String auth) {
        boardService.modifyBoard(boardId, board, auth);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 글을 수정하였습니다.");
    }

    @AuthCheck
    @RequestMapping(value = "/edit/{board_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBoard(@PathVariable("board_id") long boardId,
                                      @RequestHeader("authentication") String auth) {
        boardService.deleteBoard(boardId, auth);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 글을 삭제하였습니다.");
    }

    @AuthCheck
    @RequestMapping(value = "/like/{board_id}")
    public ResponseEntity likeToggle(@PathVariable("board_id") Long boardId,
                                     @RequestHeader("authentication") String auth) {
        String message = "해당 글에 좋아요를 ";
        message += StringUtils.equals("Y", boardService.likeToggle(boardId, auth)) ? "완료" : "취소";
        message += "하였습니다.";

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
