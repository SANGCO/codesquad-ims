package codesquad.security;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import codesquad.InputDataNullException;
import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;

@ControllerAdvice
public class SecurityControllerAdvice {
	@Resource(name = "messageSourceAccessor")
	private MessageSourceAccessor msa;
	
	private static final Logger log = LoggerFactory.getLogger(SecurityControllerAdvice.class);

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public void emptyResultData() {
		log.debug("EntityNotFoundException is happened!");
	}

	@ExceptionHandler(UnAuthorizedException.class)
	// @ResponseStatus(value = HttpStatus.FORBIDDEN)
	public String unAuthorized(UnAuthorizedException ex, Model model) {
		log.debug("UnAuthorizedException is happened!");
		model.addAttribute("errorMessage", ex.getMessage());
		return "/user/login";
	}

	@ExceptionHandler(UnAuthenticationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public void unAuthentication() {
		log.debug("UnAuthenticationException is happened!");
	}

	@ExceptionHandler(InputDataNullException.class)
	public String inputDataNull(InputDataNullException ex, Model model) {
		log.debug("InputDataNullException is happened!");
		model.addAttribute("errorMessage", msa.getMessage(ex.getMessage()));
		return "/issue/form";
	}
}
