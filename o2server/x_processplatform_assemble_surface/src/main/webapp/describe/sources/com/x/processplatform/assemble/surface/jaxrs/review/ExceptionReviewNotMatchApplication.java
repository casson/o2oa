package com.x.processplatform.assemble.surface.jaxrs.review;

import com.x.base.core.project.exception.PromptException;

class ExceptionReviewNotMatchApplication extends PromptException {

	private static final long serialVersionUID = -9089355008820123519L;

	ExceptionReviewNotMatchApplication(String id, String applicationFlag) {
		super("review: {} not match with application: {}.", id, applicationFlag);
	}
}
