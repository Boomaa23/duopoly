package com.boomaa.duopoly;

public enum SpaceName {
    GO,
    MEDITERRANEAN_AVENUE,
    BALTIC_AVENUE,
    INCOME_TAX,
    READING_RAILROAD,
    ORIENTAL_AVENUE,
    VERMONT_AVENUE,
    CONNECTICUT_AVENUE,
    ST_CHARLES_PLACE("St. Charles Place"),
    ELECTRIC_COMPANY,
    STATES_AVENUE,
    VIRGINIA_AVENUE,
    PENNSYLVANIA_RAILROAD,
    ST_JAMES_PLACE("St. James Place"),
    TENNESSEE_AVENUE,
    NEW_YORK_AVENUE,
    FREE_PARKING,
    KENTUCKY_AVENUE,
    INDIANA_AVENUE,
    ILLINOIS_AVENUE,
    B_AND_O_RAILROAD("B. & O. Railroad"),
    ATLANTIC_AVENUE,
    VENTNOR_AVENUE,
    WATER_WORKS,
    MARVIN_GARDENS,
    PACIFIC_AVENUE,
    NORTH_CAROLINA_AVENUE,
    PENNSYLVANIA_AVENUE,
    SHORT_LINE,
    PARK_PLACE,
    LUXURY_TAX,
    BOARDWALK,
    CHANCE,
    COMMUNITY_CHEST,
    JAIL,
    GO_TO_JAIL;

    private final String displayName;

    SpaceName() {
        this.displayName = toTitleCase(name());
    }

    SpaceName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    private static String toTitleCase(String name) {
        StringBuilder sb = new StringBuilder();
        for (String part : name.split("_")) {
            sb.append(part.charAt(0))
                    .append(part.substring(1).toLowerCase())
                    .append(' ');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
