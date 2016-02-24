import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

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
            TreeMap<String, Integer> topIPMap = topIP.findTopIP(largeFile);
            for (String IP : topIPMap.keySet()) {
                System.out.println(IP);
            }
        } else {
            System.out.println("You must enter the filename to parse and the number of top IPs expected");
        }
    }

    public TreeMap<String, Integer> findTopIP(File largeFile) {
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
            MyComparator myComparator = new MyComparator(getHashMap());
            TreeMap sortedMap = new TreeMap(myComparator);
            sortedMap.putAll(hashMap);
            return sortedMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}

class MyComparator implements Comparator<Object> {
    private HashMap<String, Integer> hashMap;

    public MyComparator(HashMap<String, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (hashMap.get(o1) < hashMap.get(o2)) {
            return 1;
        }
        return -1;
    }
}
