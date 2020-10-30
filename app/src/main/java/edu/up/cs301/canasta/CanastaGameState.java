/**
 * @author
 *      Ryan Hunter-Bliss
 *      Sarah Ebner
 *      Lute Lillo Portero
 */

package edu.up.cs301.canasta;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;


public class CanastaGameState extends GameState implements Button.OnClickListener {

    // instance variables
    ArrayList<Card> deck = new ArrayList<>(); //deck
    ArrayList<Card> discardPile = new ArrayList<>(); //discard pile
    private int player1Score; //player 1 is the human
    private int player2Score; //player 2 is the AI
    CanastaPlayer player1; //player 1
    CanastaPlayer player2; //player 2
    private int playerTurnID; //player turn ID
    private int selectedCard = -1; //selected card

    TextView outText;

    /**
     * Constructor
     */
    public CanastaGameState() {
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        player1Score = 0;
        player2Score = 0;
        player1 = null;
        player2 = null;
        playerTurnID = 0;
        start();
    }

    /**
     * Copy constructor
     * @param orig (Original game state)
     */
    public CanastaGameState(CanastaGameState orig) {
        for (Card c: orig.deck) {
            this.deck.add(new Card(c));
        }
        for (Card c: orig.discardPile) {
            this.discardPile.add(new Card(c));
        }
        player1Score = orig.player1Score;
        player2Score = orig.player2Score;
        player1 = new CanastaPlayer(orig.player1);
        player2 = new CanastaPlayer(orig.player2);
        playerTurnID = orig.playerTurnID;
        outText = orig.outText;
    }

    /**
     * Builds deck and shuffles
     */
    public void buildDeck() {
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new Card(j,'H'));
                deck.add(new Card(j,'S'));
                deck.add(new Card(j,'D'));
                deck.add(new Card(j,'C'));
            }
            deck.add(new Card(0,'W'));
            deck.add(new Card(0,'W'));
        }
        Collections.shuffle(deck);
    }

    /**
     * Deals card to players, adds one to discard pile
     * @return (Returns whether the action was successful or not)
     */
    public boolean deal() {
        for (int i = 0; i < 15; i++) {
            player1.getHand().add(deck.remove(0));
            player2.getHand().add(deck.remove(0));
        }
        while (deck.get(0).getValue() == 3 && (deck.get(0).getSuit() == 'H' || deck.get(0).getSuit() == 'D')) {
            Collections.shuffle(deck);
        }
        discardPile.add(deck.remove(0));
        return true;
    }
















    /**
     * Init players, call build deck
     * @return (Returns whether the action was successful or not)
     */
    public boolean start() {
        player1 = new CanastaPlayer(1);
        player2 = new CanastaPlayer(2);

        playerTurnID = 1;
        buildDeck();
        deal();
        return true;
    }

    /**
     * Converts player's hand and melds into strings
     * @return (Returns the string to be printed)
     */
    @Override
    public String toString() {
        String ret = player1.toString();
        outText.append(ret);
        return ret;
    }

    /**
     * Assigns the text view from listener
     * @param tv (The text view)
     */
    public void setTextView(TextView tv) {
        outText = tv;
    }


    /**
     * Performs the testing actions once the button is clicked
     * @param view (The view that is being updated)
     */
    @Override
    public void onClick(View view) {
        outText.setText("");

        CanastaGameState firstInstance = new CanastaGameState();

        firstInstance.setTextView(outText);
        Card addedCard = new Card(5,'H');
        firstInstance.player1.getHand().add(addedCard);
        firstInstance.player1.getHand().add(addedCard);
        firstInstance.player1.getHand().add(addedCard);
        firstInstance.player1.getHand().add(addedCard);

        CanastaGameState secondInstance = new CanastaGameState(firstInstance);

        firstInstance.drawFromDeck(firstInstance.player1);
        outText.append("Player one drew from the deck.\n");
        firstInstance.selectCard(firstInstance.player1,5);
        outText.append("Player one selected a " + addedCard.getValue() + "\n");
        firstInstance.meldCard(firstInstance.player1);
        outText.append("Player one melded a " + firstInstance.selectedCard + "\n");

        firstInstance.meldCard(firstInstance.player1);
        outText.append("Player one melded a " + firstInstance.selectedCard + "\n");
        firstInstance.undo(firstInstance.player1);
        outText.append("Player one undid a melded.\n");
        firstInstance.meldCard(firstInstance.player1);
        outText.append("Player one melded a " + firstInstance.selectedCard + "\n");
        firstInstance.meldCard(firstInstance.player1);
        outText.append("Player one melded a " + firstInstance.selectedCard + "\n");
        firstInstance.addToDiscard(firstInstance.player1);
        outText.append("Player one discarded\n\n");

        CanastaGameState thirdInstance = new CanastaGameState();
        thirdInstance.setTextView(outText);
        CanastaGameState fourthInstance = new CanastaGameState(thirdInstance);


        secondInstance.toString();
        fourthInstance.toString();
        outText.invalidate();
    }




    //accessors
    public int getPlayer1Score() {
        return player1Score;
    }
    public int getPlayer2Score() {
        return player2Score;
    }

    public int getPlayerTurnID() {
        return playerTurnID;
    }
    public void setSelectedCard(int card) {
        selectedCard = card;
    }
    public int getSelectedCard() {
        return selectedCard;
    }



}
