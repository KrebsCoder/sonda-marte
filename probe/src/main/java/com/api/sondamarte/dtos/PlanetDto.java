package com.api.sondamarte.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PlanetDto {

    @NotNull
    private String name;
    @NotNull
    private int sizeX;
    @NotNull
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
