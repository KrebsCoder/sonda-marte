package com.api.sondamarte.services;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.ProbeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProbeService {

    ProbeRepository probeRepository;

    public ProbeService(ProbeRepository probeRepository) {
        this.probeRepository = probeRepository;
    }

    public ProbeModel createProbe(ProbeDto probeDto){
        return new ProbeModel(
                probeDto.getName(),
                probeDto.getStartPositionX(),
                probeDto.getStartPositionY(),
                probeDto.getFacingPosition(),
                probeDto.getPlanetName());
    }


    @Transactional
    public ProbeModel save(ProbeModel probe) {
        return probeRepository.save(probe);
    }
}
