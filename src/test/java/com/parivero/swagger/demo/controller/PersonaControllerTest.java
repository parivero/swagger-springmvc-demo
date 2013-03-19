/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.parivero.swagger.demo.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author parivero
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-servlet.xml")
@WebAppConfiguration
public class PersonaControllerTest {
    
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void buscarTodos_retornaDosPersonas() throws Exception {

        this.mockMvc.perform(get("/personas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)));
    }

    /**
     * Test of buscarPorId method, of class PaisController.
     */
    @Test
    public void buscarPorId_ConIdInexistente_RetornaNotFound() throws Exception {
        this.mockMvc.perform(get("/personas/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void buscarPorId_ConIdExistente_RetornaPais() throws Exception {
        this.mockMvc.perform(get("/personas/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Coco"));
    }

    /**
     * Test of alta method, of class PaisController.
     */
    @Test
    public void alta_conRecursoValido_retornaPaisConNombreCreado() throws Exception {
        this.mockMvc.perform(post("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Coco\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(containsString("Modificado")));
    }

    @Test
    public void alta_conRecursoInValido_retornaBadRequest() throws Exception {
        this.mockMvc.perform(post("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0\"nombre\":\"Coco\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void modificacion_conRecursoInValido_retornaBadRequest() throws Exception {
        this.mockMvc.perform(put("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"nombre\":\"Coco\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void modificacion_conIdExistente_retornaNoContent() throws Exception {
        this.mockMvc.perform(put("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"nombre\":\"Coco\"}"))
                .andExpect(status().isNoContent());

    }

    @Test
    public void modificacion_conIdNoExistente_retornaNotFound() throws Exception {
        this.mockMvc.perform(put("/personas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0, \"nombre\":\"Coco\"}"))
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void borrarPorId_conIdNoExistente_retornaNotFound() throws Exception {
        this.mockMvc.perform(delete("/personas/0"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void borrarPorId_conIdExistente_retornaNoContent() throws Exception {
        this.mockMvc.perform(delete("/personas/1"))
                .andExpect(status().isNoContent());

    }

}