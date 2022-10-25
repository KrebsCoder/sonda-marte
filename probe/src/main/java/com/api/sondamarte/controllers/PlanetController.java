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
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.save(planetService.createPlanet(planetDto)));
    }

    // check if there is no planet like the one received in the json || json should contain the planet name
    //
    // refatorar, retornar algo mensagem caso não ache nenhum planeta.
    @GetMapping
    public ResponseEntity<Object> getAllPlanets(){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getPlanetByName(@PathVariable(value = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.findByName(name));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllPlanets(){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.deleteAll());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> deletePlanetByName(@PathVariable(value = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.deleteByName(name));
    }

    // TODO: still needs to add put method to modify the planet name
}
