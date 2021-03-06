package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codesquad.InputDataNullException;
import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Issue.class);

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
	private Milestone milestone;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	@ManyToMany
	@JoinTable(name = "ISSUE_LABEL", joinColumns = @JoinColumn(name = "ISSUE_ID"), inverseJoinColumns = @JoinColumn(name = "LABEL_ID"))
	private List<Label> labels;
	
	@OneToMany(mappedBy="issue")
	private List<Reply> replys;

	@Column(nullable = false, length = 20)
	private String title;

	@Lob
	@Column(nullable = false)
	private String contents;

	public Issue() {
	}

	public Issue(String title, String contents, User writer, Milestone milestone, List<Label> label, List<Reply> replys) {
		super(0L);
		this.title = title;
		this.contents = contents;
		this.writer = writer;
		this.milestone = milestone;
		this.labels = label;
		this.replys = replys;
	}

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}

	public void update(User loginUser, IssueDto issueDto) {
		if (!isSameWriter(loginUser)) {
			throw new UnAuthorizedException();
		}

		this.title = issueDto.getTitle();
		this.contents = issueDto.getContents();
	}

	public void addMilesstone(Milestone milestone) {
		this.milestone = milestone;
		log.debug("milestone : {}", milestone);
	}

	public void addLabelIntoLabels(Label label) {
		this.labels.add(label);
	}
	
	public void addReplyIntoReplys(Reply reply) {
		this.replys.add(reply);
	}

	public void addUser(User user) {
		if (user == null) {
			throw new InputDataNullException("nullError");
//			키값을 던지자
		}
		this.writer = user;
	}

	public IssueDto _toIssueDto() {
		return new IssueDto(super.getId(), this.writer, this.title, this.contents, this.milestone, this.labels, this.replys);
	}

	public Result valid(User loginUser) {
		if (!isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return Result.ok();
	}

	@Override
	public String toString() {
		return "Issue [milestone=" + milestone + ", writer=" + writer + ", labels=" + labels + ", replys=" + replys
				+ ", title=" + title + ", contents=" + contents + "]";
	}
}
