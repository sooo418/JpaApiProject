package com.hw.jpaApi.service;

import com.hw.jpaApi.domain.Board;
import com.hw.jpaApi.domain.LikeInf;
import com.hw.jpaApi.domain.Member;
import com.hw.jpaApi.dto.BoardDto;
import com.hw.jpaApi.dto.Result;
import com.hw.jpaApi.exception.BoardNotFoundException;
import com.hw.jpaApi.exception.DeletedBoardException;
import com.hw.jpaApi.exception.NoRequiredException;
import com.hw.jpaApi.exception.NonPermissionException;
import com.hw.jpaApi.repository.BoardRepository;
import com.hw.jpaApi.repository.LikeInfRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoardService {

    @Value("${board.msg.create}")
    private String CREATE_MSG;
    @Value("${board.msg.retrieve}")
    private String RETRIEVE_MSG;
    @Value("${board.msg.update}")
    private String UPDATE_MSG;
    @Value("${board.msg.delete}")
    private String DELETE_MSG;
    @Value("${board.msg.like}")
    private String LIKE_MSG;
    @Value("${board.msg.like_cancel}")
    private String LIKE_CANCEL_MSG;

    @Value("${board.page.size}")
    private int PAGE_SIZE;

    private final static String LIKE_Y = "Y";

    private BoardRepository boardRepository;
    private LikeInfRepository likeInfRepository;
    private MemberService memberService;

    public BoardService(BoardRepository boardRepository, LikeInfRepository likeRepository, MemberService memberService) {
        this.boardRepository = boardRepository;
        this.likeInfRepository = likeRepository;
        this.memberService = memberService;
    }

    public Result getBoards(String auth, int page) {
        Member member = memberService.getMember(auth);
        PageRequest pageRequest = PageRequest.of((page - 1), PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Board> boards = boardRepository.findBoards(pageRequest);
        setBoardLikeYn(boards.getContent(), member);

        //msg, data, totalPage
        return new Result(RETRIEVE_MSG, boards.stream().map(Board::convertDto).collect(Collectors.toList()), boards.getTotalPages());
    }
    public void setBoardLikeYn(List<Board> boards, Member member) {
        if (!member.hasId()) {
            return;
        }
        List<LikeInf> likeInfs = likeInfRepository.findLikeInfs(boards, member);

        boards.forEach(board -> {
            board.changeLikeYn(likeInfs);
        });
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public Result createBoard(BoardDto boardDto, String auth) {
        Board board = new Board(boardDto.getContent());

        if (board.hasRequired()) {
            throw new NoRequiredException();
        }

        Member member = memberService.getMember(auth);
        board.setMember(member);
        board.setCreateTime(LocalDateTime.now());
        boardRepository.save(board);

        return new Result(CREATE_MSG);
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public Result modifyBoard(Long boardId, BoardDto boardDto, String auth) {
        Board board = new Board(boardDto.getContent());

        if (board.hasRequired()) {
            throw new NoRequiredException();
        }

        Member member = memberService.getMember(auth);
        Board findBoard = findEditableBoard(boardId, member);

        findBoard.modify(board);

        return new Result(UPDATE_MSG);
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public Result deleteBoard(Long boardId, String auth) {
        Member member = memberService.getMember(auth);
        Board findBoard = findEditableBoard(boardId, member);

        findBoard.delete();

        return new Result(DELETE_MSG);
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public Result likeToggle(Long boardId, String auth) {
        Member member = memberService.getMember(auth);

        Optional<LikeInf> likeInfOpt = likeInfRepository.findByMember_IdAndBoard_BoardId(member.getId(), boardId);
        LikeInf likeInf = likeInfOpt.orElse(new LikeInf(member, new Board(boardId)));

        //likeInf 생성시에 likeYn은 N이 기본값이므로 Y로 변경
        likeInf.toggle();

        if (!likeInf.hasLikeInfId()) {
            likeInfRepository.save(likeInf);
        }

        Board board = getBoard(boardId);

        String likeYn = likeInf.getLikeYn();
        board.updateLikeCnt(likeYn);

        return new Result(LIKE_Y.equals(likeYn) ? LIKE_MSG : LIKE_CANCEL_MSG);
    }

    public Board getBoard(Long boardId) {
        if (boardId == null) {
            throw new NoRequiredException();
        }

        Optional<Board> findBoardOpt = boardRepository.findByBoardId(boardId);
        Board findBoard = findBoardOpt.orElseThrow(BoardNotFoundException::new);
        if (findBoard.isDelete()) {
            throw new DeletedBoardException();
        }

        return findBoard;
    }

    private Board findEditableBoard(Long boardId, Member member) {
        Board findBoard = getBoard(boardId);
        if (!findBoard.hasPermission(member)) {
            throw new NonPermissionException();
        }

        return findBoard;
    }
}
