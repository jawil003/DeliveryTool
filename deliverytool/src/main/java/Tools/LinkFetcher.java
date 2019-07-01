/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Tools;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class LinkFetcher {

    public static String normalizePath(String path, String contains) {
        String pathBackup = path;
        if (path.contains(contains)) {
            path = path.replaceFirst(contains, "");
        }

        if (path.contains(contains)) {
            return path;
        }
        return pathBackup;
    }
}
