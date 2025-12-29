package com.boomaa.duopoly;

import com.boomaa.duopoly.spaces.ChanceSpace;
import com.boomaa.duopoly.spaces.CommunityChestSpace;
import com.boomaa.duopoly.spaces.GoToJailSpace;
import com.boomaa.duopoly.spaces.GroupableSpace;
import com.boomaa.duopoly.spaces.JailSpace;
import com.boomaa.duopoly.spaces.PaymentSpace;
import com.boomaa.duopoly.spaces.PropertySpace;
import com.boomaa.duopoly.spaces.PropertySpace.Color;
import com.boomaa.duopoly.spaces.RailroadSpace;
import com.boomaa.duopoly.spaces.Space;
import com.boomaa.duopoly.spaces.UtilitySpace;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardManager {
    private static final List<Space> BOARD = List.of(
        new PaymentSpace(SpaceName.GO, 200),
        new PropertySpace(SpaceName.MEDITERRANEAN_AVENUE, 60, Color.BROWN, 2, 10, 30, 90, 160, 250),
        CommunityChestSpace.getInstance(),
        new PropertySpace(SpaceName.BALTIC_AVENUE, 60, Color.BROWN, 4, 20, 60, 180, 320, 450),
        new PaymentSpace(SpaceName.INCOME_TAX, -200),
        new RailroadSpace(SpaceName.READING_RAILROAD, 200),
        new PropertySpace(SpaceName.ORIENTAL_AVENUE, 100, Color.LIGHT_BLUE, 6, 30, 90, 270, 400, 550),
        ChanceSpace.getInstance(),
        new PropertySpace(SpaceName.VERMONT_AVENUE, 100, Color.LIGHT_BLUE, 6, 30, 90, 270, 400, 550),
        new PropertySpace(SpaceName.CONNECTICUT_AVENUE, 120, Color.LIGHT_BLUE, 8, 40, 100, 300, 450, 600),
        new JailSpace(),
        new PropertySpace(SpaceName.ST_CHARLES_PLACE, 140, Color.PURPLE, 10, 50, 150, 450, 625, 750),
        new UtilitySpace(SpaceName.ELECTRIC_COMPANY, 150),
        new PropertySpace(SpaceName.STATES_AVENUE, 140, Color.PURPLE, 10, 50, 150, 450, 625, 750),
        new PropertySpace(SpaceName.VIRGINIA_AVENUE, 160, Color.PURPLE, 12, 60, 180, 500, 700, 900),
        new RailroadSpace(SpaceName.PENNSYLVANIA_RAILROAD, 200),
        new PropertySpace(SpaceName.ST_JAMES_PLACE, 180, Color.ORANGE, 14, 70, 200, 550, 750, 950),
        CommunityChestSpace.getInstance(),
        new PropertySpace(SpaceName.TENNESSEE_AVENUE, 180, Color.ORANGE, 14, 70, 200, 550, 750, 950),
        new PropertySpace(SpaceName.NEW_YORK_AVENUE, 200, Color.ORANGE, 16, 80, 220, 600, 800, 1000),
        new PaymentSpace(SpaceName.FREE_PARKING, 0),
        new PropertySpace(SpaceName.KENTUCKY_AVENUE, 220, Color.RED, 18, 90, 250, 700, 875, 1050),
        ChanceSpace.getInstance(),
        new PropertySpace(SpaceName.INDIANA_AVENUE, 220, Color.RED, 18, 90, 250, 700, 875, 1050),
        new PropertySpace(SpaceName.ILLINOIS_AVENUE, 240, Color.RED, 20, 100, 300, 750, 925, 1100),
        new RailroadSpace(SpaceName.B_AND_O_RAILROAD, 200),
        new PropertySpace(SpaceName.ATLANTIC_AVENUE, 260, Color.YELLOW, 22, 110, 330, 800, 975, 1150),
        new PropertySpace(SpaceName.VENTNOR_AVENUE, 260, Color.YELLOW, 22, 110, 330, 800, 975, 1150),
        new UtilitySpace(SpaceName.WATER_WORKS, 150),
        new PropertySpace(SpaceName.MARVIN_GARDENS, 280, Color.YELLOW, 24, 120, 360, 850, 1025, 1200),
        new GoToJailSpace(),
        new PropertySpace(SpaceName.PACIFIC_AVENUE, 300, Color.GREEN, 26, 130, 390, 900, 1100, 1275),
        new PropertySpace(SpaceName.NORTH_CAROLINA_AVENUE, 300, Color.GREEN, 26, 130, 390, 900, 1100, 1275),
        CommunityChestSpace.getInstance(),
        new PropertySpace(SpaceName.PENNSYLVANIA_AVENUE, 320, Color.GREEN, 28, 150, 450, 1000, 1200, 1400),
        new RailroadSpace(SpaceName.SHORT_LINE, 200),
        ChanceSpace.getInstance(),
        new PropertySpace(SpaceName.PARK_PLACE, 350, Color.BLUE, 35, 175, 500, 1100, 1300, 1500),
        new PaymentSpace(SpaceName.LUXURY_TAX, -100),
        new PropertySpace(SpaceName.BOARDWALK, 400, Color.BLUE, 50, 200, 600, 1400, 1700, 2000)
    );
    private static final Map<SpaceName, Integer> nameToPositionMap;
    private static final Map<Space.GroupIdentifier, List<Integer>> groupToPositionsMap;
    private static final Map<Space.GroupIdentifier, List<GroupableSpace<?>>> groupToSpacesMap;

    static {
        nameToPositionMap = IntStream.range(0, BOARD.size())
                .boxed()
                .collect(Collectors.toMap(i -> BOARD.get(i).getName(), i -> i));
        groupToPositionsMap = IntStream.range(0, BOARD.size())
                .filter(i -> BOARD.get(i) instanceof GroupableSpace<?>)
                .boxed()
                .collect(
                        Collectors.groupingBy(
                            i -> ((GroupableSpace<?>) BOARD.get(i)).getGroupIdentifier(),
                            Collectors.mapping(i -> i, Collectors.toList())
                        )
                );
        groupToSpacesMap = BOARD.stream()
                .filter(spc -> spc instanceof GroupableSpace<?>)
                .map(spc -> (GroupableSpace<?>) spc)
                .collect(
                        Collectors.groupingBy(
                            GroupableSpace::getGroupIdentifier,
                            Collectors.mapping(spc -> spc, Collectors.toList())
                        )
                );
    }

    private BoardManager() {
    }

    public static int getNumSpaces() {
        return BOARD.size();
    }

    public static Space positionToSpace(int position) {
        return BOARD.get(position % BOARD.size());
    }

    public static int spaceNameToPosition(SpaceName name) {
        if (!nameToPositionMap.containsKey(name)) {
            throw new NoSuchElementException("No space on board named " + name);
        }
        return nameToPositionMap.get(name);
    }

    public static Space spaceNameToSpace(SpaceName name) {
        return BOARD.get(spaceNameToPosition(name));
    }

    public static List<Integer> groupToPositions(Space.GroupIdentifier gid) {
        if (!groupToPositionsMap.containsKey(gid)) {
            throw new NoSuchElementException("No group on board named " + gid);
        }
        return groupToPositionsMap.get(gid);
    }

    public static List<GroupableSpace<?>> groupToSpaces(Space.GroupIdentifier gid) {
        if (!groupToSpacesMap.containsKey(gid)) {
            throw new NoSuchElementException("No group on board named " + gid);
        }
        return groupToSpacesMap.get(gid);
    }
}
