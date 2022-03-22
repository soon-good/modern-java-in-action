package io.study.lang.javastudy2022ty1.effectivejava_temp.item58;

import org.junit.jupiter.api.Test;

import java.util.*;

public class IteratorTest {

    enum Suit {CLUB, DIAMOND, HEART, SPACE}
    enum Rank {ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}

    static Collection<Suit> suits = Arrays.asList(Suit.values());
    static Collection<Rank> ranks = Arrays.asList(Rank.values());

    class Card{
        private Suit suit;
        private Rank rank;
        public Card(Suit suit, Rank rank){
            this.suit = suit;
            this.rank = rank;
        }
    }

    @Test
    public void 테스트1_suits_를_내부_for_문에서까지_순회하는_것으로_인해_에러가_발생한다(){
        List<Card> deck = new ArrayList<>();

        for(Iterator<Suit> i = suits.iterator(); i.hasNext();){
            for(Iterator<Rank> j = ranks.iterator(); j.hasNext();){
                Suit suit = i.next();
                Rank rank = j.next();
                System.out.println("suit = " + suit + ", rank = " + rank);
                deck.add(new Card(suit, rank));
            }
        }
    }

    @Test
    public void 테스트2_suits_순회를_for_each_구문으로_개선해보기(){
        List<Card> deck = new ArrayList<>();

        for(Suit suit : suits){
            for(Rank rank : ranks){
                System.out.println("suit = " + suit + ", rank = " + rank);
                deck.add(new Card(suit, rank));
            }
        }
    }
}
