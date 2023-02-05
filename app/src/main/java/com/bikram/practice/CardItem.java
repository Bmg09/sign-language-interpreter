package com.bikram.practice;

public class CardItem {
    String letter;
    int vidResId;

    public String getLetter() {
        return letter;
    }

    public int getVidResId() {
        return vidResId;
    }


    public CardItem(String letter, int vidResId) {
        this.letter = letter;
        this.vidResId = vidResId;
    }
}
