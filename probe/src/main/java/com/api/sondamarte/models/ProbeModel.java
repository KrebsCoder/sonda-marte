package com.api.sondamarte.models;


import com.api.sondamarte.enums.ProbeDirection;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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
    // charAt[0]
    @Column(nullable = false, length = 5)
    private ProbeDirection direction;

    @ManyToOne
    private PlanetModel planet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProbeModel that = (ProbeModel) o;
        return positionX == that.positionX && positionY == that.positionY && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(direction, that.direction) && Objects.equals(planet, that.planet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, positionX, positionY, direction, planet);
    }

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

    public void changeDirection(ProbeModel probeModel, ProbeDirection direction) {
        this.direction = direction;
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
}
