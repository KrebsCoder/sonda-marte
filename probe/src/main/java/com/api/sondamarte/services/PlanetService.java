package com.api.sondamarte.services;

import com.api.sondamarte.dtos.PlanetDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.repositories.PlanetRepository;
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

    public List<PlanetModel> findAll() {
        return planetRepository.findAll();
    }

    public Optional<PlanetModel> findByName(String name) {
        return planetRepository.findByName(name);
    }
}
