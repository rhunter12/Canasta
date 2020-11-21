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

    private int playerTurnID; //player turn ID
    private int selectedCard = -1; //selected card
    private int turnStage;//used to determine which actions are allowed
    private PlayerResources[] resources = new PlayerResources[2];

    /**
     * Constructor
     */
    public CanastaGameState() {
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        player1Score = 0;
        player2Score = 0;
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
        playerTurnID = orig.playerTurnID;
        turnStage=orig.turnStage;
        resources[0] = orig.resources[0];
        resources[1] = orig.resources[1];
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
            resources[0].getHand().add(deck.remove(0));
            resources[1].getHand().add(deck.remove(0));
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
        resources[0] = new PlayerResources(0);
        resources[1] = new PlayerResources(1);


        playerTurnID = 0;
        buildDeck();
        deal();
        return true;
    }

    /**
     * Starts a new round by removing the hand, melds, deck, and discard pile
     *
     * External resource
     * Date: 11 November 2020
     * Problem: Needed to empty and array list without changing the address of the list itself
     * Resource: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
     * Solution: found retainAll, which retains only the items specified in the parameter
     */
    public void cleanStart() {
        updatePoints();
        resources[0].addTotalScore(resources[0].getScore());
        deck.retainAll(new ArrayList<Card>());
        discardPile.retainAll(new ArrayList<Card>());

        resources[0].getHand().retainAll(new ArrayList<Card>());
        for (int i=1; i<resources[0].getMelds().size(); i++) {
            resources[0].getMelds().get(i).retainAll(new ArrayList<Card>());
        }

        resources[1].getHand().retainAll(new ArrayList<Card>());
        for (int i=1; i<resources[0].getMelds().size(); i++) {
            resources[1].getMelds().get(i).retainAll(new ArrayList<Card>());
        }

        buildDeck();
        deal();
    }

    public void updatePoints(){
        int sum=0;
        for (int i=1; i<resources[0].getMelds().size(); i++){
            boolean hasWilds=false;
            for (int j=0; j<resources[0].getMelds().get(i).size();j++){
                if (resources[0].getMelds().get(i).get(j).getValue()==0){
                    hasWilds=true;
                    sum=sum+50;
                }
                else if (resources[0].getMelds().get(i).get(j).getValue()==2){
                    hasWilds=true;
                    sum=sum+20;
                }
                else if (resources[0].getMelds().get(i).get(j).getValue()==1){
                    sum=sum+20;
                }
                else if (resources[0].getMelds().get(i).get(j).getValue()<=7){
                    sum=sum+5;
                }
                else if (resources[0].getMelds().get(i).get(j).getValue()>7){
                    sum=sum+10;
                }
            }

            if (resources[0].getMelds().get(i).size()>=7){//canastas
                if (i==2){
                    sum=sum+1000;
                }
                else if (hasWilds){
                    sum=sum+300;
                }
                else{
                    sum=sum+500;
                }
            }
        }
        if(playerTurnID == 0) {
            resources[0].setScore(sum);

        }
        sum=0;
        for (int i=1; i<resources[1].getMelds().size(); i++){

            boolean hasWilds=false;
            for (int j=0; j<resources[1].getMelds().get(i).size();j++){
                if (resources[1].getMelds().get(i).get(j).getValue()==0){
                    hasWilds=true;
                    sum=sum+50;
                }
                else if (resources[1].getMelds().get(i).get(j).getValue()==2){
                    hasWilds=true;
                    sum=sum+20;
                }
                else if (resources[1].getMelds().get(i).get(j).getValue()==1){
                    sum=sum+20;
                }
                else if (resources[1].getMelds().get(i).get(j).getValue()<=7){
                    sum=sum+5;
                }
                else if (resources[1].getMelds().get(i).get(j).getValue()>7){
                    sum=sum+10;
                }
            }

            if (resources[1].getMelds().get(i).size()>=7){//canastas
                if (i==2){
                    sum=sum+1000;
                }
                else if (hasWilds){
                    sum=sum+300;
                }
                else{
                    sum=sum+500;
                }
            }
        }

        resources[1].setScore(sum);


    }


    public int checkPointsToMeld (int pNum) {
        if (resources[pNum].getTotalScore() < 0) {
            return 15;
        }
        else if (resources[pNum].getTotalScore() < 1500) {
            return 50;
        }
        else if (resources[pNum].getTotalScore() < 3000) {
            return 90;
        }
        else {
            return 120;
        }
    }

    public boolean isPileLocked() {
        for (int i = 0; i < discardPile.size(); i++) {
            if (discardPile.get(i).getValue() == 0 || discardPile.get(i).getValue() == 2) {
                return true;
            }
        }
        return false;
    }


    //accessors
    public int getPlayer1Score() {
        return player1Score;
    }
    public void setPlayer1Score(int score) {
        this.player1Score = score;
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

    public PlayerResources getResources(int num) {
        return resources[num];
    }



}