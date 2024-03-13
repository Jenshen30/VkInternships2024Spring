package com.vkinterships.VkTask;

import java.util.Map;

public class Utils {
    public static String mapToHttpParams(Map<String, String> mp) {
        if (mp.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder("?");
        for (var entry : mp.entrySet()) {
            builder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());

        }
        return builder.toString();
    }
}
