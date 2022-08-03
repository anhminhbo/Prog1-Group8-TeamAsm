package Repo;
import java.io.*;

public class RepoService {
    public static BufferedReader readFile(String path) throws IOException {
        BufferedReader temp = new BufferedReader(new FileReader(path));
        temp.close();
        return temp;
    }
    public static void writeFile(String path, String data) throws IOException {
        BufferedWriter temp = new BufferedWriter(new FileWriter(path, true));
        temp.newLine();
        temp.write(data);
        temp.close();
    }
}
