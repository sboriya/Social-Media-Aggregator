package com.social.aggregator.enums;

public enum Theme {
    DARK,
    LIGHT;
    public static boolean isValidTheme(String theme) {
        for (Theme themeEnum : values()) {
            if (themeEnum.name().equalsIgnoreCase(theme.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
