//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pro.delfik.lmao.core.connection.database.objects;

public class DataList {
    private String[] keys;
    private DataObject[] values;

    public DataList(int length) {
        this.keys = new String[length];
        this.values = new DataObject[length];
    }

    public DataList(){
        this(1);
    }

    public DataObject get(String key) {
        if (key == null) {
            return null;
        } else {
            int i;
            for(i = 0; i < this.keys.length; i++) {
                if(this.keys[i] == null)return null;
                if(this.keys[i].equals(key))break;
                if (i == this.keys.length - 1) {
                    return null;
                }
            }

            return this.values[i];
        }
    }

    public void remove(String key) {
        int i;
        for(i = 0; i < this.keys.length && !key.equals(this.keys[i]); ++i) {
            if (i == this.keys.length - 1) {
                return;
            }
        }

        this.keys[i] = null;
        this.values[i] = null;
    }

    public void put(String key, DataObject object) {
        int i2 = this.keys.length;

        int i;
        for(i = 0; i < i2 && this.keys[i] != null; ++i) {
            if (this.keys[i].equals(key)) {
                this.values[i] = object;
                return;
            }

            if (i2 - 1 == i) {
                this.addLength(1);
                ++i2;
            }
        }

        this.keys[i] = key;
        this.values[i] = object;
    }

    public void addLength(int length) {
        String[] keys = new String[this.keys.length + length];
        DataObject[] values = new DataObject[this.keys.length + length];

        for(int i = 0; i < this.keys.length; ++i) {
            keys[i] = this.keys[i];
            values[i] = this.values[i];
        }

        this.keys = keys;
        this.values = values;
    }

    public DataObject[] getAllValues() {
        return this.values;
    }

    public String[] getAllKeys() {
        return this.keys;
    }
}
