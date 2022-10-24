package com.api.sondamarte.controllers;

import com.api.sondamarte.dtos.PlanetDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.services.PlanetService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/planet")
public class PlanetController {

    final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    public ResponseEntity<Object> registerPlanet(@RequestBody @Valid PlanetDto planetDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.save(planetService.createPlanet(planetDto)));
    }
}
