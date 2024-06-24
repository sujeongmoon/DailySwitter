package com.sparta.dailyswitter.domain.like.postlike.entity;


import com.sparta.dailyswitter.common.util.Timestamped;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post_like")
@NoArgsConstructor
public class PostLike extends Timestamped {
}
