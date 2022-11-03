package com.api.sondamarte.controllers;

import com.api.sondamarte.dtos.PlanetDto;
import com.api.sondamarte.services.PlanetService;
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
    public ResponseEntity<Object> createPlanet(@RequestBody @Valid PlanetDto planetDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.createPlanet(planetDto));
    }

    @GetMapping
    public ResponseEntity<Object> getAllPlanets(){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getPlanetByName(@PathVariable(value = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.findPlanetByName(name));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> deletePlanetByName(@PathVariable(value = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.deleteByName(name));
    }

    @PutMapping("/{name}")
    public ResponseEntity<Object> changePlanetName(@PathVariable(value = "name") String name,
                                                   @RequestBody @Valid PlanetDto planetDto){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.changePlanetName(name, planetDto));
    }
}
