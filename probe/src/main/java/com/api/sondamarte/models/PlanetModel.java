package com.api.sondamarte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TB_PLANET")
public class PlanetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;
    @Column(length = 255)
    private String name;
    @Column(nullable = false)
    private int sizeX;
    @Column(nullable = false)
    private int sizeY;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanetModel that = (PlanetModel) o;
        return sizeX == that.sizeX && sizeY == that.sizeY && Objects.equals(Id, that.Id) && Objects.equals(name, that.name) && Objects.equals(probes, that.probes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, sizeX, sizeY, probes);
    }

    @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL)
    @JsonIgnore
    private final List<ProbeModel> probes = new ArrayList<>();


    public PlanetModel(String name, int sizeX, int sizeY) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public PlanetModel() {

    }
    public List<ProbeModel> getProbes() {
        return probes;
    }

    public String getName() {
        return name;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
