package com.spring.blog_management.service;

import com.spring.blog_management.dao.TagRepository;
import com.spring.blog_management.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(int id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag not found"));
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(int id) {
        tagRepository.deleteById(id);
    }

}
