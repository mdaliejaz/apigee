# Update

Since there is a probability that the HashMap doesn't fit in the memory, I've changed the implementation to use MySQL database.<br \>
The file is read line by line and thus does not need a lot of memory. Each time a line is read, it's processed and then added to the database. If the IP already exists in the database, it's count is incremented by 1. If not, the new IP is inserted with a count of 1.

There are checks done to accomodate invalid IP. In case the IP is invalid, that line in the weblog is skipped and the user is notified of the skipped line. The processing continues from the next line onwards. I decided to continue the processing, as a few wrong (junk) lines would anyways not effect much on the overall top 'n' IPs count. This behaviour can be easily changed if required. A sample output for top 5 IP addresses for testFile3 (having junk lines) is shown below:
```
Could not parse IP in this line: '5:33	fake-ip	hi	400'. Skipping line and going ahead.
Could not parse IP in this line: '5:33	a.a.a.a	bye	501'. Skipping line and going ahead.
Could not parse IP in this line: ''. Skipping line and going ahead.
Could not parse IP in this line: ''. Skipping line and going ahead.
Could not parse IP in this line: ''. Skipping line and going ahead.
Could not parse IP in this line: 'swijw'. Skipping line and going ahead.
Could not parse IP in this line: ''. Skipping line and going ahead.
255.240.230.3
255.240.230.1
255.240.230.5
250.250.30.4
255.240.230.8
```

======

# Execution Step

// cd into src directory<br \>
Compile:<br \>
$ javac TopIP.java<br \>
Run with the parameters <i>java TopIP \<filename\> \<number of top IPs to view\></i>:<br \>
$ java TopIP ../testResources/testFile1 5<br \>
255.240.230.5<br \>
255.240.230.3<br \>
250.250.30.4<br \>
255.240.230.1<br \>
255.240.230.8<br \>

Or, you can run from IDE by setting up the parameters in edit options.

# Design Considerations

In the current implementation `java.util.Scanner` is used to handle large files; files larger than the available memory!

<i>java.util.Scanner</i> runs through the contents of the file and retrieve lines serially, one by one, thus avoiding the loading of entire file in memory.
```
Scanner scanner = new Scanner(new FileReader(largeFile));
while (scanner.hasNextLine()) {
  String line = scanner.nextLine();
  ...
}
scanner.close();

OR

FileInputStream inputStream = new FileInputStream(path);
sc = new Scanner(inputStream, "UTF-8");
while (sc.hasNextLine()) {
  String line = sc.nextLine();
  ...
}
if (inputStream != null) {
  inputStream.close();
}
if (sc != null) {
  sc.close();
}
```

We could have also chosen to stream file with Apache Commons IO:

```
LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
while (it.hasNext()) {
  String line = it.nextLine();
  ...
}
LineIterator.closeQuietly(it);

```

While either of the above approach is valid, the following typical way (standard way of reading the lines of the file in-memory) of file handling should be avoided (both Guava and Apache Commons IO):
```
Files.readLines(new File(path), Charsets.UTF_8);
OR
FileUtils.readLines(new File(path));
```

# Other design considerations done

## Use Map Reduce
We could have used Map Reduce concept (or simply divided the file into multiple small files and took the top 'n' from each of them). But if we go in this typical way, we might not achieve the desired result.
For instance, a division as following would have resulted in wrong result:
```
     File1 File2 File3
url1   5     0     5
url2   0     5     5
url3   5     5     0
url4   5     0     0
url5   0     5     0
url6   0     0     5
url7   4     4     4
```
However, if we divided the work in such a way that each section just fills the map parallely, adding to the bigger map, this could have been achieved.

## Read the file into a database

We could have read the file in a database and avoided the RAM usage, making the query from the permanent storage directly.

## Use Patricia Tree

We could use Patricia tree (a trie) to store the IP addresses, that is if space constraint doesn't even allow approximately 4.3 billion of fixed public IPv4 addresses to be stored in the HashMap.

## Make the process faster

We could have made the process faster if more details on requirements can be procured:
- If it's always the top 5 results, we would not need to sort the result obtained and make one <i>O(n)</i> traversal to find them. This might be however, a bad idea in case we want to find top 100 among a million entries. We are sorting using TreeMap with our own Comparator, thus making each add and lookup operation succeed in <i>O(log n)</i> time.
- We could have spawned multiple threads based on the available memory. These multiple threads could have generated the map in a faster way by working on different parts of the file.

# References
http://www.baeldung.com/java-read-lines-large-file
