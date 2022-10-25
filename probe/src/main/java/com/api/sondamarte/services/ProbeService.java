package com.api.sondamarte.services;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.ProbeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProbeService {

    ProbeRepository probeRepository;

    private final PlanetService planetService;

    public ProbeService(ProbeRepository probeRepository, PlanetService planetService) {
        this.probeRepository = probeRepository;
        this.planetService = planetService;
    }

    public ProbeModel createProbe(ProbeDto probeDto){
        Optional<PlanetModel> planetName = planetService.findByName(probeDto.getPlanetName());
        // TODO validar se a sonda tem o nome do planeta
        return new ProbeModel(
                probeDto.getName(),
                probeDto.getStartPositionX(),
                probeDto.getStartPositionY(),
                probeDto.getFacingPosition(),
                planetName.get());
    }


    @Transactional
    public ProbeModel save(ProbeModel probe) {
        return probeRepository.save(probe);
    }

    public List<ProbeModel> findAll() {
        return probeRepository.findAll();
    }
    public Optional<ProbeModel> findByName(String name) {
        return probeRepository.findByName(name);
    }

    @Transactional
    public List<ProbeModel> deleteAll() {
        List<ProbeModel> probes = findAll();
        for (ProbeModel probe : probes){
            probeRepository.deleteByName(probe.getName());
        }
        return probes;
    }
}
