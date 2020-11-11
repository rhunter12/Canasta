package edu.up.cs301.canasta;

import java.util.ArrayList;

public interface CanPlayer {



    public String toString();

    public void setScore(int s);

    public int getScore();

    public void setPlayerNum(int n);

    public int getPlayerNum();

    public ArrayList<Card> getHand();

    public ArrayList<Card> getMeldedAce();

    public void setMeldedAce(ArrayList<Card> meldedAce);

    public ArrayList<Card> getMeldedWild();

    public void setMeldedWild(ArrayList<Card> meldedWild);

    public ArrayList<Card> getMelded3();

    public void setMelded3(ArrayList<Card> melded3);

    public ArrayList<Card> getMelded4();

    public void setMelded4(ArrayList<Card> melded4);

    public ArrayList<Card> getMelded5();

    public void setMelded5(ArrayList<Card> melded5);

    public ArrayList<Card> getMelded6();

    public void setMelded6(ArrayList<Card> melded6);

    public ArrayList<Card> getMelded7();

    public void setMelded7(ArrayList<Card> melded7);

    public ArrayList<Card> getMelded8();

    public void setMelded8(ArrayList<Card> melded8);

    public ArrayList<Card> getMelded9();

    public void setMelded9(ArrayList<Card> melded9);

    public ArrayList<Card> getMelded10();

    public void setMelded10(ArrayList<Card> melded10);

    public ArrayList<Card> getMeldedJack();

    public void setMeldedJack(ArrayList<Card> meldedJack);

    public ArrayList<Card> getMeldedQueen();

    public void setMeldedQueen(ArrayList<Card> meldedQueen);

    public ArrayList<Card> getMeldedKing();

    public void setMeldedKing(ArrayList<Card> meldedKing);

    public ArrayList<Integer> getPlayerMoves() ;




    public int getTotalScore();

    public ArrayList<ArrayList<Card>> getMelds();
}
