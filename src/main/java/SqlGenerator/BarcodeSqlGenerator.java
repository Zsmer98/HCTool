package SqlGenerator;

import Utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class BarcodeSqlGenerator {
    private final String Dsi_waybill_no;
    private final int Dsi_cde_destcode;
    private final String Dsi_updatetime;
    private final String Dsi_createtime;
    private final String Dsi_modtime;

    @Override
    public String toString() {
        return "INSERT INTO scc.dsi_sortinginfo (dsi_waybill_no, dsi_cde_destcode,dsi_updatetime,dsi_createtime,dsi_modtime) VALUES ('" +
                Dsi_waybill_no + "', '" +
                "D" + Dsi_cde_destcode + "', " +
                Dsi_updatetime + ", " +
                Dsi_createtime + ", " +
                Dsi_modtime + ");";
    }

    public BarcodeSqlGenerator(String Dsi_waybill_no, int Dsi_cde_destcode) {
        this.Dsi_waybill_no = Dsi_waybill_no;
        this.Dsi_cde_destcode = Dsi_cde_destcode;
        this.Dsi_updatetime = "1635926698.602";
        this.Dsi_createtime = "1635926698.602";
        this.Dsi_modtime = "1635926698.602";
    }


    public static String newFilePath(String oldpath) {
        if (oldpath == null) return null;

        String[] strs = oldpath.split("\\.");
        StringBuilder sb = new StringBuilder(strs[0]);
        for (int i = 1; i < strs.length - 1; ++i) {
            sb.append(".").append(strs[i]);
        }

        sb.append("-SQL.").append(strs[strs.length - 1]);
        return sb.toString();
    }

    public static void main(String[] args) {
        String oldpath = FileUtils.getPath("请输入文件路径");
        List<String> data = null;
        try {
            data = FileUtils.readFile(oldpath);
        } catch (FileNotFoundException e) {
            System.out.printf("%s文件不存在，请确定文件位置或者名称是否正确。", oldpath);
        }

        //Processing data
        if (data == null) return;
        List<BarcodeSqlGenerator> list = new LinkedList<>();
        for (int i = 0; i < data.size(); ++i) {
            list.add(new BarcodeSqlGenerator(data.get(i), i));
        }

        FileUtils.writeFile(newFilePath(oldpath), list);
    }
}