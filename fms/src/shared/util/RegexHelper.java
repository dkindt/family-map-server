package shared.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexHelper {

    public static Map<String, String> search(String pattern, String input) {

        String[] groupNames = getGroupNames(pattern);
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        matcher.find();

        Map<String, String> match = new LinkedHashMap<>();
        for (String name : groupNames) {
            match.put(name, matcher.group(name));
        }
        return match;
    }

    private static String[] getGroupNames(String pattern) {

        final String p = "\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>";
        final Pattern regex = Pattern.compile(p);
        final Matcher matcher = regex.matcher(pattern);

        List<String> names = new ArrayList<>();
        while (matcher.find()) {
            names.add(matcher.group(1));
        }
        String[] result = new String[names.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = names.get(i);
        }
        return result;
    }
}
