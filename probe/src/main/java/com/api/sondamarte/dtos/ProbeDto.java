package com.api.sondamarte.dtos;

import javax.validation.constraints.NotNull;

public class ProbeDto {

    @NotNull
    private String name;

    private int startPositionX;

    private int startPositionY;

    private String facingPosition;
    private String planetName;

    private String movement;

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

    public String getPlanetName() {
        return planetName;
    }

    public String getMovement() {
        return movement;
    }
}
