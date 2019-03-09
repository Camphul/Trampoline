package com.lucadev.example.trampoline.messaging;

import com.lucadev.trampoline.security.logging.activity.UserActivity;
import com.lucadev.trampoline.security.model.User;
import lombok.*;

import java.io.Serializable;

/**
 * POC for messaging queue/task queue
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserActivityMessage implements Serializable {

	private String principal = "";
	private String category;
	private String identifier;
	private long executionStart;
	private long executionEnd;
	private String className;
	private String methodName;

	public UserActivityMessage(UserActivity userActivity) {
		if(userActivity.getAuthentication() != null) {
			Object principal = userActivity.getAuthentication().getPrincipal();
			if(principal instanceof User) {
				this.principal = String.valueOf(((User) principal).getId());
			}
		}
		this.category = userActivity.getCategory();
		this.identifier = userActivity.getIdentifier();
		this.executionStart = userActivity.getInvocationContext().getExecutionStart();
		this.executionEnd = userActivity.getInvocationContext().getExecutionFinish();
		this.className = userActivity.getInvocationContext().getClassName();
		this.methodName = userActivity.getInvocationContext().getMethodName();
	}
}
