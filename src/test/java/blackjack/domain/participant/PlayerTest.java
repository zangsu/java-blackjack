package blackjack.domain.participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.card.Shape;
import blackjack.domain.card.Value;
import blackjack.domain.fixture.CardsFixture;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlayerTest {

    private static final Name DEFAULT_NAME = new Name("name");

    @DisplayName("점수를 계산할 수 있다.")
    @ParameterizedTest
    @MethodSource("cardsAndScore")
    void calculateScoreTest(List<Card> cards, int expected) {
        Player player = new Player(cards, DEFAULT_NAME);

        assertThat(player.calculateScore()).isEqualTo(expected);
    }

    static Stream<Arguments> cardsAndScore() {
        return Stream.of(
                Arguments.of(CardsFixture.BLACKJACK, 21),
                Arguments.of(CardsFixture.TWO_ACE, 12),
                Arguments.of(CardsFixture.CARDS_SCORE_16, 16)
        );
    }

    @DisplayName("ACE가 11로 계산되었을 때 버스트가 되지 않는다면, ACE를 그대로 계산한다.")
    @Test
    void calculateScoreTest_usingAceMaxScoreCase() {
        Player player = new Player(List.of(
                new Card(Value.ACE, Shape.HEART),
                new Card(Value.TWO, Shape.HEART)
        ), DEFAULT_NAME);

        assertThat(player.calculateScore()).isEqualTo(13);
    }

    @DisplayName("ACE가 11로 계산되었을 때 버스트가 된다면, ACE를 1로 계산한다.")
    @Test
    void calculateScoreTest_usingAceMinScoreCase() {
        Player player = new Player(List.of(
                new Card(Value.ACE, Shape.HEART),
                new Card(Value.KING, Shape.HEART),
                new Card(Value.TWO, Shape.HEART)
        ), DEFAULT_NAME);

        assertThat(player.calculateScore()).isEqualTo(13);
    }

    @DisplayName("카드의 총 점수가 21을 넘지 않으면, 카드를 더 뽑을 수 있다")
    @Test
    void isDrawableTest_whenScoreIsUnderBound_returnTrue() {
        Player player = new Player(CardsFixture.CARDS_SCORE_21, DEFAULT_NAME);

        assertThat(player.isDrawable()).isTrue();
    }

    @DisplayName("카드의 총 점수가 21을 넘으면, 카드를 더 뽑을 수 없다")
    @Test
    void isDrawableTest_whenScoreIsOverBound_returnFalse() {
        Player player = new Player(CardsFixture.CARDS_SCORE_22, DEFAULT_NAME);

        assertThat(player.isDrawable()).isFalse();
    }

    @DisplayName("게임을 시작할 때는 카드를 두 장 뽑는다.")
    @Test
    void drawStartCardsTest() {
        Player player = Player.from("name");
        Deck deck = Deck.createShuffledDeck();

        player.drawStartCards(deck);

        assertThat(player.getCards()).hasSize(2);
    }

    @DisplayName("이미 카드를 가지고 있는 경우, 시작 카드를 뽑을 수 없다.")
    @Test
    void drawStartCardsTest_whenAlreadyStarted_throwException() {
        Player player = new Player(CardsFixture.CARDS_SCORE_16, DEFAULT_NAME);
        Deck deck = Deck.createShuffledDeck();

        assertThatThrownBy(() -> player.drawStartCards(deck))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 시작 카드를 뽑았습니다.");
    }

    @DisplayName("카드의 총 점수가 21을 넘지 않으면, 카드를 한 장 뽑는다")
    @Test
    void addTest_whenScoreIsUnderBound() {
        Player player = new Player(CardsFixture.CARDS_SCORE_21, DEFAULT_NAME);

        Card additionalCard = new Card(Value.ACE, Shape.HEART);
        player.add(additionalCard);

        assertThat(player.getCards())
                .containsAll(CardsFixture.CARDS_SCORE_21)
                .contains(additionalCard)
                .hasSize(4);
    }

    @DisplayName("카드의 총 점수가 21을 넘으면, 카드를 뽑을 때 예외가 발생한다.")
    @Test
    void addTest_whenScoreIsOverBound_throwException() {
        Player player = new Player(CardsFixture.CARDS_SCORE_22, DEFAULT_NAME);
        Card card = new Card(Value.ACE, Shape.HEART);

        assertThatThrownBy(() -> player.add(card))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("더 이상 카드를 추가할 수 없습니다.");
    }
}
