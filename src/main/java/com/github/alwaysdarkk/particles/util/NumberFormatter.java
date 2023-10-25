package com.github.alwaysdarkk.particles.util;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class NumberFormatter {

    private final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.##");
    private final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("##.##");

    private final Object[][] MONEY_FORMATS = {
        {
            "", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD",
            "OD", "ND", "VG", "UVG", "DVG", "TVG", "QTV", "QNV", "SEV", "SPV", "OVG", "NVG", "ITG"
        },
        {
            1D,
            1000.0,
            1000000.0D,
            1.0E9D,
            1.0E12D,
            1.0E15D,
            1.0E18D,
            1.0E21D,
            1.0E24D,
            1.0E27D,
            1.0E30D,
            1.0E33D,
            1.0E36D,
            1.0E39D,
            1.0E42D,
            1.0E45,
            1.0E48,
            1.0E51D,
            1.0E54D,
            1.0E57D,
            1.0E60D,
            1.0E63D,
            1.0E66,
            1.0E69D,
            1.0E72D,
            1.0E75,
            1.0E78,
            1.0E81D,
            1.0E84D,
            1.0E87D,
            1.0E90D,
            1.0E93D
        }
    };
    private final double LOG = 6.907755278982137D;

    public String formatWithSuffix(double number) {
        if (number <= 999999) return format(number);
        int index = (int) (Math.log(number) / LOG);

        return CURRENCY_FORMAT.format(number / (double) MONEY_FORMATS[1][index]) + MONEY_FORMATS[0][index];
    }

    public String format(double value) {
        return CURRENCY_FORMAT.format(value);
    }

    public String formatPercentage(double value) {
        return PERCENTAGE_FORMAT.format(value);
    }
}