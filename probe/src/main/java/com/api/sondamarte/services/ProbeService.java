package com.api.sondamarte.services;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProbeService {

    ProbeRepository probeRepository;

    PlanetService planetService;

    @Autowired
    public ProbeService(ProbeRepository probeRepository, PlanetService planetService) {
        this.probeRepository = probeRepository;
        this.planetService = planetService;
    }

    public ProbeModel createProbe(ProbeDto probeDto){
        PlanetModel planet = planetService.findPlanetByName(probeDto.getPlanetName());
        Optional<ProbeModel> probe = probeRepository.findByName(probeDto.getName());

        if (probe.isPresent()){
            throw new RuntimeException("Probe already exists.");
        }
        ProbeModel probeModel = new ProbeModel(
                probeDto.getName(),
                probeDto.getStartPositionX(),
                probeDto.getStartPositionY(),
                probeDto.getFacingPosition(),
                planet);
        probeModel.validateProbeCreationPosXPosY(probeDto);
        return save(probeModel);
    }

    public ProbeModel findProbeByName(String name) {
        Optional<ProbeModel> probe = probeRepository.findByName(name);

        if (probe.isEmpty()){
            throw new RuntimeException("Probe not found.");
        }
        return probe.get();
    }

    @Transactional
    public ProbeModel save(ProbeModel probe) {
        return probeRepository.save(probe);
    }

    public List<ProbeModel> findAll() {
        Optional<List<ProbeModel>> optionalProbeModelList = Optional.of(probeRepository.findAll());

        if (optionalProbeModelList.get().isEmpty()){
            throw new RuntimeException("Probe not found.");
        }
        return optionalProbeModelList.get();
    }

    @Transactional
    public ProbeModel deleteByName(String name) {
        ProbeModel probe = findProbeByName(name);

        probeRepository.deleteByName(name);
        return probe;
    }

    public ProbeModel changeProbeName(String name, ProbeDto probeDto) {
        PlanetModel planet = planetService.findPlanetByName(probeDto.getPlanetName());
        ProbeModel probe = findProbeByName(name);

        var probeModel = new ProbeModel(
                probe.getId(),
                probeDto.getName(),
                probe.getPositionX(),
                probe.getPositionY(),
                probe.getDirection().toString(),
                planet);
        return save(probeModel);
    }

    public ProbeModel changeProbeDirection(ProbeDto probeDto) {
        ProbeModel probe = findProbeByName(probeDto.getName());

        probe.moveProbe(probeDto.getMovement());;
        return save(probe);
    }
}
