package codesquad.service;

import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import codesquad.InputDataNullException;
import codesquad.domain.Reply;
import codesquad.domain.ReplyRepository;

@Service
public class ReplyService {
	@Resource(name = "messageSourceAccessor")
	private MessageSourceAccessor msa;
	
	@Resource(name = "replyRepository")
	private ReplyRepository replyRepository;
	
	public Reply add(Reply reply) {
		if(reply == null) {
			throw new InputDataNullException(msa.getMessage("nullError")); 
		}
		return replyRepository.save(reply);
	}
}
