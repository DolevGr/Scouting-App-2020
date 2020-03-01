package com.example.primo2020v1.utils;

import androidx.annotation.Nullable;

public class Pit {
    private String mass, comment,
            wheels, language, intake, carry, shoot;
    private boolean hasAuto, canTrench, canBumpers;
    private int endGame, cprc, cppc;

    public Pit(String mass, String comment, String language,
               String wheels, String intake, String carry, String shoot,
               boolean hasAuto, boolean canTrench, boolean canBumpers,
               int endGame, int cprc, int cppc) {
        this.mass = mass;
        this.language = language;
        this.comment = comment;
        this.wheels = wheels;
        this.intake = intake;
        this.carry = carry;
        this.shoot = shoot;
        this.hasAuto = hasAuto;
        this.canTrench = canTrench;
        this.canBumpers = canBumpers;
        this.endGame = endGame;
        this.cprc = cprc;
        this.cppc = cppc;
    }

    public Pit() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWheels() {
        return wheels;
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getCarry() {
        return carry;
    }

    public void setCarry(String carry) {
        this.carry = carry;
    }

    public String getShoot() {
        return shoot;
    }

    public void setShoot(String shoot) {
        this.shoot = shoot;
    }

    public boolean isHasAuto() {
        return hasAuto;
    }

    public void setHasAuto(boolean hasAuto) {
        this.hasAuto = hasAuto;
    }

    public boolean isCanTrench() {
        return canTrench;
    }

    public void setCanTrench(boolean canTrench) {
        this.canTrench = canTrench;
    }

    public boolean isCanBumpers() {
        return canBumpers;
    }

    public void setCanBumpers(boolean canBumpers) {
        this.canBumpers = canBumpers;
    }

    public int getEndGame() {
        return endGame;
    }

    public void setEndGame(int endGame) {
        this.endGame = endGame;
    }

    public int getCprc() {
        return cprc;
    }

    public void setCprc(int cprc) {
        this.cprc = cprc;
    }

    public int getCppc() {
        return cppc;
    }

    public void setCppc(int cppc) {
        this.cppc = cppc;
    }

    public int getWheelsIndex() {
        if (wheels.equals("6x6"))
            return 0;
        if (wheels.equals("8x8"))
            return 1;
        return 2;
    }

    public int getLanguageIndex() {
        if (language.equals("Java"))
            return 0;
        if (language.equals("C++"))
            return 1;
        return 2;
    }

    public int getIntakeIndex() {
        if (intake.equals("Floor"))
            return 0;
        if (intake.equals("Feeder"))
            return 1;
        if (intake.equals("Both"))
            return 2;
        return 3;
    }

    public int getCarryIndex() {
        if (carry.equals("0"))
            return 0;
        if (carry.equals("1"))
            return 1;
        if (carry.equals("2"))
            return 2;
        if (carry.equals("3"))
            return 3;
        if (carry.equals("4"))
            return 4;
        return 5;
    }

    public int getShootIndex() {
        if (shoot.equals("Lower"))
            return 0;
        if (shoot.equals("Higher"))
            return 1;
        if (shoot.equals("Both"))
            return 2;
        return 3;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Pit{" +
                "mass='" + mass + '\'' +
                ", comment='" + comment + '\'' +
                ", language='" + language + '\'' +
                ", wheels='" + wheels + '\'' +
                ", intake='" + intake + '\'' +
                ", carry='" + carry + '\'' +
                ", shoot='" + shoot + '\'' +
                ", hasAuto=" + hasAuto +
                ", canTrench=" + canTrench +
                ", canBumpers=" + canBumpers +
                ", endGame=" + endGame +
                ", cprc=" + cprc +
                ", cppc=" + cppc +
                '}';
    }
}
