package edu.up.cs301.canasta;

import org.junit.Test;

import static org.junit.Assert.*;

public class CanastaGameStateTest {

    @Test
    public void buildDeck() {
        //test constructor
        CanastaGameState gs=new CanastaGameState();
        assertNotNull(gs);
        assertNotNull(gs.deck);
        assertNotNull(gs.discardPile);
        assertTrue(gs.getPlayer1Score()==0);
        assertTrue(gs.getPlayer2Score()==0);

        //test buildDeck
        gs.buildDeck();
        assertEquals(gs.deck.size(),108,0.000000000001);//test that all cards exist and no extras
        for (int i=0; i<108; i++){//test that none of the cards have the same address
            for (int j=i+1; j<108; j++){
                assertTrue(gs.deck.get(i)!=gs.deck.get(j));
            }
        }
    }

    @Test
    public void deal() {
        //deal cannot be run outside of start
    }

    @Test
    public void start() {
        CanastaGameState gs=new CanastaGameState();
        gs.start();
        //start-specific checks
        assertNotNull(gs.player1);
        assertNotNull(gs.player2);

        assertNotNull(gs.player1.getHand().size()==15);
        assertNotNull(gs.player2.getHand().size()==15);

        assertNotNull(gs.player1.getMeldedWild());
        assertNotNull(gs.player1.getMelded3());
        assertNotNull(gs.player1.getMelded4());
        assertNotNull(gs.player1.getMelded5());
        assertNotNull(gs.player1.getMelded6());
        assertNotNull(gs.player1.getMelded7());
        assertNotNull(gs.player1.getMelded8());
        assertNotNull(gs.player1.getMelded9());
        assertNotNull(gs.player1.getMelded10());
        assertNotNull(gs.player1.getMeldedJack());
        assertNotNull(gs.player1.getMeldedQueen());
        assertNotNull(gs.player1.getMeldedKing());

        assertNotNull(gs.player2.getMeldedAce());
        assertNotNull(gs.player2.getMeldedWild());
        assertNotNull(gs.player2.getMelded3());
        assertNotNull(gs.player2.getMelded4());
        assertNotNull(gs.player2.getMelded5());
        assertNotNull(gs.player2.getMelded6());
        assertNotNull(gs.player2.getMelded7());
        assertNotNull(gs.player2.getMelded8());
        assertNotNull(gs.player2.getMelded9());
        assertNotNull(gs.player2.getMelded10());
        assertNotNull(gs.player2.getMeldedJack());
        assertNotNull(gs.player2.getMeldedQueen());
        assertNotNull(gs.player2.getMeldedKing());
        assertNotNull(gs.player2.getMeldedAce());


    }

    @Test
    public void setSelectedCard() {
        CanastaGameState gs=new CanastaGameState();
        gs.start();
        assert(gs.getSelectedCard()==-1);
        gs.setSelectedCard(gs.player1.getHand().get(0).getValue());
        assert(gs.getSelectedCard()==gs.player1.getHand().get(0).getValue());
    }
}