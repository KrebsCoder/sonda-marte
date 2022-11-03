package com.api.sondamarte.models;

import com.api.sondamarte.dtos.ProbeDto;
import com.api.sondamarte.enums.ProbeDirection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
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

    public void move(Move move){
        this.positionX = move.getX();
        this.positionY = move.getY();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProbeModel that = (ProbeModel) o;
        return positionX == that.positionX && positionY == that.positionY && Objects.equals(id, that.id) && Objects.equals(name, that.name) && direction == that.direction && Objects.equals(planet, that.planet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, positionX, positionY, direction, planet);
    }

    public void turnRight(){
        this.direction = direction.right();
    }

    public void moveProbe(String movement){
        for (int i = 0; i < movement.length(); i++){
            if (movement.charAt(i) == 'L'){
                turnLeft();
            } else if (movement.charAt(i) == 'R'){
                turnRight();
            } else if (movement.charAt(i) == 'M'){
                handleMovement();
            }
        }
    }

    public void handleMovement() {
        validateProbePosXPosY(getPositionX(), getPositionY());

        switch (getDirection()) {
            case NORTH, SOUTH ->  move(direction.getNextY(positionX, positionY));
            case EAST, WEST ->  move(direction.getNextX(positionX, positionY));
            default -> throw new RuntimeException("Not a valid direction");
        }
    }

    public void validateProbePosXPosY(int posX, int posY) {
        List<ProbeModel> probesList = getPlanet().getProbes();

        for (ProbeModel probe : probesList){
            if (!(probe.getName().equals(getName()) && posX == probe.getPositionX() && posY == probe.getPositionY())){
                throw new RuntimeException("There's a obstacle in the path.");
            }
        }
    }

    public void validateProbeCreationPosXPosY(ProbeDto probeDto) {
        List<ProbeModel> probesList = getPlanet().getProbes();

        for (ProbeModel probe : probesList){
            if (probeDto.getStartPositionX() == probe.getPositionX() && probeDto.getStartPositionY() == probe.getPositionY()){
                throw new RuntimeException("There's a probe on the creation position.");
            }
        }
    }
}
