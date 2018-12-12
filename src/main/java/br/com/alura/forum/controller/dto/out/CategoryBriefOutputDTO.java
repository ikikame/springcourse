package br.com.alura.forum.controller.dto.out;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.controller.repository.TopicRepository;
import br.com.alura.forum.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryBriefOutputDTO {
	private String categoryName;
	private List<String> subcategories;
	private int allTopics;
	private int lastWeekTopics;
	private int unansweredTopics;

	public CategoryBriefOutputDTO(Category category, TopicRepository topicRepository) {

		this.categoryName = category.getName();
		this.subcategories = category.getSubcategoryNames();
		this.allTopics = topicRepository.countByCategoryId(category.getId());
		this.lastWeekTopics = topicRepository.countByLastWeekAndCategoryId(category.getId(), Instant.now().minus(7,ChronoUnit.DAYS));
		this.unansweredTopics = topicRepository.countByNotAnsweredByCategoryId(category.getId());
	}

	public static List<CategoryBriefOutputDTO> listFromCategories(List<Category> categories, TopicRepository topicRepository) {
		return categories.stream().map( (Category category) ->  new CategoryBriefOutputDTO(category, topicRepository)).collect(Collectors.toList());
	}
}