package com.api.sondamarte.dtos;

import javax.validation.constraints.NotNull;

public class ProbeDto {

    @NotNull
    private String name;
    @NotNull
    private int startPositionX;
    @NotNull
    private int startPositionY;
    @NotNull
    private String facingPosition;
    @NotNull
    private String planetName;


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
}
