import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {
    public static <T> void writeFile(String  newfilename, List<T> Rows){
        if(Rows == null) return;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newfilename))){
            for(T t : Rows){
                bufferedWriter.write(t.toString() + "\n");
            }

            System.out.printf("The file located in the %s was created successfully",newfilename);
        } catch (IOException e) {
            System.out.println("Write failed. Please try again");
            e.printStackTrace();
        }
    }
}