package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import support.domain.AbstractEntity;

@Entity
public class Reply extends AbstractEntity {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_reply_writer"))
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_reply_issue"))
	private Issue issue;
	
	@NotNull
	@Size(max = 50)
	@Column(nullable = false, length = 50)
	private String replyContents;
	
	public Reply() {
	}
	
	public Reply(User writer, Issue issue, String replycontents) {
		super(0L);
		this.writer = writer;
		this.issue = issue;
		this.replyContents = replycontents;
	}

	public String getReplyContents() {
		return replyContents;
	}

	public void setReplyContents(String replyContents) {
		this.replyContents = replyContents;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	@Override
	public String toString() {
		return "Reply [writer=" + writer + ", issue=" + issue + ", replyContents=" + replyContents + "]";
	}
}
