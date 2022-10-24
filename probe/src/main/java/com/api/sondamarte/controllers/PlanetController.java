package com.api.sondamarte.controllers;

import com.api.sondamarte.dtos.PlanetDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.services.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    // check if there is no planet like the one received in the json || json should contain the planet name
    //
    @GetMapping
    public ResponseEntity<List<PlanetModel>> getAllPlanets(){
        return ResponseEntity.status(HttpStatus.OK).body(planetService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getPlanetByName(@PathVariable(value = "name") String name){
        Optional<PlanetModel> planetModelOptional = planetService.findByName(name);
        if (planetModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(planetModelOptional.get());
    }
}
