//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.core.connection.database.io;

import pro.delfik.lmao.core.connection.database.LocalIO;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.lmao.core.connection.database.objects.DataList;

public class Writer {
    public Writer() {
    }

    public static void create(DataList list, String path) {
        if(ServerIO.isConnect()){
            ServerIO.create(list, path);
        }else{
            LocalIO.write(path, list);
        }
    }

    public static void remove(String path){
        if(ServerIO.isConnect()){
            ServerIO.remove(path);
        }else{
            LocalIO.remove(path);
        }
    }
}
