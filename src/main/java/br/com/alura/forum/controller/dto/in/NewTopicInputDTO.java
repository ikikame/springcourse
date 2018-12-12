package br.com.alura.forum.controller.dto.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.alura.forum.controller.repository.CourseRepository;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic_domain.Topic;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class NewTopicInputDTO {
	@NotBlank
	@Size(min = 15, max = 1000)
	private String shortDescription;
	
	@NotBlank
	@Size(min = 15, max = 1000)
	private String content;
	
	@NotBlank
	@Size(min = 4, max = 100)
	private String courseName;

	public Topic toModel(User user, CourseRepository courseRepository) {
		Course course = courseRepository.findByName(this.courseName);
		return new Topic(shortDescription, content, user, course);
	}
}