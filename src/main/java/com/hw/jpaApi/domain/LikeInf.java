package com.hw.jpaApi.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "MEMBER_BOARD_UNIQUE", columnNames = {"ID", "BOARD_ID"})})
public class LikeInf {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LIKE_INF_ID")
    private Long likeInfId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(name = "LIKE_YN", nullable = false, length = 1)
    private String likeYn;

    public LikeInf() {
        this.likeYn = "N";
    }

    public LikeInf(Member member, Board board) {
        this.member = member;
        this.board = board;
        this.likeYn = "N";
    }

    public Long getLikeInfId() {
        return likeInfId;
    }

    public Member getMember() {
        return member;
    }

    public Board getBoard() {
        return board;
    }

    public String getLikeYn() {
        return likeYn;
    }

    public void toggle() {
        this.likeYn = "N".equals(this.likeYn) ? "Y" : "N";
    }

    public boolean hasLikeInfId() {
        return this.likeInfId != null;
    }

}
