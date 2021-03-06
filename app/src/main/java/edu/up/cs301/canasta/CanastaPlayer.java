/**
 * @author
 *      Ryan Hunter-Bliss
 *      Sarah Ebner
 *      Lute Lillo Portero
 */

package edu.up.cs301.canasta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import edu.up.cs301.game.GameFramework.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.R;

public class CanastaPlayer extends GameHumanPlayer implements View.OnClickListener {

    private GameMainActivity myActivity;

    private Button undoButton = null;
    private Button deckButton = null;
    private Button discardButton = null;
    private Button playerScore = null;
    private Button aiScore = null;
    private Button endGame = null;
    private Button newGame = null;

    private ArrayList<Button> handButtons = new ArrayList<>();
    private ArrayList<Button> meldButtons = new ArrayList<>();
    private ArrayList<Button> cardHandCount = new ArrayList<>();
    private ArrayList<Button> cardMeldCount = new ArrayList<>();
    private int[] images;

    private ArrayList<Button> aiMeldButtons = new ArrayList<>();
    private ArrayList<Button> aiCardMeldCount = new ArrayList<>();
    private boolean showTotalScore=false;

    private CanastaGameState stateCopy=null;



    /**
     * Constructor
     * @param num (The player number)
     */
    public CanastaPlayer(int num, String name) {
        super(name);
        playerNum = num;
    }

    /**
     * Copy constructor
     * @param orig (The original player)
     */
    public CanastaPlayer(CanastaPlayer orig) {
        super(orig.name);

        playerNum = orig.playerNum;
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
            stateCopy=state;

            updateText(state);

            if (images==null){
                return;
            }
            if (state.discardPile.size()>0) {
                discardButton.setAlpha(1);
                int val = state.discardPile.get(state.discardPile.size() - 1).getValue();
                if (val>0 && val<images.length) {
                    discardButton.setBackgroundResource(images[val]);
                }
            }
            else{
                discardButton.setAlpha(0);
            }

        }
        else if (info instanceof CanastaIllegalMoveInfo){
            /*
            External Citation
            Date: 11/30/2020
            Problem: We wanted some kind of pop-up message to show the user that their action did
            not succeed
            Resource 1: https://developer.android.com/reference/com/google/android/material/snackbar/Snackbar
            Resource 2: https://abhiandroid.com/materialdesign/snackbar
            Solution: I used the Snackbar.make() and snackbar.show() method shown in both resources
             */
            CanastaIllegalMoveInfo moveInfo=(CanastaIllegalMoveInfo)info;
            if ((moveInfo).getAct().getPlayer()==this) {
                String msg="Error";

                if (moveInfo.getAct() instanceof CanastaMeldAction){
                    msg=myActivity.getString(R.string.meldError);
                }
                else if (moveInfo.getAct() instanceof CanastaDrawAction){
                    msg=myActivity.getString(R.string.drawError);
                }
                else if (moveInfo.getAct() instanceof CanastaDiscardAction){
                    if (moveInfo.getNum()==1){
                        msg=myActivity.getString(R.string.discardError1);
                    }
                    else if (moveInfo.getNum()==2){
                        msg=myActivity.getString(R.string.discardError2);
                    }
                }

                Snackbar s = Snackbar.make(undoButton, msg, Snackbar.LENGTH_LONG);
                s.show();
            }
        }
        else if (info instanceof NotYourTurnInfo){
            String msg=myActivity.getString(R.string.notYourTurnError);
            Snackbar s = Snackbar.make(undoButton, msg, Snackbar.LENGTH_LONG);
            s.show();
        }
    }

    /**
     * Updates text in the UI when the score changes,
     * your hand or meld changes, or the counts change
     * @param state (The game state)
     */
    public void updateText(CanastaGameState state) {


        int humanScore = state.getResources(playerNum).getScore();
        int aiScoreVal = state.getResources((playerNum+1)%2).getScore();

        if (showTotalScore){
            humanScore = state.getResources(playerNum).getTotalScore();
            aiScoreVal = state.getResources((playerNum+1)%2).getTotalScore();
        }

        this.playerScore.setText("" + humanScore);
        this.aiScore.setText("" + aiScoreVal);

        //sets visibility of melds
        for (int i = 1; i < meldButtons.size(); i++) {
            if (state.getResources(playerNum).getMelds().get(i).size() != 0) {
                meldButtons.get(i).setAlpha(1);
            }
            else {
                meldButtons.get(i).setAlpha(0);
            }
        }

        //sets visibility of AI melds
        for (int i = 1; i < aiMeldButtons.size(); i++) {
            if (state.getResources((playerNum+1)%2).getMelds().get(i).size() != 0) {
                aiMeldButtons.get(i).setAlpha(1);
            }
            else {
                aiMeldButtons.get(i).setAlpha(0);
            }
        }

        //set counter of cards in your hand
        for (int i = 0; i < cardHandCount.size(); i++) {
            cardHandCount.get(i).setText("" + countInHand(state.getResources(playerNum).getHand(),i));
        }

        //set counter for number of cards in meld
        for (int i = 1; i < cardMeldCount.size(); i++) {
            if (state.getResources(playerNum).getMelds().get(i).size() != 0) {
                cardMeldCount.get(i).setText("" + state.getResources(playerNum).getMelds().get(i).size());
            }
            else {
                cardMeldCount.get(i).setText("0");
            }
        }

        //set counter for number of cards in AI meld
        for (int i = 1; i < aiCardMeldCount.size(); i++) {
            if (state.getResources((playerNum+1)%2).getMelds().get(i).size() != 0) {
                aiCardMeldCount.get(i).setText("" + state.getResources((playerNum+1)%2).getMelds().get(i).size());
            }
            else {
                aiCardMeldCount.get(i).setText("0");
            }
        }

        //set cards to visible once they're in your hand
        for (int i = 0; i < handButtons.size(); i++) {
            boolean exists = false;
            for (int j = 0; j < state.getResources(playerNum).getHand().size(); j++) {
                if (state.getResources(playerNum).getHand().get(j).getValue() == i) {
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
        handButtons.add(0,(Button)activity.findViewById(R.id.handJoker));
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
        cardHandCount.add(0, (Button)activity.findViewById(R.id.nPlayerJoker));
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




        //connects ai meld card counters
        aiCardMeldCount.add(0, null);
        aiCardMeldCount.add(1, (Button)activity.findViewById((R.id.nPCCardsAS)));
        aiCardMeldCount.add(2, (Button)activity.findViewById((R.id.nPCCards2)));
        aiCardMeldCount.add(3, (Button)activity.findViewById((R.id.nPCCards3)));
        aiCardMeldCount.add(4, (Button)activity.findViewById((R.id.nPCCards4)));
        aiCardMeldCount.add(5, (Button)activity.findViewById((R.id.nPCCards5)));
        aiCardMeldCount.add(6, (Button)activity.findViewById((R.id.nPCCards6)));
        aiCardMeldCount.add(7, (Button)activity.findViewById((R.id.nPCCards7)));
        aiCardMeldCount.add(8, (Button)activity.findViewById((R.id.nPCCards8)));
        aiCardMeldCount.add(9, (Button)activity.findViewById((R.id.nPCCards9)));
        aiCardMeldCount.add(10, (Button)activity.findViewById((R.id.nPCCards10)));
        aiCardMeldCount.add(11, (Button)activity.findViewById((R.id.nPCCardsJ)));
        aiCardMeldCount.add(12, (Button)activity.findViewById((R.id.nPCCardsQ)));
        aiCardMeldCount.add(13, (Button)activity.findViewById((R.id.nPCCardsK)));

        //connects ai meld buttons
        aiMeldButtons.add(0,null);
        aiMeldButtons.add(1,(Button)activity.findViewById(R.id.PCmeldAs));
        aiMeldButtons.add(2,(Button)activity.findViewById(R.id.PCmeld2));
        aiMeldButtons.add(3,(Button)activity.findViewById(R.id.PCmeld3));
        aiMeldButtons.add(4,(Button)activity.findViewById(R.id.PCmeld4));
        aiMeldButtons.add(5,(Button)activity.findViewById(R.id.PCmeld5));
        aiMeldButtons.add(6,(Button)activity.findViewById(R.id.PCmeld6));
        aiMeldButtons.add(7,(Button)activity.findViewById(R.id.PCmeld7));
        aiMeldButtons.add(8,(Button)activity.findViewById(R.id.PCmeld8));
        aiMeldButtons.add(9,(Button)activity.findViewById(R.id.PCmeld9));
        aiMeldButtons.add(10,(Button)activity.findViewById(R.id.PCmeld10));
        aiMeldButtons.add(11,(Button)activity.findViewById(R.id.PCmeldJ));
        aiMeldButtons.add(12,(Button)activity.findViewById(R.id.PCmeldQ));
        aiMeldButtons.add(13,(Button)activity.findViewById(R.id.PCmeldK));


        for (int i = 0; i < handButtons.size(); i++) {
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
        this.playerScore.setOnClickListener(this);
        this.aiScore.setOnClickListener(this);

        discardButton.setOnClickListener(this);
        deckButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);

        int[] i={R.drawable.jokercard,R.drawable.club_as,R.drawable.club2,R.drawable.club3,R.drawable.club4,
                R.drawable.club5,R.drawable.club6,R.drawable.club7,R.drawable.club8,R.drawable.club9,
                R.drawable.club10,R.drawable.club_jack,R.drawable.club_queen,R.drawable.club_king};
        images=i;


        this.endGame = (Button)activity.findViewById(R.id.offButton);
        this.newGame = (Button)activity.findViewById(R.id.restartgame);

        endGame.setOnClickListener(this);
        newGame.setOnClickListener(this);
    }


    /**
     * Tells what action should be done when a button is pressed
     * @param view
     *
     * External Citation
     * Date: 30 November 2020
     * Problem: did not know how to create a popup window
     *
     * Resource: https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
     * Solution: followed the example to create a similar popup
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
        //flip between the total score and score for a particular hand by clicking the score
        if (view==aiScore|| view==playerScore){
            showTotalScore=!showTotalScore;
            int humanScore = stateCopy.getResources(playerNum).getScore();
            int aiScoreVal = stateCopy.getResources((playerNum+1)%2).getScore();

            if (showTotalScore){
                humanScore = stateCopy.getResources(playerNum).getTotalScore();
                aiScoreVal = stateCopy.getResources((playerNum+1)%2).getTotalScore();
            }
            this.playerScore.setText("" + humanScore);
            this.aiScore.setText("" + aiScoreVal);
        }

        for (int i = 1; i < meldButtons.size(); i++) {
            if (view == meldButtons.get(i)) {
                meld.setMeldDestination(i);
                game.sendAction(meld);
                System.out.println("Meld button clicked");
            }
        }

        for (int i = 0; i < handButtons.size(); i++) {
            if (view == handButtons.get(i)) {
                game.sendAction(new CanastaSelectCardAction(this,i));
                System.out.println("Hand button clicked");
            }
        }


        if (view == newGame) {
            final AlertDialog alertDialog = new AlertDialog.Builder(myActivity).create();
            alertDialog.setTitle("Point Values");
            alertDialog.setMessage(myActivity.getString(R.string.pointValues));

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.show();

            final AlertDialog alertDialog2 = new AlertDialog.Builder(myActivity).create();
            alertDialog2.setTitle("How to Play");
            alertDialog2.setMessage(myActivity.getString(R.string.howTo));


            alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "Next",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog2.show();


        }
        if (view == endGame) {
            System.exit(1);
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



    public void setPlayerNum(int n) { playerNum = n; }

    public int getPlayerNum() {
        return  playerNum;
    }






}