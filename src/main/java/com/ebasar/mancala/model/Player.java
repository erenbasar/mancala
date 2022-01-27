package com.ebasar.mancala.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

@Getter
@Setter
@Entity
public class Player {

    @Id
    private String name;
    private int homePit;
    private int[] pits;
    private boolean isActive;

    public Player(String name, int stoneCount, int boardSize, boolean isActive) {
        this.name = name;
        this.isActive = isActive;
        pits = new int[boardSize];
        Arrays.fill(pits, stoneCount);
        homePit = 0;
    }

    protected Player() {

    }

    public int getStoneNumberByPit(int pit) {
        return pits[pit];
    }

    public void setStoneNumberByPit(int pit, int count) {
        pits[pit] = count;
    }

    public void addStoneToPit(int pit) {
        pits[pit]++;
    }

    public void addStonesToHomePit(int count) {
        homePit += count;
    }
}