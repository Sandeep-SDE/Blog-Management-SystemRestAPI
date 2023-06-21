package com.spring.blog_management.dao;


import com.spring.blog_management.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
