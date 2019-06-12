/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Tools;

public class LinkFetcher {

    public static String normalizePath(String path, String contains) {
        String pathBackup = path;
        if (path.contains(contains)) {
            path = path.toLowerCase().replaceFirst(contains, "");
        }

        if (path.contains(contains)) {
            return path;
        }
        return pathBackup;
    }
}
