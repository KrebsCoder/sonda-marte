package com.api.sondamarte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_PLANET")
public class PlanetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int sizeX;
    @Column(nullable = false)
    private int sizeY;

    @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL)
    @JsonIgnore
    private final List<ProbeModel> probes = new ArrayList<>();

    public PlanetModel(String name, int sizeX, int sizeY) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public PlanetModel(UUID id, String name, int sizeX, int sizeY) {
        Id = id;
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public PlanetModel() {

    }

    public List<ProbeModel> getProbes() {
        return probes;
    }

    @JsonIgnore
    public UUID getId() {
        return Id;
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
