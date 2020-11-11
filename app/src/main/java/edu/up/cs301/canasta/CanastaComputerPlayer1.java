package edu.up.cs301.canasta;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.R;

public class CanastaComputerPlayer1 extends GameComputerPlayer implements CanPlayer{

    private int score;
    private ArrayList<Card> hand = new ArrayList<>();
    private int playerNum;
    private int totalScore;
    private boolean discardState; //true = start of turn, pick up pile      false = discard on click
    private GameMainActivity myActivity;

    private ArrayList<Button> meldButtons = new ArrayList<>();

    private ArrayList<ArrayList<Card>> melds = new ArrayList<>();

    private ArrayList<Card> meldedAce = new ArrayList<>();
    private ArrayList<Card> meldedWild = new ArrayList<>();
    private ArrayList<Card> melded3 = new ArrayList<>();
    private ArrayList<Card> melded4 = new ArrayList<>();
    private ArrayList<Card> melded5 = new ArrayList<>();
    private ArrayList<Card> melded6 = new ArrayList<>();
    private ArrayList<Card> melded7 = new ArrayList<>();
    private ArrayList<Card> melded8 = new ArrayList<>();
    private ArrayList<Card> melded9 = new ArrayList<>();
    private ArrayList<Card> melded10 = new ArrayList<>();
    private ArrayList<Card> meldedJack = new ArrayList<>();
    private ArrayList<Card> meldedQueen = new ArrayList<>();
    private ArrayList<Card> meldedKing = new ArrayList<>();

    private ArrayList<Integer> playerMoves = new ArrayList<>();



    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public CanastaComputerPlayer1(int num, String name) {

        super(name);
        score = 0;
        hand = new ArrayList<>();
        playerNum = num;
        totalScore = 0;

        melds.add(0,null);
        melds.add(1,meldedAce);
        melds.add(2,meldedWild);
        melds.add(3,melded3);
        melds.add(4,melded4);
        melds.add(5,melded5);
        melds.add(6,melded6);
        melds.add(7,melded7);
        melds.add(8,melded8);
        melds.add(9,melded9);
        melds.add(10,melded10);
        melds.add(11,meldedJack);
        melds.add(12,meldedQueen);
        melds.add(13,meldedKing);
        meldButtons.add(0,null);


    }
    public CanastaComputerPlayer1(CanastaComputerPlayer1 orig) {
        super(orig.name);
        score = orig.score;

        melds.add(0,null);
        melds.add(1,meldedAce);
        melds.add(2,meldedWild);
        melds.add(3,melded3);
        melds.add(4,melded4);
        melds.add(5,melded5);
        melds.add(6,melded6);
        melds.add(7,melded7);
        melds.add(8,melded8);
        melds.add(9,melded9);
        melds.add(10,melded10);
        melds.add(11,meldedJack);
        melds.add(12,meldedQueen);
        melds.add(13,meldedKing);

        for (Card c: orig.hand) {
            this.hand.add(new Card(c));
        }
        for (Card c: orig.meldedAce) {
            this.meldedAce.add(new Card(c));
        }
        for (Card c: orig.meldedWild) {
            this.meldedWild.add(new Card(c));
        }
        for (Card c: orig.melded3) {
            this.melded3.add(new Card(c));
        }
        for (Card c: orig.melded4) {
            this.melded4.add(new Card(c));
        }
        for (Card c: orig.melded5) {
            this.melded5.add(new Card(c));
        }
        for (Card c: orig.melded6) {
            this.melded6.add(new Card(c));
        }
        for (Card c: orig.melded7) {
            this.melded7.add(new Card(c));
        }
        for (Card c: orig.melded8) {
            this.melded8.add(new Card(c));
        }
        for (Card c: orig.melded9) {
            this.melded9.add(new Card(c));
        }
        for (Card c: orig.melded10) {
            this.melded10.add(new Card(c));
        }
        for (Card c: orig.meldedJack) {
            this.meldedJack.add(new Card(c));
        }
        for (Card c: orig.meldedQueen) {
            this.meldedQueen.add(new Card(c));
        }
        for (Card c: orig.meldedKing) {
            this.meldedKing.add(new Card(c));
        }
        for (Integer v: orig.playerMoves) {
            this.playerMoves.add(v);
        }
        playerNum = orig.playerNum;
        totalScore = orig.totalScore;
    }

    @Override
    protected void receiveInfo(GameInfo info) {

        if (info instanceof CanastaGameState){
            CanastaGameState state=(CanastaGameState)info;

            if (state.player1==null){return;}

            if (state.player1==null){return;}
            if (playerNum==0){
                hand=state.player1.getHand();
                for (int i=1; i<melds.size(); i++){
                    melds=state.player1.getMelds();
                }
                totalScore=state.getPlayer1Score();

            }
            else if (playerNum==1){
                hand=state.player2.getHand();
                for (int i=1; i<melds.size(); i++){
                    melds=state.player2.getMelds();
                }
                totalScore=state.getPlayer2Score();

            }
            else{
                System.out.println("Error-player num has an unexpected value");
            }

            if (hand.size()<1){return;}

            game.sendAction(new CanastaDrawAction(this));
            int cardVal = hand.get(0).getValue();
            game.sendAction(new CanastaSelectCardAction(this, cardVal));
            game.sendAction(new CanastaDiscardAction(this));

//            for (int i = 1; i < meldButtons.size(); i++) {
//                if (melds.get(i).size() != 0) {
//                    meldButtons.get(i).setAlpha(1);
//                }
//                else {
//                    meldButtons.get(i).setAlpha(0);
//                }
//            }
        }
        else{
            System.out.println("Received other info message.");
        }
        return;

    }
    /*public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;

        activity.setContentView(R.layout.main_activity);
        meldButtons.add(1, (Button) activity.findViewById(R.id.PCmeldAs));
        meldButtons.add(2, (Button) activity.findViewById(R.id.PCmeld2));
        meldButtons.add(3, (Button) activity.findViewById(R.id.PCmeld3));
        meldButtons.add(4, (Button) activity.findViewById(R.id.PCmeld4));
        meldButtons.add(5, (Button) activity.findViewById(R.id.PCmeld5));
        meldButtons.add(6, (Button) activity.findViewById(R.id.PCmeld6));
        meldButtons.add(7, (Button) activity.findViewById(R.id.PCmeld7));
        meldButtons.add(8, (Button) activity.findViewById(R.id.PCmeld8));
        meldButtons.add(9, (Button) activity.findViewById(R.id.PCmeld9));
        meldButtons.add(10, (Button) activity.findViewById(R.id.PCmeld10));
        meldButtons.add(11, (Button) activity.findViewById(R.id.PCmeldJ));
        meldButtons.add(12, (Button) activity.findViewById(R.id.PCmeldQ));
        meldButtons.add(13, (Button) activity.findViewById(R.id.PCmeldK));
    }*/

    public void setScore(int s) {
        score = s;
    }

    public int getScore() {
        return score;
    }

    public void setPlayerNum(int n) { playerNum = n; }

    public int getPlayerNum() {
        return  playerNum;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getMeldedAce() {
        return meldedAce;
    }

    public void setMeldedAce(ArrayList<Card> meldedAce) {
        this.meldedAce = meldedAce;
    }

    public ArrayList<Card> getMeldedWild() {
        return meldedWild;
    }

    public void setMeldedWild(ArrayList<Card> meldedWild) {
        this.meldedWild = meldedWild;
    }

    public ArrayList<Card> getMelded3() {
        return melded3;
    }
    public void setMelded3(ArrayList<Card> melded3) {
        this.melded3 = melded3;
    }

    public ArrayList<Card> getMelded4() {
        return melded4;
    }

    public void setMelded4(ArrayList<Card> melded4) {
        this.melded4 = melded4;
    }

    public ArrayList<Card> getMelded5() {
        return melded5;
    }

    public void setMelded5(ArrayList<Card> melded5) {
        this.melded5 = melded5;
    }

    public ArrayList<Card> getMelded6() {
        return melded6;
    }

    public void setMelded6(ArrayList<Card> melded6) {
        this.melded6 = melded6;
    }

    public ArrayList<Card> getMelded7() {
        return melded7;
    }

    public void setMelded7(ArrayList<Card> melded7) {
        this.melded7 = melded7;
    }

    public ArrayList<Card> getMelded8() {
        return melded8;
    }

    public void setMelded8(ArrayList<Card> melded8) {
        this.melded8 = melded8;
    }

    public ArrayList<Card> getMelded9() {
        return melded9;
    }

    public void setMelded9(ArrayList<Card> melded9) {
        this.melded9 = melded9;
    }

    public ArrayList<Card> getMelded10() {
        return melded10;
    }

    public void setMelded10(ArrayList<Card> melded10) {
        this.melded10 = melded10;
    }

    public ArrayList<Card> getMeldedJack() {
        return meldedJack;
    }

    public void setMeldedJack(ArrayList<Card> meldedJack) {
        this.meldedJack = meldedJack;
    }

    public ArrayList<Card> getMeldedQueen() {
        return meldedQueen;
    }

    public void setMeldedQueen(ArrayList<Card> meldedQueen) {
        this.meldedQueen = meldedQueen;
    }

    public ArrayList<Card> getMeldedKing() {
        return meldedKing;
    }

    public void setMeldedKing(ArrayList<Card> meldedKing) {
        this.meldedKing = meldedKing;
    }

    public ArrayList<Integer> getPlayerMoves() {
        return playerMoves;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public ArrayList<ArrayList<Card>> getMelds() {
        return melds;
    }

}
