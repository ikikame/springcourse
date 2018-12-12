package br.com.alura.forum.controller.dto.in;

import java.util.ArrayList;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.forum.model.topic_domain.Topic;
import br.com.alura.forum.model.topic_domain.TopicStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicSearchInputDto {
	private TopicStatus status;
	private String categoryName;
	
	public Specification<Topic> criaQuery() {
		return ((root, criteriaQuery, criteriaBuilder) -> {
			ArrayList<Predicate> predicates = new ArrayList<Predicate>();
			if (status != null) {
				predicates.add(criteriaBuilder.equal(root.get("status"), status));
			}
			if (categoryName != null && categoryName.length() > 0) {
				Path<String> categoryPath = root.get("course").get("subcategory").get("category").get("name");
				predicates.add(criteriaBuilder.equal(categoryPath, categoryName));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
	}
}
