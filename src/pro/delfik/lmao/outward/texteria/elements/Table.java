/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 */
package pro.delfik.lmao.outward.texteria.elements;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import pro.delfik.lmao.outward.texteria.utils.ByteMap;

import java.util.LinkedList;
import java.util.List;

public class Table
extends Element<Table> {
    protected List<Column> columns = new LinkedList<>();
    public List<String[]> rows = new LinkedList<>();
    protected int titleColor = -769226;
    protected String title = null;
    protected int headingColor = -5317;
    protected boolean drawBack = false;
    protected int maxRows = -1;
    protected int scrollbarColor = -5317;
    protected int rowHoverColor = 1140946643;

    public Table(String id) {
        super(id);
    }

    public Table addColumn(Column column) {
        this.columns.add(column);
        return this;
    }

    public /* varargs */ Table addRow(String ... row) {
        if (row[0] == null) {
            return this;
        }
        if (StringUtils.isBlank(row[0])) {
            return this;
        }
        if (row.length != this.columns.size()) {
            throw new IllegalArgumentException("Row length must be equal to columns amount (" + row.length + " != " + this.columns.size() + ")");
        }
        for (int i = 0; i < row.length; ++i) {
            row[i] = row[i] == null ? "" : ChatColor.translateAlternateColorCodes('&', row[i]);
        }
        this.rows.add(row);
        return this;
    }

    public Table setTitle(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        return this;
    }

    public Table setDrawBack(boolean drawBack) {
        this.drawBack = drawBack;
        return this;
    }

    public List<String[]> getRows() {
        return this.rows;
    }

    public String[] getRow(int index) {
        return this.rows.get(index);
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public Column getColumn(int index) {
        return this.columns.get(index);
    }

    public int getTitleColor() {
        return this.titleColor;
    }

    public Table setTitleColor(int color) {
        this.titleColor = color;
        return this;
    }

    public int getHeadingColor() {
        return this.headingColor;
    }

    public Table setHeadingColor(int color) {
        this.headingColor = color;
        return this;
    }

    public int getMaxRows() {
        return this.maxRows;
    }

    public Table setMaxRows(int maxRows) {
        this.maxRows = maxRows;
        return this;
    }

    public int getRowHoverColor() {
        return this.rowHoverColor;
    }

    public Table setRowHoverColor(int color) {
        this.rowHoverColor = color;
        return this;
    }

    public int getScrollbarColor() {
        return this.scrollbarColor;
    }

    public Table setScrollbarColor(int color) {
        this.scrollbarColor = color;
        return this;
    }

    @Override
    public void write(ByteMap map) {
        ByteMap cols = new ByteMap();
        cols.put("size", this.columns.size());
        int id = 0;
        for (Column column : this.columns) {
            cols.put("" + id + ".n", column.name);
            cols.put("" + id + ".w", column.width);
            if (column.center) {
                cols.put("" + id + ".c", true);
            }
            if (column.color != -1) {
                cols.put("" + id + ".t", column.color);
            }
            ++id;
        }
        map.put("cols", cols);
        ByteMap rows = new ByteMap();
        id = 0;
        for (String[] row : this.rows) {
            rows.put("" + id++ + "", row);
        }
        map.put("rows", rows);
        if (this.title != null) {
            map.put("title", this.title);
        }
        if (this.titleColor != -5317) {
            map.put("title.c", this.titleColor);
        }
        if (this.headingColor != -5317) {
            map.put("heading.c", this.headingColor);
        }
        if (this.drawBack) {
            map.put("drawBack", true);
        }
        if (this.maxRows != -1) {
            map.put("maxRows", this.maxRows);
        }
        if (this.scrollbarColor != -5317) {
            map.put("sb.c", this.scrollbarColor);
        }
        if (this.rowHoverColor != 1140946643) {
            map.put("rh.c", this.rowHoverColor);
        }
        super.write(map);
    }

    @Override
    protected String getType() {
        return "Table";
    }

    public static class Column {
        public String name;
        public int width;
        public boolean center = false;
        public int color = -1;

        public Column(String name, int width) {
            this.name = ChatColor.translateAlternateColorCodes('&', name);
            this.width = width;
        }

        public Column setCenter(boolean flag) {
            this.center = flag;
            return this;
        }

        public Column setColor(int color) {
            this.color = color;
            return this;
        }
    }

}

