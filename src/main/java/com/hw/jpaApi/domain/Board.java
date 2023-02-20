package com.hw.jpaApi.domain;

import com.hw.jpaApi.dto.BoardDto;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@DynamicInsert
@Entity
public class Board extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Column(name = "CONTENT", nullable = false, length = 400)
    private String content;

    @ColumnDefault("0")
    @Column(name = "LIKE_COUNT")
    private Integer likeCount;

    @ColumnDefault("'N'")
    @Column(name = "DEL_YN", length = 1)
    private String delYn;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ID")
    private Member member;

//    @OneToMany(mappedBy = "board", fetch = LAZY)
//    private List<LikeInf> likeInfs = new ArrayList<>();

    @Transient
    private String likeYn;

    public Board() {
        this.likeYn = "N";
    }

    public Board(Long boardId) {
        this.boardId = boardId;
        this.likeYn = "N";
    }

    public Board(String content) {
        this.content = content;
        this.likeYn = "N";
    }

    public Long getBoardId() {
        return boardId;
    }


    public String getContent() {
        return content;
    }

    public Integer getLikeCount() {
        return likeCount;
    }


    public String getDelYn() {
        return delYn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

//    public List<LikeInf> getLikeInfs() {
//        return likeInfs;
//    }

    public String getLikeYn() {
        return likeYn;
    }

    public boolean hasRequired() {
        return this.content != null && "".equals(this.content);
    }

    public boolean isDelete() {
        return "Y".equals(this.delYn);
    }

    public void modify(Board board) {
        this.content = board.getContent();
        this.setModifyTime(LocalDateTime.now());
    }

    public void delete() {
        this.delYn = "Y";
        this.setDeleteTime(LocalDateTime.now());
    }

    public BoardDto convertDto() {
        return new BoardDto(this.boardId, this.content, this.likeCount, this.member.getNickname(), this.likeYn, this.member.getAccountType(), this.delYn, this.getCreateTime(), this.getModifyTime(), this.getDeleteTime());
    }

    public void changeLikeYn(List<LikeInf> likeInfs) {
        likeInfs.forEach(likeInf -> {
            if (likeInf.getBoard().getBoardId() == this.boardId) {
                this.likeYn = likeInf.getLikeYn();
            }
        });
    }

    public void updateLikeCnt(String likeYn) {
        if ("Y".equals(likeYn)) {
            likeCntUp();
        } else {
            likeCntDown();
        }
    }

    public void likeCntUp() {
        if (this.likeCount == null) this.likeCount = 0;
        this.likeCount++;
    }
    public void likeCntDown() {
        if (this.likeCount == null) {
            this.likeCount = 0;
            return;
        }
        if (this.likeCount == 0) {
            return;
        }
        this.likeCount--;
    }

    public boolean hasPermission(Member member) {
        if (!member.hasId()) {
            return false;
        }
        return this.member.getId() == member.getId();
    }
}
