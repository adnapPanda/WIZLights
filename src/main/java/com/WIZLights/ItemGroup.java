package com.WIZLights;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemGroup {
    NONE("No Group"),
    ONE("Group 1"),
    TWO("Group 2"),
    THREE("Group 3"),
    FOUR("Group 4");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
