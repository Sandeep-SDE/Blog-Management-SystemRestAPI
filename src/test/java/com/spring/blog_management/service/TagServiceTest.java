package com.spring.blog_management.service;

import com.spring.blog_management.dao.TagRepository;
import com.spring.blog_management.entity.Tag;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTags_ShouldReturnAllTags() {

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "Tag 1"));
        tags.add(new Tag(2, "Tag 2"));
        when(tagRepository.findAll()).thenReturn(tags);

        List<Tag> result = tagService.getAllTags();

        assertEquals(tags, result);
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void getTagById_WithExistingId_ShouldReturnTag() {

        int tagId = 1;
        Tag tag = new Tag(tagId, "Tag 1");
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        Tag result = tagService.getTagById(tagId);
        assertEquals(tag, result);
        verify(tagRepository, times(1)).findById(tagId);
    }

    @Test
    void getTagById_WithNonExistingId_ShouldThrowNoSuchElementException() {

        int tagId = 1;
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> tagService.getTagById(tagId));
        verify(tagRepository, times(1)).findById(tagId);
    }

    @Test
    void createTag_ShouldReturnCreatedTag() {

        Tag tag = new Tag(1, "Tag 1");
        when(tagRepository.save(tag)).thenReturn(tag);

        Tag result = tagService.createTag(tag);

        assertEquals(tag, result);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void updateTag_ShouldReturnUpdatedTag() {

        Tag tag = new Tag(1, "Tag 1");
        when(tagRepository.save(tag)).thenReturn(tag);

        Tag result = tagService.updateTag(tag);

        assertEquals(tag, result);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void deleteTag_ShouldInvokeDeleteById() {
        int tagId = 1;

        tagService.deleteTag(tagId);

        verify(tagRepository, times(1)).deleteById(tagId);
    }
}
