package br.com.alura.forum.controller.repository;

import org.springframework.data.repository.Repository;

import br.com.alura.forum.model.Course;

public interface CourseRepository extends Repository<Course, Long>{
	Course findByName(String Name);
}
