package br.com.anibal.armory.simpleserver.controllers;

import br.com.anibal.armory.simpleserver.controllers.hateoas.assemblers.CursoModelAssembler;
import br.com.anibal.armory.simpleserver.entities.Curso;
import br.com.anibal.armory.simpleserver.services.CursoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CursosController.class)
@Import({CursoModelAssembler.class, CursoService.class})
class HateoasCursosControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CursoRepository repository;

    @Test
    void shouldFetchCursos() throws Exception {
        given(repository.findAll()).willReturn(
                Arrays.asList(
                        new Curso(1, "Java"),
                        new Curso(2, "Spring")));

        mvc.perform(get(CursosController.REQUEST_MAPPING))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.cursos[0].id", is(1)))
            .andReturn();
    }

    @Test
    void getCurso() {
    }
}