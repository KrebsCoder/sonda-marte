package com.api.sondamarte.dtos;

import javax.validation.constraints.NotNull;

public class PlanetDto {

    @NotNull
    private String name;

    private int sizeX;

    private int sizeY;

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
