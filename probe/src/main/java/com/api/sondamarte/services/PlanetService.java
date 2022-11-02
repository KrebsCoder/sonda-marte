package com.api.sondamarte.services;

import com.api.sondamarte.dtos.PlanetDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        PlanetModel planetModel = findPlanetByName(name);
        planetRepository.deleteByName(name);
        return planetModel;
    }

    @Transactional
    public PlanetModel changePlanetName(String name, PlanetDto planetDto) {
        PlanetModel planet = findPlanetByName(name);
        PlanetModel planetModel = new PlanetModel(
                planet.getId(),
                planetDto.getName(),
                planet.getSizeX(),
                planet.getSizeY());
        return planetRepository.save(planetModel);
    }

    public PlanetModel findPlanetByName(String name) {
        Optional<PlanetModel> planet = planetRepository.findByName(name);
        if (planet.isEmpty()){
            throw new RuntimeException("Planet not found.");
        }
        return planet.get();
    }
}
