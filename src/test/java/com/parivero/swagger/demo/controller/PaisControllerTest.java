/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.parivero.swagger.demo.controller;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author parivero
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-servlet.xml")
@WebAppConfiguration
public class PaisControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void buscarTodos_retornaDosPaises() throws Exception {

        this.mockMvc.perform(get("/paises"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)));
    }

    /**
     * Test of buscarPorId method, of class PaisController.
     */
    @Test
    public void buscarPorId_ConIdInexistente_RetornaNotFound() throws Exception {
        this.mockMvc.perform(get("/paises/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void buscarPorId_ConIdExistente_RetornaPais() throws Exception {
        this.mockMvc.perform(get("/paises/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Argentina"));
    }

    /**
     * Test of alta method, of class PaisController.
     */
    @Test
    public void alta_conRecursoValido_retornaPaisConNombreCreado() throws Exception {
        this.mockMvc.perform(post("/paises")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Argentina\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(containsString("Creado")));
    }

    @Test
    public void alta_conRecursoInValido_retornaBadRequest() throws Exception {
        this.mockMvc.perform(post("/paises")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0\"nombre\":\"Argentina\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void modificacion_conRecursoInValido_retornaBadRequest() throws Exception {
        this.mockMvc.perform(put("/paises")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"nombre\":\"Argentina\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void modificacion_conIdExistente_retornaNoContent() throws Exception {
        this.mockMvc.perform(put("/paises")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"nombre\":\"Argentina\"}"))
                .andExpect(status().isNoContent());

    }

    @Test
    public void modificacion_conIdNoExistente_retornaNotFound() throws Exception {
        this.mockMvc.perform(put("/paises")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0, \"nombre\":\"Argentina\"}"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void borrarPorId_conIdNoExistente_retornaNotFound() throws Exception {
        this.mockMvc.perform(delete("/paises/0"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void borrarPorId_conIdExistente_retornaNoContent() throws Exception {
        this.mockMvc.perform(delete("/paises/1"))
                .andExpect(status().isNoContent());

    }
}