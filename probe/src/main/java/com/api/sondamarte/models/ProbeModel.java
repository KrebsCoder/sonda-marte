package com.api.sondamarte.models;

public class ProbeModel {
    private String name;
    private int startPositionX;
    private int startPositionY;
    private char facingPosition;

    public ProbeModel(String name, int startPositionX, int startPositionY, char facingPosition) {
        this.name = name;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.facingPosition = facingPosition;
    }
}
