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
import java.util.Collection;
import java.util.Collections;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;


public class CanastaGameState extends GameState {

    // instance variables
    ArrayList<Card> deck = new ArrayList<>(); //deck
    ArrayList<Card> discardPile = new ArrayList<>(); //discard pile
    private int player1Score; //player 1 is the human
    private int player2Score; //player 2 is the AI
    CanastaPlayer player1; //player 1
    CanastaPlayer player2; //player 2
    private int playerTurnID; //player turn ID
    private int selectedCard = -1; //selected card
    private int turnStage;//used to determine which actions are allowed

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
        turnStage=0;
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
        turnStage=orig.turnStage;
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
            //deck.add(new Card(0,'W'));
            //deck.add(new Card(0,'W'));
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
        player1 = new CanastaPlayer(0,"Human");
        player2 = new CanastaPlayer(1,"AI");

        playerTurnID = 0;
        buildDeck();
        deal();
        return true;
    }

    /**
     * Starts a new round by removing the hand, melds, deck, and discard pile
     */
    public void cleanStart() {
        updatePoints();
        deck.retainAll(new ArrayList<Card>());
        discardPile.retainAll(new ArrayList<Card>());

        player1.getHand().retainAll(new ArrayList<Card>());
        for (int i=1; i<player1.getMelds().size(); i++) {
            player1.getMelds().get(i).retainAll(new ArrayList<Card>());
        }

        player2.getHand().retainAll(new ArrayList<Card>());
        for (int i=1; i<player1.getMelds().size(); i++) {
            player2.getMelds().get(i).retainAll(new ArrayList<Card>());
        }

        buildDeck();
        deal();
    }

    public void updatePoints(){
        //player1.addTotalScore(player1.getScore());
        int sum=0;
        for (int i=1; i<player1.getMelds().size(); i++){
            boolean hasWilds=false;
            for (int j=0; j<player1.getMelds().get(i).size();j++){
                if (player1.getMelds().get(i).get(j).getValue()==0){
                    hasWilds=true;
                    sum=sum+50;
                }
                else if (player1.getMelds().get(i).get(j).getValue()==2){
                    hasWilds=true;
                    sum=sum+20;
                }
                else if (player1.getMelds().get(i).get(j).getValue()==1){
                    sum=sum+20;
                }
                else if (player1.getMelds().get(i).get(j).getValue()<=7){
                    sum=sum+5;
                }
                else if (player1.getMelds().get(i).get(j).getValue()>7){
                    sum=sum+10;
                }
            }
            if (player1.getMelds().get(i).size()>=7){//canastas
                if (i==2){
                    player1.addTotalScore(1000);
                }
                else if (hasWilds){
                    player1.addTotalScore(300);
                }
                else{
                    player1.addTotalScore(500);
                }
            }
        }
        player1.addTotalScore(player1.getScore() + sum);
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
    public void setPlayerTurnID(int playerTurnID){ this.playerTurnID = playerTurnID; }
    public void setSelectedCard(int card) {
        selectedCard = card;
    }
    public int getSelectedCard() {
        return selectedCard;
    }
    public int getTurnStage(){return turnStage;}
    public int nextTurnStage(){
        turnStage++;
        turnStage=turnStage%2;
        return turnStage;
        //0 means player needs to draw or pick up discard pile
        //1 means you can meld, discard, or undo
    }
    public int nextPlayer(){
        playerTurnID++;
        playerTurnID=playerTurnID%2;
        return playerTurnID;
    }



}
