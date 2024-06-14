package com.sparta.newspeed.service;

import com.sparta.newspeed.entity.Comment;
import com.sparta.newspeed.entity.Likes;
import com.sparta.newspeed.entity.Peed;
import com.sparta.newspeed.entity.User;
import com.sparta.newspeed.entity.ContentTypeEnum;
import com.sparta.newspeed.repository.LikeRepository;
import com.sparta.newspeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final PeedService peedService;

    public ResponseEntity<String> likeFeed(Long peedsId, UserDetailsImpl userDetailsImpl) {
        Peed peed = peedService.findPeed(peedsId);
        User user = userService.findUserById(userDetailsImpl.getUser().getId());
        Optional<Likes> likes = findLikeByContentIdAndUser(peed.getId(), user, ContentTypeEnum.PEED);
        if (likes.isPresent()) {
            peed.likesCount("-");
            likeRepository.delete(likes.get());
            return new ResponseEntity<>("안좋아요.", HttpStatus.OK);
        } else {
            peed.likesCount("+");
            likeRepository.save(new Likes(peed, user, ContentTypeEnum.PEED));
            return new ResponseEntity<>("좋아요.", HttpStatus.OK);
        }
    }

    public ResponseEntity<String> likeComment(Long commentId, UserDetailsImpl userDetailsImpl) {
        Comment comment = commentService.findCommentById(commentId);
        User user = userService.findUserById(userDetailsImpl.getUser().getId());
        Optional<Likes> likes = findLikeByContentIdAndUser(commentId, user, ContentTypeEnum.COMMENT);
        if (likes.isPresent()) {
            comment.likesCount("-");
            likeRepository.delete(likes.get());
            return new ResponseEntity<>("안좋아요.", HttpStatus.OK);
        } else {
            comment.likesCount("+");
            likeRepository.save(new Likes(comment, user, ContentTypeEnum.COMMENT));
            return new ResponseEntity<>("좋아요.", HttpStatus.OK);
        }
    }

    public Optional<Likes> findLikeByContentIdAndUser(Long Id, User user, ContentTypeEnum contentType) {
        Optional<Likes> likes =
                likeRepository
                        .findByContentIdAndUserIdAndContentType(Id, user.getId(), contentType);

        return likes;
    }
  }

