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

## Make the process faster

We could have made the process faster if more details on requirements can be procured:
- If it's always the top 5 results, we would not need to sort the result obtained and make one <i>O(n)</i> traversal to find them. This might be however, a bad idea in case we want to find top 100 among a million entries. We are sorting using TreeMap with our own Comparator, thus making each add and lookup operation succeed in <i>O(log n)</i> time.
- We could have spawned multiple threads based on the available memory. These multiple threads could have generated the map in a faster way by working on different parts of the file.

# References
http://www.baeldung.com/java-read-lines-large-file
