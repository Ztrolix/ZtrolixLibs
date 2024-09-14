package com.ztrolix.zlibs.mixin.client;

import com.ztrolix.zlibs.accessor.AbstractClientPlayerEntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin implements AbstractClientPlayerEntityAccessor {
    @Unique
    @Nullable
    private List<String> chatTextList = null;

    @Unique
    private int oldAge = 0;

    @Unique
    private int width;

    @Unique
    private int height;

    @Unique
    @Override
    public void setChatText(List<String> textList, int currentAge, int width, int height) {
        this.chatTextList = textList;
        this.oldAge = currentAge;
        this.width = width;
        this.height = height;
    }

    @Unique
    @Nullable
    @Override
    public List<String> getChatText() {
        return this.chatTextList;
    }

    @Unique
    @Override
    public int getOldAge() {
        return this.oldAge;
    }

    @Unique
    @Override
    public int getWidth() {
        return this.width;
    }

    @Unique
    @Override
    public int getHeight() {
        return this.height;
    }
}