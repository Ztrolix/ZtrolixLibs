package com.ztrolix.zlibs.accessor;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AbstractClientPlayerEntityAccessor {
    void setChatText(List<String> text, int currentAge, int width, int height);

    @Nullable List<String> getChatText();

    int getOldAge();

    int getWidth();

    int getHeight();
}