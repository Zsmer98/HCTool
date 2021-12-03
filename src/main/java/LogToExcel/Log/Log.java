package LogToExcel.Log;

import FileUtils.ReadFile;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public abstract class Log {
    //总标题，设置成和读取的关键字相同
    private final String key;
    //列表的标题
    private final String[] header;
    //内容列表
    private List<LogText> texts;
    //文件路径
    public String path;

    public Log(String key, String[] header, String path) {
        this.key = key;
        this.header = header;
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public String[] getHeader() {
        return header;
    }

    public List<LogText> getList() {
        return texts;
    }

    protected void setData(String filepath) {
        filepath = path + filepath;
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

        //对得到的数据自定义排序
        texts.sort(compare());
    }

    abstract public LogText getDataByRules(String s);

    abstract public Comparator<LogText> compare();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LogText text : texts) {
            sb.append(text.toString()).append("\n");
        }
        return sb.toString();
    }
}
