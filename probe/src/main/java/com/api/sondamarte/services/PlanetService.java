package com.api.sondamarte.services;

import com.api.sondamarte.dtos.PlanetDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    final PlanetRepository planetRepository;

    @Autowired
    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public ResponseEntity<Object> createPlanet(PlanetDto planetDto){
        Optional<PlanetModel> planet = planetRepository.findByName(planetDto.getName());
        if (planet.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Planet already exists.");
        }
        PlanetModel planetModel = new PlanetModel(planetDto.getName(), planetDto.getSizeX(), planetDto.getSizeY());
        return ResponseEntity.status(HttpStatus.OK).body(save(planetModel));
    }

    @Transactional
    public PlanetModel save(PlanetModel planetModel){
        return planetRepository.save(planetModel);
    }

    public ResponseEntity<Object> findAll() {
        Optional<List<PlanetModel>> planetModelOptional = Optional.of(planetRepository.findAll());
        if (planetModelOptional.get().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(planetModelOptional.get());
    }

    public ResponseEntity<Object> findByName(String name) {
        Optional<PlanetModel> planetModelOptional = planetRepository.findByName(name);
        if (planetModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(planetModelOptional.get());
    }


    public ResponseEntity<Object> deleteByName(String name) {
        Optional<PlanetModel> planetModelOptional = planetRepository.findByName(name);
        if (planetModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found.");
        }
        planetRepository.delete(planetModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("All planets were deleted.");
    }

    @Transactional
    public ResponseEntity<Object> changePlanetName(String name, PlanetDto planetDto) {
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(name);
        if (optionalPlanetModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found.");
        }
        var planetModel = new PlanetModel(
                optionalPlanetModel.get().getId(),
                planetDto.getName(),
                planetDto.getSizeX(),
                planetDto.getSizeY());
        return ResponseEntity.status(HttpStatus.CREATED).body(save(planetModel));
    }
}
