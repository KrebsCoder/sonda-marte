package com.api.sondamarte.services;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import com.api.sondamarte.repositories.PlanetRepository;
import com.api.sondamarte.repositories.ProbeRepository;
import com.api.sondamarte.enums.ProbeDirection;
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
    public List<ProbeModel> deleteAll() {
        List<ProbeModel> probes = probeRepository.findAll();
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

    public ResponseEntity<Object> changeProbeDirection(ProbeDto probeDto) {
        Optional<ProbeModel> optionalProbeModel = probeRepository.findByName(probeDto.getName());
        if (optionalProbeModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Probe not found.");
        }
        for (int i = 0; i < probeDto.getMovement().length(); i++){
            if (probeDto.getMovement().charAt(i) == 'L'){
                handleMovementLeft(optionalProbeModel.get());
            } else if (probeDto.getMovement().charAt(i) == 'R'){
                handleMovementRight(optionalProbeModel.get());
            } else if (probeDto.getMovement().charAt(i) == 'M'){
                handleMovement(optionalProbeModel.get());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(save(optionalProbeModel.get()));
    }

    private void handleMovement(ProbeModel probeModel) {

        switch (probeModel.getDirection()){
            case NORTH -> moveProbeNorth(probeModel);
            case SOUTH -> moveProbeSouth(probeModel);
            case EAST -> moveProbeEast(probeModel);
            case WEST -> moveProbeWest(probeModel);
        }
    }

    private void moveProbeWest(ProbeModel probeModel) {
        int x = probeModel.getPositionX();

        if (probeModel.getPositionX() == 0){
            probeModel.changePositionX(probeModel.getPlanet().getSizeX());
        } else {
            probeModel.decreasePositionX();
        }
    }

    private void moveProbeEast(ProbeModel probeModel) {
        int x = probeModel.getPositionX();

        if (probeModel.getPositionX() == probeModel.getPlanet().getSizeX()){
            probeModel.changePositionX(0);
        } else {
            probeModel.increasePositionX();
        }
    }

    private void moveProbeSouth(ProbeModel probeModel) {
        int y = probeModel.getPositionY();

        if (probeModel.getPositionY() == 0){
            probeModel.changePositionY(probeModel.getPlanet().getSizeY());
        } else {
            probeModel.decreasePositionY();
        }
    }

    private void moveProbeNorth(ProbeModel probeModel) {
        int y = probeModel.getPositionY();

        if (probeModel.getPositionY() == probeModel.getPlanet().getSizeY()){
            probeModel.changePositionY(0);
        } else {
            probeModel.increasePositionY();
        }
    }


    private void handleMovementRight(ProbeModel probeModel) {
        switch (probeModel.getDirection()){
            case NORTH -> probeModel.changeDirection(probeModel, ProbeDirection.EAST);
            case SOUTH -> probeModel.changeDirection(probeModel, ProbeDirection.WEST);
            case EAST -> probeModel.changeDirection(probeModel, ProbeDirection.SOUTH);
            case WEST -> probeModel.changeDirection(probeModel, ProbeDirection.NORTH);
        }
    }

    private void handleMovementLeft(ProbeModel probeModel) {
        switch (probeModel.getDirection()) {
            case NORTH -> probeModel.changeDirection(probeModel, ProbeDirection.WEST);
            case SOUTH -> probeModel.changeDirection(probeModel, ProbeDirection.EAST);
            case EAST -> probeModel.changeDirection(probeModel, ProbeDirection.NORTH);
            case WEST -> probeModel.changeDirection(probeModel, ProbeDirection.SOUTH);
        }
    }
}
