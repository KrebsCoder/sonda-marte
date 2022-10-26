package com.api.sondamarte.services;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.PlanetRepository;
import com.api.sondamarte.repositories.ProbeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProbeService {

    ProbeRepository probeRepository;
    PlanetRepository planetRepository;

    public ProbeService(ProbeRepository probeRepository, PlanetRepository planetRepository) {
        this.probeRepository = probeRepository;
        this.planetRepository = planetRepository;
    }

    public ResponseEntity<Object> createProbe(ProbeDto probeDto){
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(probeDto.getPlanetName());
        if (optionalPlanetModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe planet not found.");
        }
        var probeModel = new ProbeModel(
                probeDto.getName(),
                probeDto.getStartPositionX(),
                probeDto.getStartPositionY(),
                probeDto.getFacingPosition(),
                optionalPlanetModel.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(save(probeModel));
    }


    @Transactional
    public ProbeModel save(ProbeModel probe) {
        return probeRepository.save(probe);
    }

    public List<ProbeModel> findAll() {
        return probeRepository.findAll();
    }
    public ResponseEntity<Object> findByName(String name) {
        Optional<ProbeModel> probeModelOptional = probeRepository.findByName(name);
        if (probeModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(probeModelOptional.get());
    }

    @Transactional
    public List<ProbeModel> deleteAll() {
        List<ProbeModel> probes = findAll();
        for (ProbeModel probe : probes){
            probeRepository.deleteByName(probe.getName());
        }
        return probes;
    }

    @Transactional
    public ResponseEntity<Object> deleteByName(String name) {
        Optional<ProbeModel> probe = probeRepository.findByName(name);
        if (probe.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found.");
        }
        probeRepository.deleteByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(probe.get());
    }

    public ResponseEntity<Object> changeProbeName(String name, ProbeDto probeDto) {
        Optional<ProbeModel> optionalProbeModel = probeRepository.findByName(name);
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(probeDto.getPlanetName());

        if (optionalProbeModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found.");
        } else if (optionalPlanetModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe planet not found.");
        }

        var probeModel = new ProbeModel(
                optionalProbeModel.get().getId(),
                probeDto.getName(),
                probeDto.getStartPositionX(),
                probeDto.getStartPositionY(),
                probeDto.getFacingPosition(),
                optionalPlanetModel.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(save(probeModel));
    }
}
