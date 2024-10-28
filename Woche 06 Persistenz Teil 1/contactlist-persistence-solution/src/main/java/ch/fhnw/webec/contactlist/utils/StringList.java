package ch.fhnw.webec.contactlist.utils;

import java.util.List;

public class StringList {

    public static List<String> removeBlankEntries(List<String> list) {
        if (list == null) return null;
        return list.stream().filter(x -> x != null && !x.isBlank()).toList();
    }

}
