package blackjack.domain.participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import blackjack.domain.card.Deck;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayersTest {

    @DisplayName("최소 한 명 이상의 플레이어가 존재해야 한다.")
    @Test
    void validateTest_countOfPlayersIsZero_throwException() {
        assertThatThrownBy(() -> Players.from(Collections.emptyList(), Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 한 명의 플레이어가 있어야 합니다.");
    }

    @DisplayName("N 명 이상의 플레이어를 가지면 예외가 발생한다.")
    @Test
    void validateTest_tooManyPlayers_throwException() {
        List<String> manyNames = List.of("1", "2", "3", "4", "5");
        List<Integer> manyBets = List.of(1, 2, 3, 4, 5);

        assertThatThrownBy(() -> Players.from(manyNames, manyBets))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최대 4명의 플레이어만 참여 가능합니다.");
    }

    @DisplayName("중복된 이름을 사용하면, 예외가 발생한다.")
    @Test
    void validateTest_whenNameIsDuplicated_throwException() {
        List<String> duplicatedNames = List.of("짱수", "커찬", "커찬");
        List<Integer> bets = List.of(1, 2, 3);

        assertThatThrownBy(() -> Players.from(duplicatedNames, bets))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 이름을 사용할 수 없습니다.");
    }

    @DisplayName("게임을 시작할 때 모든 플레이어들은 카드를 두 장 뽑는다.")
    @Test
    void drawStartCardsTest() {
        Players players = Players.from(List.of("1", "2"), List.of(1, 2));
        Deck deck = Deck.createShuffledDeck();

        players.drawStartCards(deck);

        List<Player> allPlayers = players.getPlayers();
        assertAll(
                () -> assertThat(allPlayers.get(0).getCards()).hasSize(2),
                () -> assertThat(allPlayers.get(1).getCards()).hasSize(2)
        );
    }
}
