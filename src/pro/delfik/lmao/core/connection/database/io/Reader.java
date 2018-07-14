//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.core.connection.database.io;

import pro.delfik.lmao.core.connection.database.LocalIO;
import pro.delfik.lmao.core.connection.database.ServerIO;
import pro.delfik.lmao.core.connection.database.objects.DataList;

public class Reader {
    public Reader() {
    }

    public static DataList read(String path) {
        if(ServerIO.isConnect()){
            return ServerIO.read(path);
        }else{
            return LocalIO.read(path);
        }
    }

    public static DataList getAll(String path){
        if(ServerIO.isConnect()){
            return ServerIO.getAll(path);
        }else{
            return LocalIO.getAll(path);
        }
    }
}
