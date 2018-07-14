//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.core.connection.database.objects;

public class DataObject {
    private String value;

    public DataObject(String value) {
        this.value = value.equals("\u1234") ? "" : value.equals("") ? "\u1234" : value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
