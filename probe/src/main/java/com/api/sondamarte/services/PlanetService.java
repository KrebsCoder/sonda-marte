package com.api.sondamarte.services;

import com.api.sondamarte.dtos.PlanetDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.repositories.PlanetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public PlanetModel createPlanet(PlanetDto planetDto){
        return new PlanetModel(planetDto.getName(), planetDto.getSizeX(), planetDto.getSizeY());
    }

    public int planetTotalSize(PlanetModel planetModel){
        return planetModel.getSizeX() * planetModel.getSizeY();
    }

    @Transactional
    public PlanetModel save(PlanetModel planetModel){
        return planetRepository.save(planetModel);
    }

    public ResponseEntity<Object> findAll() {
        int i = 0;
        Optional<List<PlanetModel>> planetModelOptional = Optional.of(planetRepository.findAll());
        for (PlanetModel planetModel : planetModelOptional.get()){
            i++;
        }
        if (i == 0){
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

    @Transactional
    public void delete(PlanetModel planetModel) {
        planetRepository.deleteByName(planetModel.getName());
    }

    @Transactional
    public List<PlanetModel> deleteAll() {
        List<PlanetModel> Planets = planetRepository.findAll();
        for (PlanetModel planet : Planets){
            planetRepository.deleteByName(planet.getName());
        }
        return Planets;
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
