package com.api.sondamarte.models;


import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "TB_PROBES")
public class ProbeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false)
    private int startPositionX;
    @Column(nullable = false)
    private int startPositionY;
    // charAt[0]
    @Column(nullable = false, length = 1)
    private String facingPosition;

    @ManyToOne
    private PlanetModel planet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProbeModel that = (ProbeModel) o;
        return startPositionX == that.startPositionX && startPositionY == that.startPositionY && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(facingPosition, that.facingPosition) && Objects.equals(planet, that.planet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startPositionX, startPositionY, facingPosition, planet);
    }

    public ProbeModel(String name, int startPositionX, int startPositionY, String facingPosition, PlanetModel planet) {
        this.name = name;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.facingPosition = facingPosition;
        this.planet = planet;
    }

    public PlanetModel getPlanet() {
        return planet;
    }

    public ProbeModel() {
    }

    public String getName() {
        return name;
    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public String getFacingPosition() {
        return facingPosition;
    }
}
