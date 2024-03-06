# java-blackjack

블랙잭 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## 기능 요구 사항

- 블랙잭 게임은 딜러와 플레이어 중 카드의 합이 21 또는 21에 가장 가까운 숫자를 가지는 쪽이 이기는 게임이다.
- 각 카드는 점수를 가진다.
  - 숫자 카드는 카드 숫자로 계산한다.
  - King, Queen, Jack은 각각 10으로 계산한다.
  - Ace는 1 또는 11로 계산한다.
- 플레이어는 이름을 가진다.
  - 이름은 공백으로만 구성될 수 없다.
  - 이름은 앞뒤 공백을 가질 수 없다.
  - 중복된 이름은 가질 수 없다.

### 플레이어 승리 조건

- 아래의 두 조건을 모두 만족하면 플레이어가 승리한다.
  - 카드의 점수 합이 21을 넘기지 않는다.
  - 딜러보다 점수가 높거나 딜러의 카드 점수 합이 21을 초과한다.

### 프로그램 진행 순서
1. 참가자 이름 입력한다.
2. 카드를 2장씩 나눠준 후에, 현재 카드 상태 출력한다.
3. 각 참가자들의 턴을 진행한다.
   1. 카드가 21을 넘은 경우, 턴이 종료된다.
   2. 카드를 더 받을 지 여부를 입력한다.
   3. 만약 더 받는다면, 카드를 한 장 추가하고 1으로 돌아간다.
   4. 더 받지 않을 경우, 턴을 종료한다.
4. 딜러의 턴을 진행한다.
   1. 카드가 16을 넘은 경우, 턴이 종료된다.
   2. 카드를 한 장 더 추가하고, 1로 돌아간다.
5. 딜러 및 모든 참가자의 보유 카드들과 점수를 출력한다.
6. 딜러 및 모든 참가자의 승패를 출력한다.

#### Card

- 문양과 숫자를 가진다.
- 점수를 반환할 수 있어야 한다.
  - 숫자 카드는 숫자 그대로 점수로 사용한다.
  - King, Queen, Jack은 모두 10점으로 사용한다.
  - Ace는 1, 또는 11 점으로 사용할 수 있다.

#### Deck

- 카드 한장을 제공할 수 있다.

#### Dealer

- 카드 합이 16 이하일 때 카드를 뽑는다.
- 보유 중인 카드 정보와 점수를 반환할 수 있다.

#### Player

- 카드 합이 21 이하일 떄 카드를 뽑을 수 있다.
- 보유 중인 카드 정보와 점수를 반환할 수 있다.
- 카드를 더 뽑을 수 있는지 알 수 있다.
