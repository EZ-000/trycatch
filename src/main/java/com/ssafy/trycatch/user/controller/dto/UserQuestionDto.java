package com.ssafy.trycatch.user.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserQuestionDto {
	public final Long questionId;
	public final SimpleUserInfo author;
	public final String category;
	public final String title;
	public final String content;
	public final String[] tags;
	public final Integer likeCount;
	public final Integer answerCount;
	public final Integer viewCount;
	public final Long timeStamp;
	public final boolean isLiked;
	public final boolean isSolved;
	public final boolean isBookmarked;

	@Builder
	public UserQuestionDto(Long questionId, SimpleUserInfo author, String category, String title, String content,
		String[] tags, Integer likeCount, Integer answerCount, Integer viewCount, Long timeStamp, boolean isLiked,
		boolean isSolved, boolean isBookmarked) {
		this.questionId = questionId;
		this.author = author;
		this.category = category;
		this.title = title;
		this.content = content;
		this.tags = tags;
		this.likeCount = likeCount;
		this.answerCount = answerCount;
		this.viewCount = viewCount;
		this.timeStamp = timeStamp;
		this.isLiked = isLiked;
		this.isSolved = isSolved;
		this.isBookmarked = isBookmarked;
	}
}
