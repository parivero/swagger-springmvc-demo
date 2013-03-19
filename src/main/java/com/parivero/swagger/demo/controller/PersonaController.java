/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.parivero.swagger.demo.controller;

import com.mangofactory.swagger.annotations.ApiError;
import com.mangofactory.swagger.annotations.ApiErrors;
import com.mangofactory.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.parivero.swagger.demo.domain.Persona;
import com.parivero.swagger.demo.exception.NotFoundException;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author parivero
 */
@Controller
@RequestMapping("/personas")
@Api(value = "", description = "Operaciones sobre el recurso Persona")
public class PersonaController {

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Lista todas las personas")
    @ApiModel(type = Persona.class, collection = true)
    public @ResponseBody
    Collection<Persona> buscarTodos() {
        Persona persona = new Persona();
        persona.setId(Long.MIN_VALUE);
        persona.setNombre("Coco");
        Collection<Persona> demos = new ArrayList<>();
        demos.add(persona);
        demos.add(persona);
        return demos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Busca una persona por su id",
            responseClass = "{\n"
            + "  \"id\": \"number\",\n"
            + "  \"nombre\": \"string\"\n"
            + "}")
    @ApiErrors(errors = {
        @ApiError(code = 404, reason = "Recurso no encontrado")})
    public @ResponseBody
    Persona buscarPorId(@ApiParam(value = "id por el cual filtar", required = true)
            @PathVariable("id") Long id) {
        if (id == 0) {
            throw new NotFoundException();
        }
        Persona persona = new Persona();
        persona.setId(id);
        persona.setNombre("Coco");
        return persona;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Borra una persona por su id")
    @ApiErrors(errors = {
        @ApiError(code = 404, reason = "Recurso no encontrado")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarPorId(@ApiParam(value = "id de persona a borrar", required = true) @PathVariable("id") Long id) {
        if (id == 0) {
            throw new NotFoundException();
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Alta de Persona")
    @ApiErrors(errors = {
        @ApiError(code = 400, reason = "request invalido")})
    @ApiModel(type = Persona.class)
    public @ResponseBody
    Persona alta(@ApiParam(value = "recurso a crear sin id", required = true)
            @RequestBody Persona persona) {
        if (persona.getId() != null) {
            throw new IllegalArgumentException();
        }
        persona.setId(Long.MAX_VALUE);
        persona.setNombre(persona.getNombre() + "- Modificado");
        return persona;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Modificación de Persona")
    @ApiErrors(errors = {
        @ApiError(code = 400, reason = "Request invalido")})
    public void modificacion(@ApiParam(value = "recurso a modificar", required = true)
            @RequestBody Persona persona) {
        if (persona.getId() == null) {
            throw new IllegalArgumentException();
        }

        if (persona.getId() == 0) {
            throw new NotFoundException();

        }
    }

   
}
