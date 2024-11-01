package lotto.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    public static final Money ZERO = Money.won(0);
    public static final int ZERO_THRESHOLD = 0;

    private final BigDecimal amount;

    public static Money won(int amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    Money(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isNotMultipleOf(Money other) {
        return this.amount.remainder(other.amount)
                .compareTo(BigDecimal.ZERO) != ZERO_THRESHOLD;
    }

    public Money divide(Money money) {
        return new Money(this.amount.divideToIntegralValue(money.amount));
    }

    public boolean isGreaterThan(Money other) {
        return amount.compareTo(other.amount) > ZERO_THRESHOLD;
    }

    public int intValue() {
        return amount.intValue();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Money other)) {
            return false;
        }

        return Objects.equals(amount.doubleValue(), other.amount.doubleValue());
    }

    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
