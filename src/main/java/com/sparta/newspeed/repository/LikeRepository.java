package com.sparta.newspeed.repository;

import com.sparta.newspeed.entity.ContentTypeEnum;
import com.sparta.newspeed.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByContentIdAndUserIdAndContentType(Long contentId, Long UserId, ContentTypeEnum contentTypeEnum);


}
