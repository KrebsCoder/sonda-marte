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

    @Transactional
    public PlanetModel createPlanet(PlanetDto planetDto){
        Optional<PlanetModel> planet = planetRepository.findByName(planetDto.getName());
        if (planet.isPresent()){
            throw new RuntimeException("Planet already exists.");
        }
        return planetRepository.save(new PlanetModel(planetDto.getName(), planetDto.getSizeX(), planetDto.getSizeY()));
    }

    // could be improved with pagination
    public List<PlanetModel> findAll() {
        Optional<List<PlanetModel>> planetModelOptional = Optional.of(planetRepository.findAll());
        if (planetModelOptional.get().isEmpty()){
            throw new RuntimeException("Planet not found.");
        }
        return planetModelOptional.get();
    }

    public PlanetModel findByName(String name) {
        Optional<PlanetModel> planetModelOptional = planetRepository.findByName(name);
        if (planetModelOptional.isEmpty()){
            throw new RuntimeException("Planet not found.");
        }
        return planetModelOptional.get();
    }


    @Transactional
    public PlanetModel deleteByName(String name) {
        Optional<PlanetModel> planetModelOptional = planetRepository.findByName(name);
        if (planetModelOptional.isEmpty()){
            throw new RuntimeException("Planet not found.");
        }
        planetRepository.delete(planetModelOptional.get());
        return planetModelOptional.get();
    }

    @Transactional
    public PlanetModel changePlanetName(String name, PlanetDto planetDto) {
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(name);
        if (optionalPlanetModel.isEmpty()){
            throw new RuntimeException("Planet not found.");
        }
        PlanetModel planetModel = new PlanetModel(
                optionalPlanetModel.get().getId(),
                planetDto.getName(),
                optionalPlanetModel.get().getSizeX(),
                optionalPlanetModel.get().getSizeY());
        return planetRepository.save(planetModel);
    }
}
