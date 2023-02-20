package com.hw.jpaApi.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class DateEntity {
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private LocalDateTime deleteTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(LocalDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }
}
