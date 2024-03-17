package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.card.Hand;
import java.util.List;

public abstract class Participant {

    protected static final int BLACKJACK_SCORE = 21;
    private static final int START_CARDS_SIZE = 2;

    private final Hand hand;

    protected Participant(List<Card> cards) {
        this.hand = new Hand(cards);
    }

    protected abstract int getMaxDrawableScore();

    protected abstract int getStartCardSize();

    public final void drawStartCards(Deck deck) {
        if (!hand.isEmpty()) {
            throw new IllegalStateException("이미 시작 카드를 뽑았습니다.");
        }
        for (int i = 0; i < START_CARDS_SIZE; i++) {
            add(deck.draw());
        }
    }

    public void drawAdditionalCard(Deck deck) {
        add(deck.draw());
    }

    protected final void add(Card card) {
        if (!isDrawable()) {
            throw new IllegalStateException("더 이상 카드를 추가할 수 없습니다.");
        }
        hand.add(card);
    }

    public final boolean isDrawable() {
        return calculateScore() <= getMaxDrawableScore();
    }

    public final int calculateScore() {
        return hand.calculateScore();
    }

    public boolean isBlackjack() {
        return hand.isBlackjack();
    }

    public final boolean isBusted() {
        return hand.isBusted();
    }

    public final List<Card> getStartCards() {
        return getCards().subList(0, getStartCardSize());
    }

    public final List<Card> getCards() {
        return hand.getCards();
    }
}
