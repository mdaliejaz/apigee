import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by ali on 2/24/16.
 */
public class TopIP {
    private HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

    public HashMap<String, Integer> getHashMap() {
        return this.hashMap;
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            File largeFile = new File(args[0]);
            TopIP topIP = new TopIP();
            HashMap<String, Integer> topIPMap = topIP.findTopIP(largeFile);
            for (String IP : topIPMap.keySet()) {
                System.out.println(IP);
            }
        } else {
            System.out.println("You must enter the filename to parse and the number of top IPs expected");
        }
    }

    public HashMap<String, Integer> findTopIP(File largeFile) {
        try {
            Scanner scanner = new Scanner(new FileReader(largeFile));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String ip = line.trim().split("\t+")[1];
                if (hashMap.containsKey(ip)) {
                    this.hashMap.put(ip, this.hashMap.get(ip) + 1);
                } else {
                    this.hashMap.put(ip, 1);
                }
            }
            scanner.close();
            System.out.println(Arrays.asList(hashMap));
            return hashMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
