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
        EnumMap<Ranking, Integer> statistics = new EnumMap<>(Ranking.class);
        initStatistics(statistics);
        updateStatistics(lottos, winningNumbers, statistics);
        return statistics;
    }

    private void initStatistics(EnumMap<Ranking, Integer> statistics) {
        for (Ranking ranking : Ranking.values()) {
            statistics.put(ranking, ZERO_QUANTITY);
        }
    }

    private void updateStatistics(PurchasedLottos lottos, WinningNumbers winningNumbers, EnumMap<Ranking, Integer> statistics) {
        lottos.getLottos().forEach(lotto -> {
            int matchingCount = lotto.getMatchingCount(winningNumbers.getWinningLotto());
            boolean hasBonusNumber = lotto.hasBonusNumber(winningNumbers.getBonusNumber());
            Ranking ranking = Ranking.getRanking(matchingCount, hasBonusNumber);

            int sumUnit = 1;
            statistics.merge(ranking, sumUnit, Integer::sum);
        });
    }
}
