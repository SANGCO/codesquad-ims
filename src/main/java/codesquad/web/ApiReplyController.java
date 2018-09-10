package codesquad.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Issue;
import codesquad.domain.Reply;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.ReplyService;

@RestController
@RequestMapping("/api/issues/{issueId}/replys")
public class ApiReplyController {
	private static final Logger log = LoggerFactory.getLogger(ApiReplyController.class);
	
	@Resource(name = "issueService")
	private IssueService issueService;
	
	@Resource(name = "replyService")
	private ReplyService replyService;
	
	
	@PostMapping("")
	public Reply create(@LoginUser User loginUser, @PathVariable Long issueId, String replyString) {
		log.debug("reply : {}", replyString);
		
		Issue issue = issueService.findById(issueId);
		Reply reply = new Reply(loginUser, issue, replyString);
		issue.addReplyIntoReplys(reply);
		
		return reply;
	}
}
