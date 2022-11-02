package com.api.sondamarte.services;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.PlanetRepository;
import com.api.sondamarte.repositories.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProbeService {

    ProbeRepository probeRepository;
    PlanetRepository planetRepository;

    @Autowired
    public ProbeService(ProbeRepository probeRepository, PlanetRepository planetRepository) {
        this.probeRepository = probeRepository;
        this.planetRepository = planetRepository;
    }

    public ProbeModel createProbe(ProbeDto probeDto){
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(probeDto.getPlanetName());
        // !validateProbeCreation(probeDto, optionalPlanetModel.get()) ||
        if (optionalPlanetModel.isEmpty() ||  !validateProbeName(probeDto.getName())){
            throw new RuntimeException("Invalid information on probe creation.");
        }
        var probeModel = new ProbeModel(
                probeDto.getName(),
                probeDto.getStartPositionX(),
                probeDto.getStartPositionY(),
                probeDto.getFacingPosition(),
                optionalPlanetModel.get());
        return save(probeModel);
    }

//    private boolean validateProbeCreation(ProbeDto probeDto, PlanetModel planet) {
//        int posY = probeDto.getStartPositionY();
//        int posX = probeDto.getStartPositionX();
//        List<ProbeModel> probeModelList = planet.getProbes();
//
//        for (ProbeModel probe : probeModelList){
//            if (!validateProbeCreationPosXPosY(probe, posX, posY)){
//                return false;
//            }
//        }
//        if ((posY > planet.getSizeY() || posY < 0) || (posX > planet.getSizeX() || posX < 0)){
//            return false;
//        }
//        return true;
//    }
    private boolean validateProbeName(String name) {
        Optional<ProbeModel> probe = probeRepository.findByName(name);
        if (probe.isPresent()){
            return false;
        }
        return true;
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
    public ProbeModel findByName(String name) {
        Optional<ProbeModel> probeModelOptional = probeRepository.findByName(name);
        if (probeModelOptional.isEmpty()){
            throw new RuntimeException("Probe not found.");
        }
        return probeModelOptional.get();
    }

    @Transactional
    public ProbeModel deleteByName(String name) {
        Optional<ProbeModel> probe = probeRepository.findByName(name);
        if (probe.isEmpty()){
            throw new RuntimeException("Probe not found.");
        }
        probeRepository.deleteByName(name);
        return probe.get();
    }

    public ProbeModel changeProbeName(String name, ProbeDto probeDto) {
        Optional<ProbeModel> optionalProbeModel = probeRepository.findByName(name);
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(probeDto.getPlanetName());

        if (optionalProbeModel.isEmpty()){
            throw new RuntimeException("Probe not found.");
        } else if (optionalPlanetModel.isEmpty()){
            throw new RuntimeException("Probe planet not found.");
        }

        var probeModel = new ProbeModel(
                optionalProbeModel.get().getId(),
                probeDto.getName(),
                optionalProbeModel.get().getPositionX(),
                optionalProbeModel.get().getPositionY(),
                optionalProbeModel.get().getDirection().toString(),
                optionalPlanetModel.get());

        return save(probeModel);
    }

    public ProbeModel changeProbeDirection(ProbeDto probeDto) {
        Optional<ProbeModel> optionalProbeModel = probeRepository.findByName(probeDto.getName());
        if (optionalProbeModel.isEmpty()){
            throw new RuntimeException("Probe not found.");
        }
        optionalProbeModel.get().moveProbe(probeDto.getMovement());
        return save(optionalProbeModel.get());
    }
}
