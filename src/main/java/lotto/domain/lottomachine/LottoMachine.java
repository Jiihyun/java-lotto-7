package lotto.domain.lottomachine;

import lotto.domain.Lotto;
import lotto.domain.PurchasedLottos;
import lotto.domain.WinningNumbers;
import lotto.domain.constant.Ranking;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.IntStream;

public class LottoMachine {

    public static final int ZERO_QUANTITY = 0;

    public PurchasedLottos issueTickets(NumberGenerator numberGenerator, int quantity) {
        List<Lotto> purchasedLottos = IntStream.range(ZERO_QUANTITY, quantity)
                .mapToObj(i -> new Lotto(numberGenerator.generateNumbers()))
                .toList();
        return PurchasedLottos.from(purchasedLottos);
    }

    public EnumMap<Ranking, Integer> draw(PurchasedLottos lottos, WinningNumbers winningNumbers) {
        EnumMap<Ranking, Integer> rankings = new EnumMap<>(Ranking.class);
        for (Ranking ranking : Ranking.values()) {
            rankings.put(ranking, 0);
        }
        for (Lotto lotto : lottos.getLottos()) {
            int matchingCount = lotto.getMatchingCount(winningNumbers.getWinningLotto());
            boolean hasBonusNumber = lotto.hasBonusNumber(winningNumbers.getBonusNumber());
            Ranking ranking = Ranking.getRanking(matchingCount, hasBonusNumber);
            Integer count = rankings.get(ranking);
            rankings.replace(ranking, count + 1);
        }
        return rankings;
    }
}
