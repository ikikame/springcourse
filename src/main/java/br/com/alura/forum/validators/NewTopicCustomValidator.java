package br.com.alura.forum.validators;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.alura.forum.controller.dto.in.NewTopicInputDTO;
import br.com.alura.forum.controller.repository.TopicRepository;
import br.com.alura.forum.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewTopicCustomValidator implements Validator {
	private User user;
	private TopicRepository topicRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		
		return NewTopicInputDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Instant oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS);
		int lastHourTopicsCount = topicRepository.countByOwnerAndCreationInstantAfter(user, oneHourAgo);
		if (lastHourTopicsCount > 5) {
			errors.reject("topics.limit.exceeded", new Object[] {10}, "");
		}
	}
}
