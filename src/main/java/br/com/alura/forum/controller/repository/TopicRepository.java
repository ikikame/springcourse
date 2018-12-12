package br.com.alura.forum.controller.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic_domain.Topic;

public interface TopicRepository extends Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {
	
	@Query("select t from Topic t")
	List<Topic> list();
	Page<Topic> findAll();

	@Query("select count(topic) from Topic topic "
			+ "join topic.course course "
			+ "join course.subcategory subcategory "
			+ "join subcategory.category category "
			+ "where category.id = :categoryId")
	int countByCategoryId(@Param("categoryId") Long categoryId);
	
	@Query("select count(topic) from Topic topic "
			+ "join topic.course course "
			+ "join course.subcategory subcategory "
			+ "join subcategory.category category "
			+ "where category.id = :categoryId and topic.creationInstant > :lastWeek")
	int countByLastWeekAndCategoryId(@Param("categoryId") Long categoryId,
							@Param("lastWeek") Instant lastWeek);
	
	@Query("select count(topic) from Topic topic "
			+ "join topic.course course "
			+ "join course.subcategory subcategory "
			+ "join subcategory.category category "
			+ "where category.id = :categoryId "
			+ "AND topic.status = 'NOT_ANSWERED'")
	int countByNotAnsweredByCategoryId(@Param("categoryId") Long categoryId);
	
	void save(Topic topic);
	
	int countByOwnerAndCreationInstantAfter(User user, Instant from);
}
