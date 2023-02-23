package com.hw.jpaApi.dto;

import com.hw.jpaApi.constants.AccountType;

import java.time.LocalDateTime;

public class BoardDto {

    private Long boardId;
    private String content;
    private Integer likeCount;
    private String author;
    private String likeYn;
    private AccountType accountType;
    private String delYn;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private LocalDateTime deleteTime;

    public BoardDto() {
    }

    public BoardDto(Long boardId, String content, Integer likeCount, String author, String likeYn, AccountType accountType, String delYn, LocalDateTime createTime, LocalDateTime modifyTime, LocalDateTime deleteTime) {
        this.boardId = boardId;
        this.content = content;
        this.likeCount = likeCount;
        this.author = author;
        this.likeYn = likeYn;
        this.accountType = accountType;
        this.delYn = delYn;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.deleteTime = deleteTime;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getAuthor() {
        return author;
    }

    public String getLikeYn() {
        return likeYn;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getDelYn() {
        return delYn;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "boardId=" + boardId +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", author='" + author + '\'' +
                ", likeYn='" + likeYn + '\'' +
                ", accountType=" + accountType +
                ", delYn='" + delYn + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", deleteTime=" + deleteTime +
                '}';
    }
}
