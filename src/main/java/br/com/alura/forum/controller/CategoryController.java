package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.out.CategoryBriefOutputDTO;
import br.com.alura.forum.controller.repository.CategoryRepository;
import br.com.alura.forum.controller.repository.TopicRepository;
import br.com.alura.forum.model.Category;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private TopicRepository topicRepository;

	@GetMapping(value = "/api/topics/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CategoryBriefOutputDTO> Dashboard() {

		List<Category> categories = categoryRepository.findByCategoryIsNull();
		return CategoryBriefOutputDTO.listFromCategories(categories, topicRepository);
	}

}
