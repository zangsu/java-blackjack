package blackjack.domain.card;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HandTest {

    private static final List<Card> TWO_ACE = CardTest.TWO_ACE;
    private static final List<Card> SCORE_13_WITH_ACE = CardTest.SCORE_13_WITH_ACE;
    private static final List<Card> CARDS_SCORE_16 = CardTest.CARDS_SCORE_16;
    private static final List<Card> CARDS_SCORE_21 = CardTest.CARDS_SCORE_21;
    private static final List<Card> BLACKJACK = CardTest.BLACKJACK;
    private static final List<Card> CARDS_SCORE_22 = CardTest.CARDS_SCORE_22;

    @DisplayName("점수를 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("cardsAndScore")
    void calculateScoreTest(List<Card> cards, int expected) {
        Hand hand = new Hand(cards);

        assertThat(hand.calculateScore()).isEqualTo(expected);
    }

    static Stream<Arguments> cardsAndScore() {
        return Stream.of(Arguments.of(BLACKJACK, 21),
                Arguments.of(TWO_ACE, 12),
                Arguments.of(SCORE_13_WITH_ACE, 13),
                Arguments.of(CARDS_SCORE_16, 16));
    }

    @DisplayName("카드의 버스트 상태를 알 수 있다.")
    @Nested
    class BustTest {

        @DisplayName("21점이 넘으면 버스트이다.")
        @Test
        void whenBusted_returnTrue() {
            Hand bustedHand = new Hand(CARDS_SCORE_22);

            assertThat(bustedHand.isBusted()).isTrue();
        }

        @DisplayName("21점 이하 점수는 버스트가 아니다.")
        @Test
        void whenNotBusted_returnFalse() {
            Hand hand = new Hand(CARDS_SCORE_21);

            assertThat(hand.isBusted()).isFalse();
        }
    }

    @DisplayName("카드의 블랙잭 상태를 판단할 수 있다.")
    @Nested
    class BlackjackTest {

        @DisplayName("21점이면서 2장의 카드라면 블랙잭이다.")
        @Test
        void whenBlackjack_returnTrue() {
            Hand blackjackHand = new Hand(BLACKJACK);

            assertThat(blackjackHand.isBlackjack()).isTrue();
        }

        @DisplayName("21점 미만 점수는 블랙잭이 아니다.")
        @Test
        void whenUnderScore_returnFalse() {
            Hand blackjackHand = new Hand(List.of(
                    new Card(Value.KING, Shape.HEART),
                    new Card(Value.QUEEN, Shape.HEART)
            ));

            assertThat(blackjackHand.isBlackjack()).isFalse();
        }

        @DisplayName("21점이지만 3장의 카드라면 블랙잭이 아니다.")
        @Test
        void whenOverSize_returnFalse() {
            Hand blackjackHand = new Hand(CARDS_SCORE_21);

            assertThat(blackjackHand.isBlackjack()).isFalse();
        }

        @DisplayName("21점 초과 점수는 블랙잭이 아니다.")
        @Test
        void whenOverScore_returnFalse() {
            Hand blackjackHand = new Hand(CARDS_SCORE_22);

            assertThat(blackjackHand.isBlackjack()).isFalse();
        }

    }
}
