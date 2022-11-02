package com.api.sondamarte.models;

import com.api.sondamarte.enums.ProbeDirection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_PROBES")
public class ProbeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int positionX;
    @Column(nullable = false)
    private int positionY;

    @Column(nullable = false, length = 5)
    private ProbeDirection direction;

    @ManyToOne
    private PlanetModel planet;

    public ProbeModel(String name, int positionX, int positionY, String direction, PlanetModel planet) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = ProbeDirection.valueOf(direction);
        this.planet = planet;
    }

    public ProbeModel(UUID id, String name, int positionX, int positionY, String direction, PlanetModel planet) {
        this.id = id;
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = ProbeDirection.valueOf(direction);
        this.planet = planet;
    }

    public ProbeModel() {
    }

    public PlanetModel getPlanet() {
        return planet;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public ProbeDirection getDirection() {
        return direction;
    }

    public void turnLeft(){
        this.direction = direction.left();
    }

    public void turnRight(){
        this.direction = direction.right();
    }

    public void changePositionY(int positionY) {
        this.positionY = positionY;
    }

    public void changePositionX(int positionX) {
        this.positionX = positionX;
    }

    public void decreasePositionX() {
        positionX -= 1;
    }
    public void increasePositionX() {
        positionX += 1;
    }
    public void decreasePositionY() {
        positionY -= 1;
    }
    public void increasePositionY() {
        positionY += 1;
    }

    public void moveProbe(String movement){
        for (int i = 0; i < movement.length(); i++){
            if (movement.charAt(i) == 'L'){
                turnLeft();
            } else if (movement.charAt(i) == 'R'){
                turnRight();
            } else if (movement.charAt(i) == 'M'){
                if (!handleMovement()){
                    throw new RuntimeException("There's a obstacle in the path.");
                }
            }
        }
    }

    public boolean handleMovement() {

        if (!validateProbePosXPosY(getPositionX(), getPositionY())){
            return false;
        }
        switch (getDirection()){
            case NORTH ->  moveNorth();
            case SOUTH ->  moveSouth();
            case EAST ->  moveEast();
            case WEST ->  moveWest();
        }
        return true;
    }
//    public boolean validateProbeCreationPosXPosY(int posX, int posY) {
//        List<ProbeModel> probesList = getPlanet().getProbes();
//
//        for (ProbeModel probe : probesList){
//            if (posX == probe.getPositionX() && posY == probe.getPositionY()){
//                return false;
//            }
//        }
//        return true;
//    }

    public boolean validateProbePosXPosY(int posX, int posY) {
        List<ProbeModel> probesList = getPlanet().getProbes();

        for (ProbeModel probe : probesList){
            if (!(probe.getName().equals(getName())) && posX == probe.getPositionX() && posY == probe.getPositionY()){
                return false;
            }
        }
        return true;
    }

    public void moveWest() {
        if (getPositionX() == 0){
            changePositionX(getPlanet().getSizeX());
        } else {
            decreasePositionX();
        }
    }

    public void moveEast() {
        if (getPositionX() == getPlanet().getSizeX()){
            changePositionX(0);
        } else {
            increasePositionX();
        }
    }

    public void moveSouth() {
        if (getPositionY() == 0){
            changePositionY(getPlanet().getSizeY());
        } else {
            decreasePositionY();
        }
    }

    public void moveNorth() {
        if (getPositionY() == getPlanet().getSizeY()){
            changePositionY(0);
        } else {
            increasePositionY();
        }
    }
}
