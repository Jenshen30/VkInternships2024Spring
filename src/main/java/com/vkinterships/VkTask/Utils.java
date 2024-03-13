package com.vkinterships.VkTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {
    public static String mapToHttpParams(Map<String, String> mp) {
        if (mp.isEmpty()) {
            return "";
        }
        List<String> builder = new ArrayList<>();
        for (var entry : mp.entrySet()) {
            builder.add(entry.getKey() + "=" + entry.getValue());

        }
        return "?" + String.join("&", builder);
    }
}
