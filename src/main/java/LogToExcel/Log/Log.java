package LogToExcel;

import FileUtils.ReadFile;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

abstract class Log {
    //总标题，设置成和读取的关键字相同
    private final String title;
    //列表的标题
    private final String[] header;
    //内容列表
    private List<LogText> texts;
    //文件路径
    public String path;
    public Log(String title, String[] header, String path) {
        this.title = title;
        this.header = header;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public String[] getHeader() {
        return header;
    }

    public List<LogText> getList() {
        return texts;
    }

    protected void setData(String filepath) {
        filepath = path + "\\" + filepath;
        texts = new LinkedList<>();

        List<String> file;
        try {
            file = ReadFile.readFile(filepath);
        } catch (FileNotFoundException e) {
            System.out.printf("%s文件不存在，请确定文件位置或者名称是否正确。%n", filepath);
            return;
        }

        for (String s : file) {
            LogText t = getDataByRules(s);
            if (t.getList().size() != 0) texts.add(t);//有可能读取到的这一条数据里没有需要的
        }
        Collections.sort(texts);
    }

    abstract LogText getDataByRules(String s);
}
