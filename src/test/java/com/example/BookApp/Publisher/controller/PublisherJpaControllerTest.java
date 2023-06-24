package com.example.BookApp.publisher.controller;

import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherVM;
import com.example.BookApp.publisher.mapper.PublisherVMMapper;
import com.example.BookApp.publisher.service.PublisherJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PublisherJpaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PublisherJpaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PublisherJpaController publisherJpaController;

    @MockBean
    private PublisherJpaService publisherJpaServiceMock;

    @MockBean
    private PublisherVMMapper publisherVMMapperMock;

    PublisherVM input;
    Publisher toSave;
    Publisher saved;
    PublisherVM output;

    @BeforeEach
    public void before() {
        input = createPublisherVM(null, "name", null);
        toSave = createPublisher(null, "name", null);
        saved = createPublisher(1L, "name", 1L);
        output = createPublisherVM(1L, "name", 1L);
    }

    @Test
    void save() throws Exception {

        // given

        Mockito.when(publisherVMMapperMock.toPublisher(any())).thenReturn(toSave);
        Mockito.when(publisherJpaServiceMock.save(toSave)).thenReturn(saved);
        Mockito.when(publisherVMMapperMock.toPublisherVM(saved)).thenReturn(output);

        // when

        ResultActions response = mockMvc.perform(post("/publisher/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        );

        // then

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(output.getName())))
                .andExpect(jsonPath("$.id", CoreMatchers.is(output.getId().intValue())))
                .andExpect(jsonPath("$.version", CoreMatchers.is(output.getVersion().intValue())))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(publisherVMMapperMock, Mockito.times(1)).toPublisher(any());
        Mockito.verify(publisherJpaServiceMock, Mockito.times(1)).save(toSave);
        Mockito.verify(publisherVMMapperMock, Mockito.times(1)).toPublisherVM(saved);
    }

    private PublisherVM createPublisherVM(Long id, String name, Long version) {
        return PublisherVM.builder()
                .id(id)
                .name(name)
                .version(version)
                .build();
    }

    private Publisher createPublisher(Long id, String name, Long version) {
        return Publisher.builder()
                .id(id)
                .name(name)
                .version(version)
                .build();
    }

}


