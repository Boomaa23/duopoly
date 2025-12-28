package com.boomaa.duopoly.spaces;

public class Space {
    private final String name;

    public Space(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public interface GroupIdentifier {
    }
}
