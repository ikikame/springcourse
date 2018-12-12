package br.com.alura.forum.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.in.NewTopicInputDTO;
import br.com.alura.forum.controller.dto.in.TopicSearchInputDto;
import br.com.alura.forum.controller.dto.out.TopicBriefOutputDto;
import br.com.alura.forum.controller.dto.out.TopicOutputDTO;
import br.com.alura.forum.controller.repository.CourseRepository;
import br.com.alura.forum.controller.repository.TopicRepository;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic_domain.Topic;
import br.com.alura.forum.validators.NewTopicCustomValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/topics")
public class TopicController {

	@Autowired 
	private TopicRepository topicRepository;
	@Autowired 
	private CourseRepository courseRepository;
	

	public TopicController(final TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TopicBriefOutputDto> allTopics(TopicSearchInputDto topicInputSearch, @PageableDefault(size = 10, page = 1, sort = "creationInstant", direction=Sort.Direction.DESC) Pageable pageable) {
		
		Page<Topic> topics = topicRepository.findAll(topicInputSearch.criaQuery(), pageable);
		return TopicBriefOutputDto.listFromTopics(topics);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TopicOutputDTO> createTopic(@RequestBody @Valid NewTopicInputDTO inputDTO, @AuthenticationPrincipal User user, UriComponentsBuilder uriBuilder) {
		Topic topic = inputDTO.toModel(user, courseRepository);
		topicRepository.save(topic);
		URI location = uriBuilder.path("/api/topics/{id}").buildAndExpand(topic.getId()).toUri();
		return ResponseEntity.created(location).body(new TopicOutputDTO(topic));
	}
	@InitBinder
	public void addValidator(WebDataBinder binder, @AuthenticationPrincipal User user) {
		/*if (user == null) {
			return ;
		}*/
		//log.info("User email: " + user.getEmail());
		binder.addValidators(new NewTopicCustomValidator(user, topicRepository));
	}
}
