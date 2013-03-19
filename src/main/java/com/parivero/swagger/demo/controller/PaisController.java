/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.parivero.swagger.demo.controller;

import com.parivero.swagger.demo.domain.Pais;
import com.parivero.swagger.demo.exception.NotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author parivero
 */
@Controller
@RequestMapping("/paises")
public class PaisController {
    
    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Collection<Pais> buscarTodos() {
        Pais pais = new Pais();
        pais.setId(Long.MIN_VALUE);
        pais.setNombre("Argentina");
        Collection<Pais> paises = new ArrayList<>();
        paises.add(pais);
        paises.add(pais);
        return paises;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Pais buscarPorId(@PathVariable("id") Long id) {
        if (id == 0) {
            throw new NotFoundException();
        }
        Pais pasi = new Pais();
        pasi.setId(id);
        pasi.setNombre("Argentina");
        return pasi;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarPorId(@PathVariable("id") Long id) {
        if (id == 0) {
            throw new NotFoundException();
        }
        
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Pais alta(@RequestBody Pais pais) {
        if (pais.getId() != null ) {
            throw new IllegalArgumentException();
        }
        pais.setId(Long.MAX_VALUE);
        pais.setNombre(pais.getNombre() + "- Creado");
        return pais;
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modificacion(@RequestBody Pais pais) {
        if (pais.getId() == null ) {
            throw new IllegalArgumentException();
        }
        
        if (pais.getId() == 0) {
            throw new NotFoundException();
                    
        }
        
    }

       
}
