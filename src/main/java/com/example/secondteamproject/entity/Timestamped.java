package com.example.secondteamproject.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //이 클래스를 상속하면 생성시간, 수정시간도 컬럼으로 인식하게 됨
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {

    @CreatedDate //생성시간 저장
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정시간 저장
    private LocalDateTime modifiedAt;
}