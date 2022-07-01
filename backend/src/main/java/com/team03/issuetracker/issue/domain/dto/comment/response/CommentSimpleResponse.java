package com.team03.issuetracker.issue.domain.dto.comment.response;

import com.team03.issuetracker.common.domain.Member;
import com.team03.issuetracker.issue.domain.Comment;
import com.team03.issuetracker.issue.domain.Emoji;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentSimpleResponse {

	private WriterResponseOfSimpleComment writer;
	private LocalDateTime createdAt;
	private String content;
	private EmojiResponseOfSimpleComment emoji;

	public static CommentSimpleResponse from(Comment comment) {
		return new CommentSimpleResponse(
			comment.getWriter() == null ? null
				: WriterResponseOfSimpleComment.from(comment.getWriter()),
			comment.getCreatedDate(), comment.getContent(),
			comment.getEmoji() == null ? null
				: EmojiResponseOfSimpleComment.from(comment.getEmoji()));
	}

	@Getter
	@RequiredArgsConstructor
	private static class WriterResponseOfSimpleComment {

		private final Long writerId;
		private final String writerName;
		private final String writerProfileImage;

		public static WriterResponseOfSimpleComment from(Member writer) {
			return new WriterResponseOfSimpleComment(writer.getId(), writer.getName()
			,writer.getProfileImage());
		}
	}

	@Getter
	@RequiredArgsConstructor
	private static class EmojiResponseOfSimpleComment {

		private final Long emojiId;
		private final String unicode;

		public static EmojiResponseOfSimpleComment from(Emoji emoji) {
			return Objects.nonNull(emoji)
				? new EmojiResponseOfSimpleComment(emoji.getId(), emoji.getUnicode())
				: null;
		}
	}
}
