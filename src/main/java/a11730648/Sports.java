package a11730648;

import java.math.BigDecimal;

public enum Sports {
    ARCHERY,
    BASKETBALL,
    CLIMBING {
        @Override
        public BigDecimal getFeeFactor() {
            return new BigDecimal("1.2");
        }
    },
    DIVING {
        @Override
        public BigDecimal getFeeFactor() {
            return new BigDecimal("1.8");
        }
    },
    FOOTBALL,
    GOLF {
        @Override
        public BigDecimal getFeeFactor() {
            return new BigDecimal("2.1");
        }
    },
    HANDBALL,
    HOCKEY,
    MOUNTAINBIKING,
    PARKOUR;

    public BigDecimal getFeeFactor() {
        return BigDecimal.ONE;
    }

    public BigDecimal getFee(BigDecimal feePerSports) {
        if (feePerSports == null) {
            throw new IllegalArgumentException("Cannot calculate fee with null 'feePerSports' value");
        }

        return getFeeFactor().multiply(feePerSports);
    }
}
