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

    public PlanetModel createPlanet(PlanetDto planetDto){
        Optional<PlanetModel> planet = planetRepository.findByName(planetDto.getName());
        if (planet.isPresent()){
            throw new RuntimeException("Planet already exists.");
        }
        PlanetModel planetModel = new PlanetModel(planetDto.getName(), planetDto.getSizeX(), planetDto.getSizeY());
        return save(planetModel);
    }

    @Transactional
    public PlanetModel save(PlanetModel planetModel){
        return planetRepository.save(planetModel);
    }

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


    public String deleteByName(String name) {
        Optional<PlanetModel> planetModelOptional = planetRepository.findByName(name);
        if (planetModelOptional.isEmpty()){
            throw new RuntimeException("Planet not found.");
        }
        planetRepository.delete(planetModelOptional.get());
        return "Planet deleted.";
    }

    @Transactional
    public PlanetModel changePlanetName(String name, PlanetDto planetDto) {
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(name);
        if (optionalPlanetModel.isEmpty()){
            throw new RuntimeException("Planet not found.");
        }
        var planetModel = new PlanetModel(
                optionalPlanetModel.get().getId(),
                planetDto.getName(),
                planetDto.getSizeX(),
                planetDto.getSizeY());
        return save(planetModel);
    }
}
