package com.api.sondamarte.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "TB_PLANET")
public class PlanetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(length = 255)
    private String name;
    @Column(nullable = false)
    private int sizeX;
    @Column(nullable = false)
    private int sizeY;

    public PlanetModel(String name, int sizeX, int sizeY) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public PlanetModel() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
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
