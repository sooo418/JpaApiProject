package com.hw.jpaApi.controller;

import com.hw.jpaApi.annotation.AuthCheck;
import com.hw.jpaApi.dto.BoardDto;
import com.hw.jpaApi.dto.Result;
import com.hw.jpaApi.service.BoardService;
import com.hw.jpaApi.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Result> getBoards(@RequestHeader("authentication") String auth,
                                                    @PathVariable("page") int page) {

        return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoards(auth, page));
    }

    @AuthCheck
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity<Result> createBoard(@RequestBody BoardDto boardDto,
                                      @RequestHeader("authentication") String auth) {

        return ResponseEntity.status(HttpStatus.OK).body(boardService.createBoard(boardDto, auth));
    }

    @AuthCheck
    @RequestMapping(value = "/edit/{board_id}", method = RequestMethod.PUT)
    public ResponseEntity<Result> updateBoard(@PathVariable("board_id") long boardId,
                                      @RequestBody BoardDto boardDto,
                                      @RequestHeader("authentication") String auth) {

        return ResponseEntity.status(HttpStatus.OK).body(boardService.modifyBoard(boardId, boardDto, auth));
    }

    @AuthCheck
    @RequestMapping(value = "/edit/{board_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> deleteBoard(@PathVariable("board_id") long boardId,
                                      @RequestHeader("authentication") String auth) {

        return ResponseEntity.status(HttpStatus.OK).body(boardService.deleteBoard(boardId, auth));
    }

    @AuthCheck
    @RequestMapping(value = "/like/{board_id}")
    public ResponseEntity<Result> likeToggle(@PathVariable("board_id") Long boardId,
                                     @RequestHeader("authentication") String auth) {

        return ResponseEntity.status(HttpStatus.OK).body(boardService.likeToggle(boardId, auth));
    }
}
