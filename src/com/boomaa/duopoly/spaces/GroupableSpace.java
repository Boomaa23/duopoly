package com.boomaa.duopoly.spaces;

public interface GroupableSpace<T extends Space.GroupIdentifier> {
    T getGroupIdentifier();
}
