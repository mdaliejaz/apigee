import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopIP {
    public static void main(String[] args) {
        if (args.length == 2) {
            File largeFile = new File(args[0]);
            int topN = 5;
            try {
                topN = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Number Format Exception occurred. See Stacktrace below:");
                e.printStackTrace();
                System.out.println("Choosing to print top 5 and going ahead with the execution.");
            }
            TopIP topIP = new TopIP();
            SQLHelper sqlHelper = new SQLHelper();
            topIP.findTopIP(largeFile, sqlHelper);
            ArrayList<String> topIPObtained = sqlHelper.query(topN);
            for (String ip : topIPObtained) {
                System.out.println(ip);
            }
        } else {
            System.out.println("You must enter the filename to parse and the number of top IPs expected");
        }
    }

    public void findTopIP(File largeFile, SQLHelper sqlHelper) {
        try {
            Scanner scanner = new Scanner(new FileReader(largeFile));
            sqlHelper.deleteTable();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArray = line.trim().split("\t+");
                String ip;
                if (lineArray.length < 2) {
                    ip = "";
                } else {
                    ip = lineArray[1];
                }
                if (validateIP(ip)) {
                    sqlHelper.insert(ip);
                } else {
                    System.out.println(String.format("Could not parse IP in this line: '%s'. " +
                            "Skipping line and going ahead.", line));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean validateIP(String ip) {
        String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

//    private HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
//
//    public HashMap<String, Integer> getHashMap() {
//        return this.hashMap;
//    }

//    public static void main(String[] args) {
//        if (args.length == 2) {
//            File largeFile = new File(args[0]);
//            int topN = 5;
//            try {
//                topN = Integer.parseInt(args[1]);
//            } catch (NumberFormatException e) {
//                System.out.println("Number Format Exception occurred. See Stacktrace below:");
//                e.printStackTrace();
//                System.out.println("Choosing to print top 5 and going ahead with the execution.");
//            }
//            TopIP topIP = new TopIP();
//            TreeMap<String, Integer> topIPMap = topIP.findTopIP(largeFile);
//            int i = 1;
//            for (String IP : topIPMap.keySet()) {
//                System.out.println(IP);
//                if (i == topN) {
//                    break;
//                }
//                i++;
//            }
//        } else {
//            System.out.println("You must enter the filename to parse and the number of top IPs expected");
//        }
//    }

//    public TreeMap<String, Integer> findTopIP(File largeFile) {
//        try {
//            Scanner scanner = new Scanner(new FileReader(largeFile));
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] lineArray = line.trim().split("\t+");
//                String ip;
//                if (lineArray.length < 2) {
//                    ip = "";
//                } else {
//                    ip = lineArray[1];
//                }
//                if (validateIP(ip)) {
//                    if (hashMap.containsKey(ip)) {
//                        this.hashMap.put(ip, this.hashMap.get(ip) + 1);
//                    } else {
//                        this.hashMap.put(ip, 1);
//                    }
//                } else {
//                    System.out.println(String.format("Could not parse IP in this line: '%s'. " +
//                            "Skipping line and going ahead.", line));
//                }
//            }
//            scanner.close();
//            MyComparator myComparator = new MyComparator(getHashMap());
//            TreeMap sortedMap = new TreeMap(myComparator);
//            sortedMap.putAll(hashMap);
//            return sortedMap;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
}

//class MyComparator implements Comparator<Object> {
//    private HashMap<String, Integer> hashMap;
//
//    public MyComparator(HashMap<String, Integer> hashMap) {
//        this.hashMap = hashMap;
//    }
//
//    @Override
//    public int compare(Object o1, Object o2) {
//        if (hashMap.get(o1) < hashMap.get(o2)) {
//            return 1;
//        }
//        return -1;
//    }
//}
