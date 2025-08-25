package in.ac.iitd.db362.index.hashindex;

import in.ac.iitd.db362.index.Index;
import in.ac.iitd.db362.parser.QueryNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Starter code for Extendible Hashing
 * @param <T> The type of the key.
 */
public class ExtendibleHashing<T> implements Index<T> {

    protected static final Logger logger = LogManager.getLogger();

    private final Class<T> type;

    private String attribute; // attribute that we are indexing

    // Note: Do not rename the variable! You can initialize it to a different value for testing your code.
    public static int INITIAL_GLOBAL_DEPTH = 2;


    // Note: Do not rename the variable! You can initialize it to a different value for testing your code.
    public static int BUCKET_SIZE = 3;

    private int globalDepth;

    // directory is the bucket address table backed by an array of bucket pointers
    // the array offset (can be computed using the provided hashing scheme) allows accessing the bucket
    private Bucket<T>[] directory;


    /** Constructor */
    @SuppressWarnings("unchecked")
    public ExtendibleHashing(Class<T> type, String attribute) {
        this.type = type;
        this.globalDepth = INITIAL_GLOBAL_DEPTH;
        int directorySize = 1 << globalDepth;
        this.directory = new Bucket[directorySize];
        for (int i = 0; i < directorySize; i++) {
            directory[i] = new Bucket<>(globalDepth);
        }
        this.attribute = attribute;
    }

    private T castValueFromString(Object value, Class<T> targetType) {

        if (targetType.equals(Integer.class)) {
            return targetType.cast(Integer.valueOf((String) value));
        } else if (targetType.equals(Double.class)) {
            return targetType.cast(Double.valueOf((String) value));
        }else if(targetType.equals(LocalDate.class)){
            return targetType.cast(LocalDate.parse((String) value));
        }
        //return string if the type is of string
        return targetType.cast(value);
    }

    @Override
    public List<Integer> evaluate(QueryNode node) {
        //type casting the value since value is in string
        return search(castValueFromString(node.value, type));
    }


    @Override
    public void insert(T key, int rowId) {
        int bucketIndex =  computeBucketIndex(key,globalDepth);
        Bucket<T> bucket = directory[bucketIndex];


        while (bucket.size >= BUCKET_SIZE) {//while bucket is full
            boolean allEqual=true;
            int i = bucket.size - 1;
            while(i>=0){
                if(!key.equals(bucket.keys[i])){
                    allEqual= false;
                    break;
                }
                i--;
            }
            if (allEqual) {
                if (bucket.next != null) {

                    //To reach last overflow bucket
                    Bucket<T> currBucket;
                    for (currBucket = bucket.next; currBucket.next != null; ) {
                        currBucket = currBucket.next;
                    }

                    if (currBucket.size == BUCKET_SIZE) {
                        //when bucket is full create a new overflow bucket
                        currBucket.next = new Bucket<>(bucket.localDepth);
                        currBucket.next.keys[0] = key;
                        currBucket.next.values[0] = rowId;
                        currBucket.next.size++;
                    } else {
                        currBucket.keys[currBucket.size] = key;
                        currBucket.values[currBucket.size] = rowId;
                        currBucket.size++;
                    }

                } else {
                    // create a new overflow bucket
                    bucket.next = new Bucket<>(bucket.localDepth);
                    bucket.next.keys[0] = key;
                    bucket.next.values[0] = rowId;
                    bucket.next.size ++;
                }
                return;
            }
            if (bucket.localDepth == globalDepth)  // double the directory size if local depth = global depth
                increaseGlobalDepth();

            splitBucket(bucketIndex, bucket);
            bucketIndex = computeBucketIndex(key,globalDepth);
            bucket = directory[bucketIndex];
        }
        bucket.keys[bucket.size] = key;
        bucket.values[bucket.size] = rowId;
        bucket.size++;
    }


    private void splitBucket(int index, Bucket<T> currentBucket) {

        int localDepth = currentBucket.localDepth;

        // init a split currentBucket
        int newLocalDepth = localDepth + 1;
        Bucket<T> splitBucket = new Bucket<>(newLocalDepth);
        currentBucket.localDepth = newLocalDepth;


        //need one of the index to check which pointer to sperate the bucket from to point at the new bucket
        int corIdx = index ^ (1 << globalDepth-1); //finds the mirror index which also points to the bucket
        int i=directory.length-1;
        while(i>=0) {
            if((i & ((1 << newLocalDepth) - 1))== (corIdx & ((1 << newLocalDepth) - 1))) { // check the last newLocalDepth bits are the same
                directory[i] = splitBucket;
            }
            i--;
        }


        //there are no overflow buckets
        if(currentBucket.next ==null) {
            //
            for ( i = 0; i < currentBucket.size; i++) {
                int newIndex =  computeBucketIndex(currentBucket.keys[i],globalDepth);
                if ((newIndex & ((1 << newLocalDepth) - 1)) == (corIdx & ((1 << newLocalDepth) - 1))) {
                    splitBucket.keys[splitBucket.size] = currentBucket.keys[i];
                    splitBucket.values[splitBucket.size] = currentBucket.values[i];
                    splitBucket.size++;
                    currentBucket.keys[i] = currentBucket.keys[currentBucket.size - 1];
                    currentBucket.values[i] = currentBucket.values[currentBucket.size - 1];
                    currentBucket.size--;
                    i--;
                }
            }

        } else {
            int newIndex=  computeBucketIndex(currentBucket.keys[0],globalDepth);
            if((newIndex & ((1 << newLocalDepth) - 1))== (corIdx & ((1 << newLocalDepth) - 1))) {
                i = currentBucket.size-1;
                while(i>=0){
                    splitBucket.keys[splitBucket.size] = currentBucket.keys[i];
                    splitBucket.values[splitBucket.size] = currentBucket.values[i];
                    splitBucket.size++;
                    i--;
                }

                splitBucket.next =  currentBucket.next;
                currentBucket.size=0;
                currentBucket.next= null;
            }
        }


    }


    @Override
    public boolean delete(T key) {
        // TODO: (Bonus) Implement deletion logic with bucket merging and/or shrinking the address table
        return false;
    }

    @Override
    public List<Integer> search(T key) {

        Bucket<T> bucket = directory[computeBucketIndex(key,globalDepth)];
        List<Integer> results = new ArrayList<>();

        for (Bucket<T> current = bucket; current != null; current = current.next) {
            for (int i = 0; i < current.size; i++) {
                if (key.equals(current.keys[i])) {
                    results.add(current.values[i]);
                }
            }
        }
        results.sort(Comparator.naturalOrder());
        return results;
    }


    /**
     * Helper Function
     */


    public int computeBucketIndex(T key, int d) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (key instanceof Double) {
            return HashingScheme.getDirectoryIndex((Double) key, d);
        }
        else if (key instanceof Integer) {
            return  HashingScheme.getDirectoryIndex((Integer) key, d);
        }
        else if (key instanceof String) {
            return  HashingScheme.getDirectoryIndex((String) key, d);
        }
        else if (key instanceof LocalDate) {
            return HashingScheme.getDirectoryIndex((LocalDate) key, d);
        }
        else
            return -1;
    }

    private void increaseGlobalDepth() {
        int currentDirSize = directory.length;
        int newSize = currentDirSize << 1;
        Bucket<T>[] newDirectory = new Bucket[newSize];

        // Copy existing buckets to new directory
        int i =directory.length-1;
        while(i>=0){
            newDirectory[i] = directory[i];
            newDirectory[i + currentDirSize] = directory[i];
            i--;
        }

        directory = newDirectory;
        globalDepth++;
    }


    /**
     * Note: Do not remove this function!
     * @return
     */
    public int getGlobalDepth() {
        return globalDepth;
    }

    /**
     * Note: Do not remove this function!
     * @param bucketId
     * @return
     */
    public int getLocalDepth(int bucketId) {
        return directory[bucketId].localDepth;
    }

    /**
     * Note: Do not remove this function!
     * @return
     */
    public int getBucketCount() {
        return directory.length;
    }


    /**
     * Note: Do not remove this function!
     * @return
     */
    public Bucket<T>[] getBuckets() {
        return directory;
    }

    public void printTable() {

        System.out.println("global_depth= " + globalDepth);
        for (int i = 0; i < directory.length; i++) {
            StringBuilder bucketInfo = new StringBuilder(" = Bucket = " + i + " , local_depth= " + directory[i].localDepth);
            Bucket<T> bucket = directory[i];
            while (bucket != null) {
                bucketInfo.append("{");
                for (int j = 0; j < bucket.size; j++) {
                    bucketInfo.append("(").append(bucket.keys[j]).append(", ").append(bucket.values[j]).append(") ");
                }
                bucketInfo.append("}");
                bucket = bucket.next;
            }
            System.out.println(bucketInfo);
        }
    }

    @Override
    public String prettyName() {
        return "Hash Index";
    }

}