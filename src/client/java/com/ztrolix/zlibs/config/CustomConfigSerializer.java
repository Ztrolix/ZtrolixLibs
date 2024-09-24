package com.ztrolix.zlibs.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class CustomConfigSerializer extends GsonConfigSerializer<ZLibsConfig> {
    public CustomConfigSerializer(Config config, Class<ZLibsConfig> type) {
        super(config, type);
    }
}