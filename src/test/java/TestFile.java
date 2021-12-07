import FileUtils.ReadFile;
import FileUtils.StringUtils;
import LogToExcel.Log.Entity.Demo3D;
import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class TestFile {

    @Test
    public void test() {
        String[] list = {""};
        HashMap<String, String> result = StringUtils.getValue(list, "tst,1234,sdt,t,12,4353,s,5234,ss,34");
        for(String s : list){
            System.out.println(result.get(s));
        }
    }

    @Test
    public void test1(){
        String path = "C:\\Users\\Zsm\\Desktop";
        Log log = new Master("StatCSV", path);

        System.out.println(log);
    }
}
