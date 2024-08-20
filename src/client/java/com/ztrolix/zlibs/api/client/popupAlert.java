package com.ztrolix.zlibs.api.client;

import com.ztrolix.zlibs.config.ZLibsConfig;
import me.shedaniel.autoconfig.AutoConfig;

import javax.swing.*;

public class popupAlert {
    public static void show(String Title, String Text) {
        ZLibsConfig config = AutoConfig.getConfigHolder(ZLibsConfig.class).getConfig();

        JOptionPane.showMessageDialog(null, Text, Title, JOptionPane.INFORMATION_MESSAGE);
    }
}