package edu.up.cs301.canasta;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;

public class CanastaLocalGame extends LocalGame {

    private CanastaGameState state;

    public CanastaLocalGame() {
        state = new CanastaGameState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        if (state == null) {
            return;
        }

        CanastaGameState stateForPlayer = new CanastaGameState(state);
        p.sendInfo(stateForPlayer);
    }

    @Override
    protected boolean canMove(int playerIdx) {
        if (state.getPlayerTurnID() == playerIdx) {
            return true;
        }
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        if (state.player1 instanceof CanastaPlayer && state.player2 instanceof CanastaComputerPlayer1) {
            if (((CanastaPlayer)state.player1).getTotalScore() >= 5000 || ((CanastaComputerPlayer1)state.player2).getTotalScore() >= 5000) {
                if (((CanastaPlayer)state.player1).getTotalScore() > ((CanastaComputerPlayer1)state.player2).getTotalScore()) {
                    return playerNames[0] + " wins";
                }
                else if (((CanastaComputerPlayer1)state.player2).getTotalScore() > ((CanastaPlayer)state.player1).getTotalScore()) {
                    return playerNames[1] + " wins";
                }
                else if (((CanastaPlayer)state.player1).getTotalScore() == ((CanastaComputerPlayer1)state.player2).getTotalScore()) {
                    return "It's a tie";
                }
            }
        }

        else if (state.player1 instanceof CanastaComputerPlayer1 && state.player2 instanceof CanastaPlayer) {
            if (((CanastaComputerPlayer1)state.player1).getTotalScore() >= 5000 || ((CanastaPlayer)state.player2).getTotalScore() >= 5000) {
                if (((CanastaComputerPlayer1)state.player1).getTotalScore() > ((CanastaPlayer)state.player2).getTotalScore()) {
                    return playerNames[0] + " wins";
                }
                else if (((CanastaPlayer)state.player2).getTotalScore() > ((CanastaComputerPlayer1)state.player1).getTotalScore()) {
                    return playerNames[1] + " wins";
                }
                else if (((CanastaComputerPlayer1)state.player1).getTotalScore() == ((CanastaPlayer)state.player2).getTotalScore()) {
                    return "It's a tie";
                }
            }
        }
        else {
            System.out.println("We don't support that");
        }

        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        System.out.println("Make move called");

        int currentPlayer = state.getPlayerTurnID();
        boolean yourTurn;

        if (action.getPlayer() instanceof CanastaPlayer) {
            yourTurn = canMove(((CanastaPlayer) action.getPlayer()).getPlayerNum());
        }
        else {
            yourTurn = canMove(((CanastaComputerPlayer1) action.getPlayer()).getPlayerNum());
        }


        if (!yourTurn) {
            action.getPlayer().sendInfo(new NotYourTurnInfo());
            return false;
        }

        else if (action instanceof CanastaDrawAction) {
            System.out.println("Draw action called");
            if (currentPlayer == 0) {
                if (state.player1 instanceof CanastaPlayer) {
                    CanastaPlayer p = (CanastaPlayer)state.player1;
                    drawFromDeck(p.getHand(), currentPlayer);
                }
                else if (state.player1 instanceof CanastaComputerPlayer1) {
                    CanastaComputerPlayer1 p = (CanastaComputerPlayer1)state.player1;
                    drawFromDeck(p.getHand(), currentPlayer);
                }

            }
            else if (currentPlayer == 1) {
                if (state.player2 instanceof CanastaPlayer) {
                    CanastaPlayer p = (CanastaPlayer)state.player2;
                    drawFromDeck(p.getHand(), currentPlayer);
                }
                else if (state.player2 instanceof CanastaComputerPlayer1) {
                    CanastaComputerPlayer1 p = (CanastaComputerPlayer1)state.player2;
                    drawFromDeck(p.getHand(), currentPlayer);
                }
            }
        }

        else if (action instanceof CanastaDiscardAction) {
            if (currentPlayer == 0) {
                addToDiscard(state.player1);
            }
            else if (currentPlayer == 1) {
                addToDiscard(state.player2);
            }
        }

        else if (action instanceof CanastaMeldAction) {
            if (currentPlayer == 0) {
                meldCard(state.player1);
            }
            else if (currentPlayer == 1) {
                meldCard(state.player2);
            }
        }

        else if (action instanceof CanastaSelectCardAction) {
            if (currentPlayer == 0) {
                selectCard(currentPlayer,((CanastaSelectCardAction) action).getSelectedValue());
            }
            else if (currentPlayer == 1) {
                selectCard(currentPlayer,((CanastaSelectCardAction) action).getSelectedValue());
            }
        }

        else if (action instanceof CanastaUndoAction) {
            if (currentPlayer == 0) {
                if (state.player1 instanceof CanastaPlayer) {
                    CanastaPlayer p = (CanastaPlayer)state.player1;
                    undo(p);
                }
            }
            else if (currentPlayer == 1) {
                if (state.player2 instanceof CanastaPlayer) {
                    CanastaPlayer p = (CanastaPlayer)state.player2;
                    undo(p);
                }
            }
        }

        else {
            return false;
        }

        if (state.player1 instanceof CanastaPlayer) {
            CanastaPlayer p = (CanastaPlayer)state.player1;
            p.sendInfo(state);
        }

        if (state.player2 instanceof CanastaComputerPlayer1) {
            CanastaComputerPlayer1 p = (CanastaComputerPlayer1)state.player2;
            p.sendInfo(state);
        }
        //state.player1.sendInfo(state);
        //state.player2.sendInfo(state);
        return true;
    }



    /**
     * Takes two cards from deck; checks if it is a red three and
     * handles it accordingly
     * @param hand (The players hand)
     * @param currentPlayer (The current player)
     */
    private void drawFromDeck(ArrayList<Card> hand, int currentPlayer) {
        hand.add(state.deck.remove(0));
        hand.add(state.deck.remove(0));
        removeRedThree(hand, currentPlayer);
    }

    /**
     * Removes red three from hand and replaces it with something else
     * @param hand (The players hand)
     * @param currentPlayer (The current player)
     */
    private void removeRedThree(ArrayList<Card> hand, int currentPlayer) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(0).getValue() == 3 && (hand.get(0).getSuit() == 'H' || hand.get(0).getSuit() == 'D')) {
                hand.remove(i);
                hand.add(state.deck.remove(0));
                i = 0;  //resets loop if a red three has been found. Checks if new card is a red three
                if (currentPlayer == 0) {
                    if (state.player1 instanceof CanastaPlayer) {
                        CanastaPlayer p = (CanastaPlayer)state.player1;
                        p.setScore(p.getScore() + 100);
                    }
                    else if (state.player1 instanceof CanastaComputerPlayer1) {
                        CanastaComputerPlayer1 p = (CanastaComputerPlayer1)state.player1;
                        p.setScore(p.getScore() + 100);
                    }
                }
                else if (currentPlayer == 1) {
                    if (state.player2 instanceof CanastaPlayer) {
                        CanastaPlayer p = (CanastaPlayer)state.player2;
                        p.setScore(p.getScore() + 100);
                    }
                    else if (state.player2 instanceof CanastaComputerPlayer1) {
                        CanastaComputerPlayer1 p = (CanastaComputerPlayer1)state.player2;
                        p.setScore(p.getScore() + 100);
                    }
                }
            }
        }
    }

    /**
     * Helper method to find the number of wild
     * cards in a meld
     * @param cards (The deck of cards to be searched)
     * @param value (Which meld is being considered)
     * @return (Returns the number of wild cards found)
     */
    public int countWildCards(ArrayList<Card> cards, int value) {
        int wildCount = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() != value) {
                wildCount++;
            }
        }
        return wildCount;
    }

    /**
     * Checks if all melds are of three or more cards
     * and more than half of the cards are not a wild cards or not empty
     * @param gp (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean checkValidMeld(GamePlayer gp) {
        if (gp instanceof CanastaPlayer) {
            CanastaPlayer p = (CanastaPlayer) gp;

            if (!((p.getMeldedAce().size() >= 3 && countWildCards(p.getMeldedAce(), 1) <= p.getMeldedAce().size() / 2) || p.getMeldedAce().size() == 0)) {
                return false;
            }
            if (!((p.getMelded4().size() >= 3 && countWildCards(p.getMelded4(), 4) <= p.getMelded4().size() / 2) || p.getMelded4().size() == 0)) {
                return false;
            }
            if (!((p.getMelded5().size() >= 3 && countWildCards(p.getMelded5(), 5) <= p.getMelded5().size() / 2) || p.getMelded5().size() == 0)) {
                return false;
            }
            if (!((p.getMelded6().size() >= 3 && countWildCards(p.getMelded6(), 6) <= p.getMelded6().size() / 2) || p.getMelded6().size() == 0)) {
                return false;
            }
            if (!((p.getMelded7().size() >= 3 && countWildCards(p.getMelded7(), 7) <= p.getMelded7().size() / 2) || p.getMelded7().size() == 0)) {
                return false;
            }
            if (!((p.getMelded8().size() >= 3 && countWildCards(p.getMelded8(), 8) <= p.getMelded8().size() / 2) || p.getMelded8().size() == 0)) {
                return false;
            }
            if (!((p.getMelded9().size() >= 3 && countWildCards(p.getMelded9(), 9) <= p.getMelded9().size() / 2) || p.getMelded9().size() == 0)) {
                return false;
            }
            if (!((p.getMelded10().size() >= 3 && countWildCards(p.getMelded10(), 10) <= p.getMelded10().size() / 2) || p.getMelded10().size() == 0)) {
                return false;
            }
            if (!((p.getMeldedJack().size() >= 3 && countWildCards(p.getMeldedJack(), 11) <= p.getMeldedJack().size() / 2) || p.getMeldedJack().size() == 0)) {
                return false;
            }
            if (!((p.getMeldedQueen().size() >= 3 && countWildCards(p.getMeldedQueen(), 12) <= p.getMeldedQueen().size() / 2) || p.getMeldedQueen().size() == 0)) {
                return false;
            }
            if (!((p.getMeldedKing().size() >= 3 && countWildCards(p.getMeldedKing(), 13) <= p.getMeldedKing().size() / 2) || p.getMeldedKing().size() == 0)) {
                return false;
            }

            if (!(p.getMeldedWild().size() == 0 || p.getMeldedWild().size() >= 3)) {
                return false;
            }
        }

        else if (gp instanceof CanastaComputerPlayer1) {
            CanastaComputerPlayer1 p = (CanastaComputerPlayer1) gp;

            if (!((p.getMeldedAce().size() >= 3 && countWildCards(p.getMeldedAce(), 1) <= p.getMeldedAce().size() / 2) || p.getMeldedAce().size() == 0)) {
                return false;
            }
            if (!((p.getMelded4().size() >= 3 && countWildCards(p.getMelded4(), 4) <= p.getMelded4().size() / 2) || p.getMelded4().size() == 0)) {
                return false;
            }
            if (!((p.getMelded5().size() >= 3 && countWildCards(p.getMelded5(), 5) <= p.getMelded5().size() / 2) || p.getMelded5().size() == 0)) {
                return false;
            }
            if (!((p.getMelded6().size() >= 3 && countWildCards(p.getMelded6(), 6) <= p.getMelded6().size() / 2) || p.getMelded6().size() == 0)) {
                return false;
            }
            if (!((p.getMelded7().size() >= 3 && countWildCards(p.getMelded7(), 7) <= p.getMelded7().size() / 2) || p.getMelded7().size() == 0)) {
                return false;
            }
            if (!((p.getMelded8().size() >= 3 && countWildCards(p.getMelded8(), 8) <= p.getMelded8().size() / 2) || p.getMelded8().size() == 0)) {
                return false;
            }
            if (!((p.getMelded9().size() >= 3 && countWildCards(p.getMelded9(), 9) <= p.getMelded9().size() / 2) || p.getMelded9().size() == 0)) {
                return false;
            }
            if (!((p.getMelded10().size() >= 3 && countWildCards(p.getMelded10(), 10) <= p.getMelded10().size() / 2) || p.getMelded10().size() == 0)) {
                return false;
            }
            if (!((p.getMeldedJack().size() >= 3 && countWildCards(p.getMeldedJack(), 11) <= p.getMeldedJack().size() / 2) || p.getMeldedJack().size() == 0)) {
                return false;
            }
            if (!((p.getMeldedQueen().size() >= 3 && countWildCards(p.getMeldedQueen(), 12) <= p.getMeldedQueen().size() / 2) || p.getMeldedQueen().size() == 0)) {
                return false;
            }
            if (!((p.getMeldedKing().size() >= 3 && countWildCards(p.getMeldedKing(), 13) <= p.getMeldedKing().size() / 2) || p.getMeldedKing().size() == 0)) {
                return false;
            }

            if (!(p.getMeldedWild().size() == 0 || p.getMeldedWild().size() >= 3)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Searches hand for selected card and returns index
     * @param hand (The players hand)
     * @param n (The value being searched for)
     * @return (Returns the index of the value in the hand)
     */
    public int searchHand(ArrayList<Card> hand, int n) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == n) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Selects card
     * @param currentPlayer (The player the action is from)
     * @param card (The card that is selected)
     * @return (Returns whether the action was successful or not)
     */
    public boolean selectCard(int currentPlayer, int card) {
        if (state.getPlayerTurnID() == currentPlayer) {
            state.setSelectedCard(card);
            return true;
        }
        return false;
    }

    /**
     * Adds a selected card to the player's meld
     * @param gp (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean meldCard(GamePlayer gp) {
        if (gp instanceof CanastaPlayer) {
            CanastaPlayer p = (CanastaPlayer) gp;

            int pos = searchHand(p.getHand(), state.getSelectedCard());

            if (pos == -1) {
                return false;
            }
            switch (state.getSelectedCard()) {
                case -1:
                    return false;
                case 1:
                    p.getPlayerMoves().add(1);
                    p.getMeldedAce().add(p.getHand().remove(pos));
                    break;
                case 2:
                    p.getPlayerMoves().add(2);
                    p.getMeldedWild().add(p.getHand().remove(pos));
                    break;
                case 3:
                    p.getPlayerMoves().add(3);
                    p.getMelded3().add(p.getHand().remove(pos));
                    break;
                case 4:
                    p.getPlayerMoves().add(4);
                    p.getMelded4().add(p.getHand().remove(pos));
                    break;
                case 5:
                    p.getPlayerMoves().add(5);
                    p.getMelded5().add(p.getHand().remove(pos));
                    break;
                case 6:
                    p.getPlayerMoves().add(6);
                    p.getMelded6().add(p.getHand().remove(pos));
                    break;
                case 7:
                    p.getPlayerMoves().add(7);
                    p.getMelded7().add(p.getHand().remove(pos));
                    break;
                case 8:
                    p.getPlayerMoves().add(8);
                    p.getMelded8().add(p.getHand().remove(pos));
                    break;
                case 9:
                    p.getPlayerMoves().add(9);
                    p.getMelded9().add(p.getHand().remove(pos));
                    break;
                case 10:
                    p.getPlayerMoves().add(10);
                    p.getMelded10().add(p.getHand().remove(pos));
                    break;
                case 11:
                    p.getPlayerMoves().add(11);
                    p.getMeldedJack().add(p.getHand().remove(pos));
                    break;
                case 12:
                    p.getPlayerMoves().add(12);
                    p.getMeldedQueen().add(p.getHand().remove(pos));
                    break;
                case 13:
                    p.getPlayerMoves().add(13);
                    p.getMeldedKing().add(p.getHand().remove(pos));
                    break;
            }
        }

        if (gp instanceof CanastaComputerPlayer1) {
            CanastaComputerPlayer1 p = (CanastaComputerPlayer1) gp;

            int pos = searchHand(p.getHand(), state.getSelectedCard());

            if (pos == -1) {
                return false;
            }
            switch (state.getSelectedCard()) {
                case -1:
                    return false;
                case 1:
                    p.getPlayerMoves().add(1);
                    p.getMeldedAce().add(p.getHand().remove(pos));
                    break;
                case 2:
                    p.getPlayerMoves().add(2);
                    p.getMeldedWild().add(p.getHand().remove(pos));
                    break;
                case 3:
                    p.getPlayerMoves().add(3);
                    p.getMelded3().add(p.getHand().remove(pos));
                    break;
                case 4:
                    p.getPlayerMoves().add(4);
                    p.getMelded4().add(p.getHand().remove(pos));
                    break;
                case 5:
                    p.getPlayerMoves().add(5);
                    p.getMelded5().add(p.getHand().remove(pos));
                    break;
                case 6:
                    p.getPlayerMoves().add(6);
                    p.getMelded6().add(p.getHand().remove(pos));
                    break;
                case 7:
                    p.getPlayerMoves().add(7);
                    p.getMelded7().add(p.getHand().remove(pos));
                    break;
                case 8:
                    p.getPlayerMoves().add(8);
                    p.getMelded8().add(p.getHand().remove(pos));
                    break;
                case 9:
                    p.getPlayerMoves().add(9);
                    p.getMelded9().add(p.getHand().remove(pos));
                    break;
                case 10:
                    p.getPlayerMoves().add(10);
                    p.getMelded10().add(p.getHand().remove(pos));
                    break;
                case 11:
                    p.getPlayerMoves().add(11);
                    p.getMeldedJack().add(p.getHand().remove(pos));
                    break;
                case 12:
                    p.getPlayerMoves().add(12);
                    p.getMeldedQueen().add(p.getHand().remove(pos));
                    break;
                case 13:
                    p.getPlayerMoves().add(13);
                    p.getMeldedKing().add(p.getHand().remove(pos));
                    break;
            }
        }
        return true;
    }


    /**
     * Searches through hand for selected card to move to
     * discard pile
     * @param gp (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean addToDiscard(GamePlayer gp) {
        if (!(checkValidMeld(gp))) {
            return false;
        }

        if (gp instanceof CanastaPlayer) {
            CanastaPlayer p = (CanastaPlayer)gp;

            for (int i = 0; i < p.getHand().size(); i++) {
                if (p.getHand().get(i).getValue() == state.getSelectedCard()) {
                    state.discardPile.add(p.getHand().remove(i));
                    state.setSelectedCard(-1);

                    if (checkIfRoundOver(p)) {
                        state.cleanStart();
                        state.start();
                    }
                    return true;
                }
            }
        }
        else if (gp instanceof CanastaComputerPlayer1) {
            CanastaComputerPlayer1 p = (CanastaComputerPlayer1) gp;

            for (int i = 0; i < p.getHand().size(); i++) {
                if (p.getHand().get(i).getValue() == state.getSelectedCard()) {
                    state.discardPile.add(p.getHand().remove(i));
                    state.setSelectedCard(-1);

                    if (checkIfRoundOver(p)) {
                        state.cleanStart();
                        state.start();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfRoundOver(GamePlayer gp) {
        if (state.deck.size() == 0) {
            return true;
        }

        else if (gp instanceof CanastaPlayer) {
            CanastaPlayer p = (CanastaPlayer) gp;
            if (p.getHand().size() == 0) {
                return true;
            }
        }

        else if (gp instanceof CanastaComputerPlayer1) {
            CanastaComputerPlayer1 p = (CanastaComputerPlayer1)gp;
            if (p.getHand().size() == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * Allows player to unmeld a card based on their previous moves list
     * @param p (The player the action is from)
     * @return (Returns if the action was successful or not)
     */
    public boolean undo(CanastaPlayer p) {
        if(p.getPlayerMoves().size() == 0) {
            return false;
        }
        int value = p.getPlayerMoves().get(p.getPlayerMoves().size()-1);

        switch (value) {
            case 1:
                p.getHand().add(p.getMeldedAce().remove(p.getMeldedAce().size()-1));
                break;
            case 3:
                p.getHand().add(p.getMelded3().remove(p.getMelded3().size()-1));
                break;
            case 4:
                p.getHand().add(p.getMelded4().remove(p.getMelded4().size()-1));
                break;
            case 5:
                p.getHand().add(p.getMelded5().remove(p.getMelded5().size()-1));
                break;
            case 6:
                p.getHand().add(p.getMelded6().remove(p.getMelded6().size()-1));
                break;
            case 7:
                p.getHand().add(p.getMelded7().remove(p.getMelded7().size()-1));
                break;
            case 8:
                p.getHand().add(p.getMelded8().remove(p.getMelded8().size()-1));
                break;
            case 9:
                p.getHand().add(p.getMelded9().remove(p.getMelded9().size()-1));
                break;
            case 10:
                p.getHand().add(p.getMelded10().remove(p.getMelded10().size()-1));
                break;
            case 11:
                p.getHand().add(p.getMeldedJack().remove(p.getMeldedJack().size()-1));
                break;
            case 12:
                p.getHand().add(p.getMeldedQueen().remove(p.getMeldedQueen().size()-1));
                break;
            case 13:
                p.getHand().add(p.getMeldedKing().remove(p.getMeldedKing().size()-1));
                break;
        }

        p.getPlayerMoves().remove(p.getPlayerMoves().size()-1);
        return true;
    }

}
