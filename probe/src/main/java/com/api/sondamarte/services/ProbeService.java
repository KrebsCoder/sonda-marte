package com.api.sondamarte.services;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.PlanetRepository;
import com.api.sondamarte.repositories.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProbeService(ProbeRepository probeRepository, PlanetRepository planetRepository) {
        this.probeRepository = probeRepository;
        this.planetRepository = planetRepository;
    }

    public ResponseEntity<Object> createProbe(ProbeDto probeDto){
        Optional<PlanetModel> optionalPlanetModel = planetRepository.findByName(probeDto.getPlanetName());
        if (optionalPlanetModel.isEmpty() || !validateProbeCreation(probeDto, optionalPlanetModel) || !validateProbeName(probeDto.getName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid information on probe creation.");
        }
        var probeModel = new ProbeModel(
                probeDto.getName(),
                probeDto.getStartPositionX(),
                probeDto.getStartPositionY(),
                probeDto.getFacingPosition(),
                optionalPlanetModel.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(save(probeModel));
    }

    private boolean validateProbeCreation(ProbeDto probeDto, Optional<PlanetModel> optionalPlanetModel) {
        int posY = probeDto.getStartPositionY();
        int posX = probeDto.getStartPositionX();
        PlanetModel planet = optionalPlanetModel.get();
        List<ProbeModel> probeModelList = planet.getProbes();

        for (ProbeModel probe : probeModelList){
            if (!validateProbeCreationPosXPosY(probe, posX, posY)){
                return false;
            }
        }
        if ((posY > planet.getSizeY() || posY < 0) || (posX > planet.getSizeX() || posX < 0)){
            return false;
        }
        return true;
    }
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

    public ResponseEntity<Object> findAll() {
        Optional<List<ProbeModel>> optionalProbeModelList = Optional.of(probeRepository.findAll());
        if (optionalProbeModelList.get().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalProbeModelList.get());
    }
    public ResponseEntity<Object> findByName(String name) {
        Optional<ProbeModel> probeModelOptional = probeRepository.findByName(name);
        if (probeModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(probeModelOptional.get());
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

    public ProbeModel changeProbeDirection(ProbeDto probeDto) {
        Optional<ProbeModel> optionalProbeModel = probeRepository.findByName(probeDto.getName());
        if (optionalProbeModel.isEmpty()){
            throw new RuntimeException("Probe not found.");
        }
        for (int i = 0; i < probeDto.getMovement().length(); i++){
            if (probeDto.getMovement().charAt(i) == 'L'){
                optionalProbeModel.get().turnLeft();
            } else if (probeDto.getMovement().charAt(i) == 'R'){
                optionalProbeModel.get().turnRight();
            } else if (probeDto.getMovement().charAt(i) == 'M'){
                if (!handleMovement(optionalProbeModel.get())){
                    throw new RuntimeException("There's a obstacle in the path.");
                }
            }
        }
        return save(optionalProbeModel.get());
    }

    //avoid private methods
    private boolean handleMovement(ProbeModel probeModel) {

        if (!validateProbePosXPosY(probeModel, probeModel.getPositionX(), probeModel.getPositionY())){
            return false;
        }
        switch (probeModel.getDirection()){
            case NORTH ->  moveProbeNorth(probeModel);
            case SOUTH ->  moveProbeSouth(probeModel);
            case EAST ->  moveProbeEast(probeModel);
            case WEST ->  moveProbeWest(probeModel);
        }
        return true;
    }

    private boolean validateProbeCreationPosXPosY(ProbeModel probeModel, int posX, int posY) {
        List<ProbeModel> probesList = probeModel.getPlanet().getProbes();

        for (ProbeModel probe : probesList){
            if (posX == probe.getPositionX() && posY == probe.getPositionY()){
                return false;
            }
        }
        return true;
    }

    private boolean validateProbePosXPosY(ProbeModel probeModel, int posX, int posY) {
        List<ProbeModel> probesList = probeModel.getPlanet().getProbes();

        for (ProbeModel probe : probesList){
            if ((probe != probeModel) && posX == probe.getPositionX() && posY == probe.getPositionY()){
                return false;
            }
        }
        return true;
    }

    private void moveProbeWest(ProbeModel probeModel) {
        if (probeModel.getPositionX() == 0){
            probeModel.changePositionX(probeModel.getPlanet().getSizeX());
        } else {
            probeModel.decreasePositionX();
        }
    }

    private void moveProbeEast(ProbeModel probeModel) {
        if (probeModel.getPositionX() == probeModel.getPlanet().getSizeX()){
            probeModel.changePositionX(0);
        } else {
            probeModel.increasePositionX();
        }
    }

    private void moveProbeSouth(ProbeModel probeModel) {
        if (probeModel.getPositionY() == 0){
            probeModel.changePositionY(probeModel.getPlanet().getSizeY());
        } else {
            probeModel.decreasePositionY();
        }
    }

    private void moveProbeNorth(ProbeModel probeModel) {
        if (probeModel.getPositionY() == probeModel.getPlanet().getSizeY()){
            probeModel.changePositionY(0);
        } else {
            probeModel.increasePositionY();
        }
    }

}
