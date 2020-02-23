package com.example.primo2020v1.utils;

public class Statistics {
    private int numberOfMatches, totalCycles, totalScore, totalShots, totalPCmissed, totalPClower, totalPCouter, totalPCinner,
            dbCPRC, dbCPPC, dbBalanced, dbClimb, dbTimesCrashed, dbTimesDefended, dbYellowCard, dbRedCard;

    //    int totalPCinner, int dbBalancedint, int totalPCmissed,  int dbRedCard, int totalCycles,
//    int dbYellowCardint, int totalScore, int numMatches, int totalShots,
//    int dbCPRC, int dbCPPC, int dbTimesDefended, int dbClimb, int dbTimesCrashed, int totalPCouter, int totalPClower
    public Statistics(int numMatches, int totalCycles, int totalScore, int totalShots, int totalPCmissed, int totalPClower, int totalPCouter, int totalPCinner,
                      int dbCPRC, int dbCPPC, int dbBalanced, int dbClimb,
                      int dbTimesCrashed, int dbTimesDefended, int dbYellowCard, int dbRedCard) {
        this.numberOfMatches = numMatches;
        this.totalCycles = totalCycles;
        this.totalScore = totalScore;
        this.totalShots = totalShots;
        this.totalPCmissed = totalPCmissed;
        this.totalPClower = totalPClower;
        this.totalPCouter = totalPCouter;
        this.totalPCinner = totalPCinner;
        this.dbCPRC = dbCPRC;
        this.dbCPPC = dbCPPC;
        this.dbBalanced = dbBalanced;
        this.dbClimb = dbClimb;
        this.dbTimesCrashed = dbTimesCrashed;
        this.dbTimesDefended = dbTimesDefended;
        this.dbYellowCard = dbYellowCard;
        this.dbRedCard = dbRedCard;
    }

    public Statistics() {
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public int getTotalCycles() {
        return totalCycles;
    }

    public void setTotalCycles(int totalCycles) {
        this.totalCycles = totalCycles;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalShots() {
        return totalShots;
    }

    public void setTotalShots(int totalShots) {
        this.totalShots = totalShots;
    }

    public int getTotalPCmissed() {
        return totalPCmissed;
    }

    public void setTotalPCmissed(int totalPCmissed) {
        this.totalPCmissed = totalPCmissed;
    }

    public int getTotalPClower() {
        return totalPClower;
    }

    public void setTotalPClower(int totalPClower) {
        this.totalPClower = totalPClower;
    }

    public int getTotalPCouter() {
        return totalPCouter;
    }

    public void setTotalPCouter(int totalPCouter) {
        this.totalPCouter = totalPCouter;
    }

    public int getTotalPCinner() {
        return totalPCinner;
    }

    public void setTotalPCinner(int totalPCinner) {
        this.totalPCinner = totalPCinner;
    }

    public int getDbCPRC() {
        return dbCPRC;
    }

    public void setDbCPRC(int dbCPRC) {
        this.dbCPRC = dbCPRC;
    }

    public int getDbCPPC() {
        return dbCPPC;
    }

    public void setDbCPPC(int dbCPPC) {
        this.dbCPPC = dbCPPC;
    }

    public int getDbBalanced() {
        return dbBalanced;
    }

    public void setDbBalanced(int dbBalanced) {
        this.dbBalanced = dbBalanced;
    }

    public int getDbClimb() {
        return dbClimb;
    }

    public void setDbClimb(int dbClimb) {
        this.dbClimb = dbClimb;
    }

    public int getDbTimesCrashed() {
        return dbTimesCrashed;
    }

    public void setDbTimesCrashed(int dbTimesCrashed) {
        this.dbTimesCrashed = dbTimesCrashed;
    }

    public int getDbTimesDefended() {
        return dbTimesDefended;
    }

    public void setDbTimesDefended(int dbTimesDefended) {
        this.dbTimesDefended = dbTimesDefended;
    }

    public int getDbYellowCard() {
        return dbYellowCard;
    }

    public void setDbYellowCard(int dbYellowCard) {
        this.dbYellowCard = dbYellowCard;
    }

    public int getDbRedCard() {
        return dbRedCard;
    }

    public void setDbRedCard(int dbRedCard) {
        this.dbRedCard = dbRedCard;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "numberOfMatches=" + numberOfMatches +
                ", totalCycles=" + totalCycles +
                ", totalScore=" + totalScore +
                ", totalShots=" + totalShots +
                ", totalPCmissed=" + totalPCmissed +
                ", totalPClower=" + totalPClower +
                ", totalPCouter=" + totalPCouter +
                ", totalPCinner=" + totalPCinner +
                ", dbCPRC=" + dbCPRC +
                ", dbCPPC=" + dbCPPC +
                ", dbBalanced=" + dbBalanced +
                ", dbClimb=" + dbClimb +
                ", dbTimesCrashed=" + dbTimesCrashed +
                ", dbTimesDefended=" + dbTimesDefended +
                ", dbYellowCard=" + dbYellowCard +
                ", dbRedCard=" + dbRedCard +
                '}';
    }

    public void addAll(Statistics other) {
        this.numberOfMatches += other.numberOfMatches;
        this.totalCycles += other.totalCycles;
        this.totalScore += other.totalScore;
        this.totalShots += other.totalShots;
        this.totalPCmissed += other.totalPCmissed;
        this.totalPClower += other.totalPClower;
        this.totalPCouter += other.totalPCouter;
        this.totalPCinner += other.totalPCinner;
        this.dbCPRC += other.dbCPRC;
        this.dbCPPC += other.dbCPPC;
        this.dbBalanced += other.dbBalanced;
        this.dbClimb += other.dbClimb;
        this.dbTimesCrashed += other.dbTimesCrashed;
        this.dbTimesDefended += other.dbTimesDefended;
        this.dbYellowCard += other.dbYellowCard;
        this.dbRedCard += other.dbRedCard;
    }

    public Statistics copy() {
        return new Statistics(this.numberOfMatches, this.totalCycles, this.totalScore, this.totalShots, this.totalPCmissed, this.totalPClower, this.totalPCouter, this.totalPCinner,
        this.dbCPRC, this.dbCPPC, this.dbBalanced, this.dbClimb,
        this.dbTimesCrashed, this.dbTimesDefended, this.dbYellowCard, this.dbRedCard);
    }

    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public double getCategory(String category) {
        double totalRating;

        if (category.equals("totalPClower"))
            totalRating = this.totalPClower;
        else if(category.equals("totalPCouter"))
            totalRating = this.totalPCouter;
        else if (category.equals("totalPCinner"))
            totalRating = this.totalPCinner;
        else if (category.equals("dbCPRC"))
            totalRating = this.dbCPRC;
        else if (category.equals("dbCPPC"))
            totalRating = this.dbCPPC;
        else if (category.equals("dbClimb"))
            totalRating = this.dbClimb;
        else
            totalRating = this.dbBalanced;

        return totalRating;
    }
}
