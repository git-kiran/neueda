package com.neueda.test.neueda.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class ATM {
    private static int atm_Balance = 1500;
    private static int fiftyCount;
    private static int twentyCount;
    private static int tenCount;
    private static int fiveCount;

    private final static int[] noteValues = {50, 20, 10, 5};
    private static int[] noteCounts = {10, 30, 30, 20};

    public static int[] getNoteValues() {
        return noteValues;
    }

    public static int[] getNoteCounts() {
        return noteCounts;
    }

    public static void setNoteCounts(int[] noteCounts) {
        ATM.noteCounts = noteCounts;
    }

    public static int getAtm_Balance() {
        return atm_Balance;
    }

    public static void setAtm_Balance(int atm_Balance) {
        ATM.atm_Balance = atm_Balance;
    }

    public static int getFiftyCount() {
        return fiftyCount;
    }

    public static void setFiftyCount(int fiftyCount) {
        ATM.fiftyCount = fiftyCount;
    }

    public static int getTwentyCount() {
        return twentyCount;
    }

    public static void setTwentyCount(int twentyCount) {
        ATM.twentyCount = twentyCount;
    }

    public static int getTenCount() {
        return tenCount;
    }

    public static void setTenCount(int tenCount) {
        ATM.tenCount = tenCount;
    }

    public static int getFiveCount() {
        return fiveCount;
    }

    public static void setFiveCount(int fiveCount) {
        ATM.fiveCount = fiveCount;
    }
}
