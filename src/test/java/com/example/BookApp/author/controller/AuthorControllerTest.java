package com.example.BookApp.author.controller;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorVM;
import com.example.BookApp.author.mapper.AuthorVMMapper;
import com.example.BookApp.author.service.AuthorJpaService;
import com.example.BookApp.common.Audit;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuthorController authorController;

    @MockBean
    private AuthorJpaService authorJpaServiceMock;

    @MockBean
    private AuthorVMMapper authorVMMapperMock;

    private final Long ID = 1L;
    private final Long VERSION = 1L;
    private final String NAME = "name";
    private final String SURNAME = "surname";
    private AuthorVM input;
    private Author toSave;
    private Author saved;
    private AuthorVM output;

    @BeforeEach
    public void before() {
        input = createAuthorVM(null, NAME, SURNAME, null);
        toSave = createAuthor(null, NAME, SURNAME, null);
        saved = createAuthor(ID, NAME, SURNAME, VERSION);
        output = createAuthorVM(ID, NAME, SURNAME, VERSION);
    }

    @Test
    void save() throws Exception {

        // given
        Mockito.when(authorVMMapperMock.toAuthor(any())).thenReturn(toSave);
        Mockito.when(authorJpaServiceMock.saveAuthor(toSave)).thenReturn(saved);
        Mockito.when(authorVMMapperMock.toAuthorVM(saved)).thenReturn(output);

        // when

        ResultActions response = mockMvc.perform(post("/author/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        );

        // then

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(NAME)))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(SURNAME)))
                .andExpect(jsonPath("$.id", CoreMatchers.is(ID.intValue())))
                .andExpect(jsonPath("$.version", CoreMatchers.is(VERSION.intValue())))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(authorVMMapperMock, Mockito.times(1)).toAuthor(any());
        Mockito.verify(authorJpaServiceMock, Mockito.times(1)).saveAuthor(toSave);
        Mockito.verify(authorVMMapperMock, Mockito.times(1)).toAuthorVM(saved);
    }

    @Test
    void findAuthorById() throws Exception {

        // given
        Mockito.when(authorJpaServiceMock.findById(1L)).thenReturn(saved);
        Mockito.when(authorVMMapperMock.toAuthorVM(saved)).thenReturn(output);

        // when
        ResultActions response = mockMvc.perform(get("/author/find/" + ID.intValue())
                .contentType(MediaType.APPLICATION_JSON));
        // then

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(NAME)))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(SURNAME)))
                .andExpect(jsonPath("$.id", CoreMatchers.is(ID.intValue())))
                .andExpect(jsonPath("$.version", CoreMatchers.is(VERSION.intValue())))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(authorJpaServiceMock, Mockito.times(1)).findById(ID);
        Mockito.verify(authorVMMapperMock, Mockito.times(1)).toAuthorVM(saved);
    }

    private Author createAuthor(Long id, String name, String surname, Long version) {
        return Author.builder()
                .firstName(name)
                .lastName(surname)
                .id(id)
                .version(version)
                .audit(new Audit())
                .build();
    }

    private AuthorVM createAuthorVM(Long id, String name, String surname, Long version) {
        return AuthorVM.builder()
                .firstName(name)
                .lastName(surname)
                .id(id)
                .version(version)
                .build();
    }

}