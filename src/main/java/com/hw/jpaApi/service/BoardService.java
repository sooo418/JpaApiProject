package com.hw.jpaApi.service;

import com.hw.jpaApi.domain.Board;
import com.hw.jpaApi.domain.LikeInf;
import com.hw.jpaApi.domain.Member;
import com.hw.jpaApi.dto.BoardDto;
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

    @Value("${board.page.size}")
    private int PAGE_SIZE;

    private BoardRepository boardRepository;
    private LikeInfRepository likeInfRepository;
    private MemberService memberService;

    public BoardService(BoardRepository boardRepository, LikeInfRepository likeRepository, MemberService memberService) {
        this.boardRepository = boardRepository;
        this.likeInfRepository = likeRepository;
        this.memberService = memberService;
    }

    public List<BoardDto> getBoards(String auth, int page) {
        Member member = memberService.getMember(auth);
        PageRequest pageRequest = PageRequest.of((page - 1), PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Board> boards = boardRepository.findBoards(pageRequest);
        setBoardLikeYn(boards.getContent(), member);
        return boards.stream().map(Board::convertDto).collect(Collectors.toList());
    }
    public void setBoardLikeYn(List<Board> boards, Member member) {
        if (!member.hasId()) {
            return;
        }
        List<LikeInf> likeInfs = likeInfRepository.findLikeInfs(boards, member);

        boards.forEach(board -> {
            board.changeLikeYn(likeInfs);
        });

//        boards.forEach(board -> {
//            Optional<LikeInf> selfLikeInfOpt = board.getLikeInfs().stream().parallel()
//                    .filter(likeInf -> StringUtils.equals(likeInf.getMember().getId(), self.getId()))
//                    .findAny();
//            LikeInf selfLikeInf = selfLikeInfOpt.orElse(new LikeInf());
//            board.setLikeYn(selfLikeInf.getLikeYn());
//        });
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public void createBoard(Board board, String auth) {
        if (board.hasRequired()) {
            throw new NoRequiredException();
        }

        Member member = memberService.getMember(auth);
        board.setMember(member);
        board.setCreateTime(LocalDateTime.now());
        boardRepository.save(board);
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public void modifyBoard(Long boardId, Board board, String auth) {
        if (board.hasRequired()) {
            throw new NoRequiredException();
        }

        Member member = memberService.getMember(auth);
        Board findBoard = findEditableBoard(boardId, member);

        findBoard.modify(board);
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public void deleteBoard(Long boardId, String auth) {
        Member member = memberService.getMember(auth);
        Board findBoard = findEditableBoard(boardId, member);

        findBoard.delete();
    }

    @CacheEvict(value = "findBoardsCache", allEntries = true)
    public String likeToggle(Long boardId, String auth) {
        Member member = memberService.getMember(auth);

        Optional<LikeInf> likeInfOpt = likeInfRepository.findByMember_IdAndBoard_BoardId(member.getId(), boardId);
        LikeInf likeInf = likeInfOpt.orElse(new LikeInf(member, new Board(boardId)));

        //likeInf 생성시에 likeYn은 N이 기본값이므로 Y로 변경
        likeInf.toggle();

        if (!likeInf.hasLikeInfId()) {
            likeInfRepository.save(likeInf);
        }

        Board board = getBoard(boardId);

        board.updateLikeCnt(likeInf.getLikeYn());

        return likeInf.getLikeYn();
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
