package br.com.alura.forum.controller.dto.out;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.alura.forum.model.topic_domain.Topic;
import br.com.alura.forum.model.topic_domain.TopicStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TopicBriefOutputDto {
	private Long id;
	private String shortDescription;
	private long secondsSinceLastUpdate;
	private String ownerName;
	private String courseName;
	private String subcategoryName;
	private String categoryName;
	private int numberOfResponses;
	private boolean solved;

	public TopicBriefOutputDto(Topic topic) {
		this.id = topic.getId();
		this.shortDescription = topic.getShortDescription();
		this.secondsSinceLastUpdate =  Instant.now().getEpochSecond() - topic.getLastUpdate().getEpochSecond();
		this.ownerName = topic.getOwnerName();
		this.courseName = topic.getCourse().getName();
		this.subcategoryName = topic.getCourse().getSubcategoryName();
		this.categoryName = topic.getCourse().getCategoryName();
		this.numberOfResponses = topic.getNumberOfAnswers();
		this.solved = TopicStatus.SOLVED.equals(topic.getStatus());
	}

	public static List<TopicBriefOutputDto> listFromTopics(List<Topic> topics) {
		return topics.stream().map(TopicBriefOutputDto::new).collect(Collectors.toList());
	}
	
	public static Page<TopicBriefOutputDto> listFromTopics(Page<Topic> topics) {
		return topics.map(TopicBriefOutputDto::new);
	}
}
