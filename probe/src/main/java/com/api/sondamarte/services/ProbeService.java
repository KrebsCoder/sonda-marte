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

    public ProbeModel createProbe(ProbeDto probeDto){
        Optional<PlanetModel> planetName = planetRepository.findByName(probeDto.getPlanetName());
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

    public ResponseEntity<Object> deleteByName(String name) {
        Optional<ProbeModel> probe = probeRepository.findByName(name);
        if (probe.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(probe.get());
    }
}
