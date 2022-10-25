package com.api.sondamarte.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
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
    @Column(nullable = false, length = 1)
    private char facingPosition;

    @Column(nullable = false, length = 255)
    private String planetName;

    public ProbeModel(String name, int startPositionX, int startPositionY, char facingPosition, String planetName) {
        this.name = name;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.facingPosition = facingPosition;
        this.planetName = planetName;
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

    public char getFacingPosition() {
        return facingPosition;
    }
}
