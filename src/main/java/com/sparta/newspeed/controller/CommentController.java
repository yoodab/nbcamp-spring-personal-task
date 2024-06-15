package com.sparta.newspeed.controller;

import com.sparta.newspeed.dto.CommentReqDto;
import com.sparta.newspeed.dto.CommentResDto;
import com.sparta.newspeed.service.CommentService;
import com.sparta.newspeed.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API", description = "댓글 작성/조회/수정/삭제 API")
@RestController
@RequestMapping("/peeds")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "특정 뉴스피드에 댓글을 작성합니다.")
    @PostMapping("/{newspeedId}/comment")
    public ResponseEntity<String> addComment(@Parameter(description = "뉴스피드 ID") @PathVariable Long newspeedId,
                                             @RequestBody CommentReqDto commentReqDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.addComment(newspeedId, commentReqDto, userDetails);
        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
    }

    @Operation(summary = "댓글 조회", description = "특정 뉴스피드의 특정 댓글을 조회합니다.")
    @GetMapping("/{newspeedId}/comments/{commentId}")
    public CommentResDto getComment(@Parameter(description = "뉴스피드 ID") @PathVariable Long newspeedId,
                                    @Parameter(description = "댓글 ID") @PathVariable Long commentId) {
        return commentService.getComment(newspeedId, commentId);

    }

    @Operation(summary = "댓글 수정", description = "특정 뉴스피드의 특정 댓글을 수정합니다.")
    @PutMapping("/{newspeedId}/comments/{commentId}")
    public CommentResDto modifyComment(@Parameter(description = "뉴스피드 ID") @PathVariable Long newspeedId,
                                       @Parameter(description = "댓글 ID") @PathVariable Long commentId,
                                       @RequestBody CommentReqDto commentReqDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails

    ) {
        return commentService.mdofiyComment(newspeedId, commentId, commentReqDto, userDetails);

    }

    @Operation(summary = "댓글 삭제", description = "특정 뉴스피드의 특정 댓글을 삭제합니다.")
    @DeleteMapping("/{newspeedId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@Parameter(description = "뉴스피드 ID") @PathVariable Long newspeedId,
                                                @Parameter(description = "댓글 ID") @PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails

    ) {
        commentService.deleteComment(newspeedId, commentId, userDetails);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);

    }

}
