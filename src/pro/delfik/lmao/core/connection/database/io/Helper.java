package pro.delfik.lmao.core.connection.database.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helper {

    public static String toString(List<String> perms){
        if(perms == null)return null;
        StringBuilder sb = new StringBuilder();
        for (String perm : perms) {
            sb.append(perm);
            sb.append('}');
        }
        return sb.toString();
    }

    public static List<String> fromString(String perm){
        if(perm == null)return null;
        ArrayList<String> perms = new ArrayList<>();
        Collections.addAll(perms, perm.split("}"));
        return perms;
    }
}
