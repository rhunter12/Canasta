/**
 * @author
 *      Ryan Hunter-Bliss
 *      Sarah Ebner
 *      Lute Lillo Portero
 */

package edu.up.cs301.canasta;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.R;

public class CanastaPlayer extends GameHumanPlayer implements View.OnClickListener {
    private int score;
    private ArrayList<Card> hand = new ArrayList<>();
    private int playerNum;
    private int totalScore;
    private boolean discardState; //true = start of turn, pick up pile      false = discard on click

    private GameMainActivity myActivity;

    private Button undoButton = null;
    private Button deckButton = null;
    private Button discardButton = null;
    private Button playerScore = null;
    private Button aiScore = null;

    private ArrayList<Button> handButtons = new ArrayList<>();
    private ArrayList<Button> meldButtons = new ArrayList<>();
    private ArrayList<Button> cardHandCount = new ArrayList<>();
    private ArrayList<Button> cardMeldCount = new ArrayList<>();

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
     * Constructor
     * @param num (The player number)
     */
    public CanastaPlayer(int num, String name) {
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
    }

    /**
     * Copy constructor
     * @param orig (The original player)
     */
    public CanastaPlayer(CanastaPlayer orig) {
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

    /**
     * Converts a player info into a string
     * @return (The string to be printed)
     */
    @Override
    public String toString() {
        String info = "Player information: " + score + ", " + playerNum + "\n";
        info = info + "Hand: \n";
        for (Card c: hand) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Ace: \n";
        for (Card c: meldedAce) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Wild: \n";
        for (Card c: meldedWild) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 3: \n";
        for (Card c: melded3) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 4: \n";
        for (Card c: melded4) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 5: \n";
        for (Card c: melded5) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 6: \n";
        for (Card c: melded6) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 7: \n";
        for (Card c: melded7) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 8: \n";
        for (Card c: melded8) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 9: \n";
        for (Card c: melded9) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 10: \n";
        for (Card c: melded10) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Jack: \n";
        for (Card c: meldedJack) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Queen: \n";
        for (Card c: meldedQueen) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded King: \n";
        for (Card c: meldedKing) {
            info = info + c.toString();
            info = info + ", ";
        }

        return info;
    }

    /**
     * Copy constructor that can hide certain hand information
     * @param orig (The original player)
     * @param copyHand (Whether to copy the hand or not)
     */
    public CanastaPlayer(CanastaPlayer orig, boolean copyHand) {
        super(orig.name);
        score = orig.score;
        if (copyHand) {
            for (Card c: orig.hand) {
                this.hand.add(new Card(c));
            }
        }
        else {
            for (Card c: orig.hand) {
                if (c.getKnownCard()) {
                    this.hand.add(new Card(c));
                }
            }
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


    /**
     * Connects activity to layout
     * @return
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * Performs action when it gets info back from GameInfo
     * @param info (The game info sender)
     */
    @Override
    public void receiveInfo(GameInfo info) {
        CanastaGameState state;


        if (info instanceof CanastaGameState) {
            state = (CanastaGameState) info;
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

            updateText(state);

            //this is for the dumb ai
//            if (playerNum==1 && state.getTurnStage()==0) {
//                game.sendAction(new CanastaDrawAction(this));
//            }
//            else if (playerNum==1 && state.getTurnStage()==1){
//                if (countInHand(hand,state.getSelectedCard())==0) {
//                    game.sendAction(new CanastaSelectCardAction(this, hand.get(0).getValue()));
//                }
//                else {
//                    game.sendAction(new CanastaDiscardAction(this));
//                }
//            }

        }
    }

    /**
     * Updates text in the UI when the score changes,
     * your hand or meld changes, or the counts change
     * @param state (The game state)
     */
    public void updateText(CanastaGameState state) {
        int humanScore = 0;
        int aiScoreVal = 0;

        if (playerNum == 0) {
            humanScore = state.player1.getTotalScore();
            aiScoreVal = state.player2.getTotalScore();
        }
        else if (playerNum == 1) {
            humanScore = state.player2.getTotalScore();
            aiScoreVal = state.player1.getTotalScore();
        }

        this.playerScore.setText("" + humanScore);
        this.aiScore.setText("" + aiScoreVal);

        //sets visibility of melds
        for (int i = 1; i < meldButtons.size(); i++) {
            if (melds.get(i).size() != 0) {
                meldButtons.get(i).setAlpha(1);
            }
            else {
                meldButtons.get(i).setAlpha(0);
            }
        }

        //set counter of cards in your hand
        for (int i = 1; i < cardHandCount.size(); i++) {
            cardHandCount.get(i).setText("" + countInHand(hand,i));
        }

        //set counter for number of cards in meld
        for (int i = 1; i < cardMeldCount.size(); i++) {
            if (melds.get(i).size() != 0) {
                cardMeldCount.get(i).setText("" + melds.get(i).size());
            }
            else {
                cardMeldCount.get(i).setText("0");
            }
        }

        //set cards to visible once they're in your hand
        for (int i = 1; i < handButtons.size(); i++) {
            boolean exists = false;
            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(j).getValue() == i) {
                    exists = true;
                }
            }
            if (exists) {
                handButtons.get(i).setVisibility(View.VISIBLE);
            }
            else {
                handButtons.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Connects buttons to UI instances and sets listeners
     * @param activity (The game's main activity)
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;

        activity.setContentView(R.layout.main_activity);

        //connect hand card buttons
        handButtons.add(0,null);
        handButtons.add(1,(Button)activity.findViewById(R.id.handAS));
        handButtons.add(2,(Button)activity.findViewById(R.id.hand2));
        handButtons.add(3,(Button)activity.findViewById(R.id.hand3));
        handButtons.add(4,(Button)activity.findViewById(R.id.hand4));
        handButtons.add(5,(Button)activity.findViewById(R.id.hand5));
        handButtons.add(6,(Button)activity.findViewById(R.id.hand6));
        handButtons.add(7,(Button)activity.findViewById(R.id.hand7));
        handButtons.add(8,(Button)activity.findViewById(R.id.hand8));
        handButtons.add(9,(Button)activity.findViewById(R.id.hand9));
        handButtons.add(10,(Button)activity.findViewById(R.id.hand10));
        handButtons.add(11,(Button)activity.findViewById(R.id.handJ));
        handButtons.add(12,(Button)activity.findViewById(R.id.handQ));
        handButtons.add(13,(Button)activity.findViewById(R.id.handK));

        //connects hand card counters
        cardHandCount.add(0, null);
        cardHandCount.add(1, (Button)activity.findViewById((R.id.nPlayerAS)));
        cardHandCount.add(2, (Button)activity.findViewById((R.id.nPlayer2)));
        cardHandCount.add(3, (Button)activity.findViewById((R.id.nPlayer3)));
        cardHandCount.add(4, (Button)activity.findViewById((R.id.nPlayer4)));
        cardHandCount.add(5, (Button)activity.findViewById((R.id.nPlayer5)));
        cardHandCount.add(6, (Button)activity.findViewById((R.id.nPlayer6)));
        cardHandCount.add(7, (Button)activity.findViewById((R.id.nPlayer7)));
        cardHandCount.add(8, (Button)activity.findViewById((R.id.nPlayer8)));
        cardHandCount.add(9, (Button)activity.findViewById((R.id.nPlayer9)));
        cardHandCount.add(10, (Button)activity.findViewById((R.id.nPlayer10)));
        cardHandCount.add(11, (Button)activity.findViewById((R.id.nPlayerJ)));
        cardHandCount.add(12, (Button)activity.findViewById((R.id.nPlayerQ)));
        cardHandCount.add(13, (Button)activity.findViewById((R.id.nPlayerK)));

        //connects meld card counters
        cardMeldCount.add(0, null);
        cardMeldCount.add(1, (Button)activity.findViewById((R.id.nCardsAS)));
        cardMeldCount.add(2, (Button)activity.findViewById((R.id.nCards2)));
        cardMeldCount.add(3, (Button)activity.findViewById((R.id.nCards3)));
        cardMeldCount.add(4, (Button)activity.findViewById((R.id.nCards4)));
        cardMeldCount.add(5, (Button)activity.findViewById((R.id.nCards5)));
        cardMeldCount.add(6, (Button)activity.findViewById((R.id.nCards6)));
        cardMeldCount.add(7, (Button)activity.findViewById((R.id.nCards7)));
        cardMeldCount.add(8, (Button)activity.findViewById((R.id.nCards8)));
        cardMeldCount.add(9, (Button)activity.findViewById((R.id.nCards9)));
        cardMeldCount.add(10, (Button)activity.findViewById((R.id.nCards10)));
        cardMeldCount.add(11, (Button)activity.findViewById((R.id.nCardsJ)));
        cardMeldCount.add(12, (Button)activity.findViewById((R.id.nCardsQ)));
        cardMeldCount.add(13, (Button)activity.findViewById((R.id.nCardsK)));

        //connects meld buttons
        meldButtons.add(0,null);
        meldButtons.add(1,(Button)activity.findViewById(R.id.meldAs));
        meldButtons.add(2,(Button)activity.findViewById(R.id.meld2));
        meldButtons.add(3,(Button)activity.findViewById(R.id.meld3));
        meldButtons.add(4,(Button)activity.findViewById(R.id.meld4));
        meldButtons.add(5,(Button)activity.findViewById(R.id.meld5));
        meldButtons.add(6,(Button)activity.findViewById(R.id.meld6));
        meldButtons.add(7,(Button)activity.findViewById(R.id.meld7));
        meldButtons.add(8,(Button)activity.findViewById(R.id.meld8));
        meldButtons.add(9,(Button)activity.findViewById(R.id.meld9));
        meldButtons.add(10,(Button)activity.findViewById(R.id.meld10));
        meldButtons.add(11,(Button)activity.findViewById(R.id.meldJ));
        meldButtons.add(12,(Button)activity.findViewById(R.id.meldQ));
        meldButtons.add(13,(Button)activity.findViewById(R.id.meldK));

        for (int i = 1; i < handButtons.size(); i++) {
            handButtons.get(i).setOnClickListener(this);
        }

        for (int i = 1; i < meldButtons.size(); i++) {
            meldButtons.get(i).setOnClickListener(this);
        }

        this.discardButton = (Button)activity.findViewById(R.id.discardPile);
        this.deckButton = (Button)activity.findViewById(R.id.deck);
        this.undoButton = (Button)activity.findViewById(R.id.undoButton);

        this.playerScore = (Button)activity.findViewById(R.id.PlayerScore);
        this.aiScore = (Button)activity.findViewById(R.id.aiScore);

        discardButton.setOnClickListener(this);
        deckButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);
    }


    /**
     * Tells what action should be done when a button is pressed
     * @param view
     */
    @Override
    public void onClick(View view) {
        CanastaDiscardAction discard = new CanastaDiscardAction(this);
        CanastaDrawAction draw = new CanastaDrawAction(this);
        CanastaUndoAction undo = new CanastaUndoAction(this);
        CanastaMeldAction meld = new CanastaMeldAction(this);

        if (view == discardButton) {
            game.sendAction(discard);
            System.out.println("Discard button clicked");
        }
        if (view == deckButton) {
            game.sendAction(draw);
            System.out.println("Deck button clicked");
        }
        if (view == undoButton) {
            game.sendAction(undo);
            System.out.println("Undo button clicked");
        }

        for (int i = 1; i < meldButtons.size(); i++) {
            if (view == meldButtons.get(i)) {
                game.sendAction(meld);
                System.out.println("Meld button clicked");
            }
        }

        for (int i = 1; i < handButtons.size(); i++) {
            if (view == handButtons.get(i)) {
                game.sendAction(new CanastaSelectCardAction(this,i));
                System.out.println("Hand button clicked");
            }
        }

    }


    /**
     * Tells the number of instances of
     * a specific card in the player's hand
     * @param hand (The hand to search through)
     * @param n (The value we're searching for)
     * @return (The count of cards)
     */
    public int countInHand(ArrayList<Card> hand, int n) {
        int count = 0;

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == n) {
                count++;
            }
        }
        return count;
    }


    /*
    Getters and setters
     */

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
    public void addTotalScore(int s){totalScore=totalScore+s;}




}
