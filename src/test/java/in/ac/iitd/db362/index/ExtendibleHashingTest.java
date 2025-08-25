package in.ac.iitd.db362.index;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import in.ac.iitd.db362.index.hashindex.ExtendibleHashing;

public class ExtendibleHashingTest {

    @Test
    public void testInsert() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");
        hashIndex.insert(10, 1);
        hashIndex.insert(20, 2);
        hashIndex.insert(30, 3);

        List<Integer> results = hashIndex.search(10);
        assertNotNull(results);
        assertTrue(results.contains(1));

        // Test with double values
        ExtendibleHashing<Double> doubleHashIndex = new ExtendibleHashing<>(Double.class, "doubleColumn");
        doubleHashIndex.insert(10.5, 1);
        doubleHashIndex.insert(20.5, 2);
        doubleHashIndex.insert(30.5, 3);

        List<Integer> doubleResults = doubleHashIndex.search(10.5);
        doubleHashIndex.getBuckets();
        assertNotNull(doubleResults);
        assertTrue(doubleResults.contains(1));

        // Test with String values
        ExtendibleHashing<String> stringHashIndex = new ExtendibleHashing<>(String.class, "stringColumn");
        stringHashIndex.insert("Alpha", 1);
        stringHashIndex.insert("Beta", 2);
        stringHashIndex.insert("Gamma", 3);

        List<Integer> stringResults = stringHashIndex.search("Alpha");
        assertNotNull(stringResults);
        assertTrue(stringResults.contains(1));

        // Test with LocalDate values
        ExtendibleHashing<LocalDate> dateHashIndex = new ExtendibleHashing<>(LocalDate.class, "dateColumn");
        dateHashIndex.insert(LocalDate.of(2023, 1, 1), 1);
        dateHashIndex.insert(LocalDate.of(2023, 2, 1), 2);
        dateHashIndex.insert(LocalDate.of(2023, 3, 1), 3);

        List<Integer> dateResults = dateHashIndex.search(LocalDate.of(2023, 1, 1));
        assertNotNull(dateResults);
        assertTrue(dateResults.contains(1));

    }

    @Test
    public void testInsertDuplicatesOverflow() {
        /*
         *  THIS WORKS ONLY WHEN BUCKET SIZE IS 3!!!
         */
        ExtendibleHashing<String> hashIndex = new ExtendibleHashing<>(String.class,"columnName");
        hashIndex.insert("Biology", 1);
        hashIndex.insert("Music", 2);
        hashIndex.insert("Biology", 3);
        hashIndex.insert("Biology", 4);
        hashIndex.insert("Biology", 5);
        hashIndex.insert("Biology", 6);
        hashIndex.insert("Biology", 7);
        hashIndex.insert("Biology", 8);
        hashIndex.insert("Biology", 9);

        assertEquals(3, hashIndex.getBuckets()[13].getSize());
        assertEquals(1, hashIndex.getBuckets()[5].getSize());
        assertEquals(3, hashIndex.getBuckets()[13].getNext().getSize());
        assertEquals(2, hashIndex.getBuckets()[13].getNext().getNext().getSize());


        List<Integer> results = hashIndex.search("Biology");
        assertEquals(8,results.size());

        List<Integer> results2 = hashIndex.search("Music");
        assertEquals(1,results2.size());

    }
    @Test
    public void testInsertTestSplittingOverflowBuckets() {
        ExtendibleHashing<String> hashIndex = new ExtendibleHashing<>(String.class,"columnName");
        hashIndex.insert("Biology", 1);
        hashIndex.insert("Biology", 2);
        hashIndex.insert("Biology", 3);
        hashIndex.insert("Biology", 4);
        hashIndex.insert("Biology", 5);
        hashIndex.insert("Biology", 6);
        hashIndex.insert("Biology", 7);
        hashIndex.insert("Biology", 8);
        hashIndex.insert("Music", 9);

        assertEquals(3, hashIndex.getBuckets()[13].getSize());
        assertEquals(1, hashIndex.getBuckets()[5].getSize());
        assertEquals(3, hashIndex.getBuckets()[13].getNext().getSize());
        assertEquals(2, hashIndex.getBuckets()[13].getNext().getNext().getSize());

        List<Integer> results = hashIndex.search("Biology");
        assertEquals(8,results.size());

        List<Integer> results2 = hashIndex.search("Music");
        assertEquals(1,results2.size());

        List<Integer> results3 = hashIndex.search("Physics");
        assertEquals(0,results3.size());

    }

    @Test
    public void testInsertComplex() {
        /*
         *  THIS WORKS ONLY WHEN BUCKET SIZE IS 3!!!
         */
        ExtendibleHashing<String> hashIndex = new ExtendibleHashing<>(String.class, "columnName");
        hashIndex.insert("Electrical", 1);
        hashIndex.insert("Finance", 2);
        hashIndex.insert("History", 3);
        hashIndex.insert("Music", 4);
        hashIndex.insert("Biology", 5);
        hashIndex.insert("Computer", 6);
        hashIndex.insert("Physics", 7);
        hashIndex.insert("Computer", 8);
        hashIndex.insert("Physics", 9);
        assertEquals(3, hashIndex.getGlobalDepth());
        hashIndex.insert("Computer", 10);
        hashIndex.insert("Computer", 11);
        assertNotNull(hashIndex.getBuckets()[3].getNext());

        hashIndex.insert("Music", 12);
        hashIndex.insert("Biology", 13);
        assertEquals(4, hashIndex.getGlobalDepth());
        assertNotNull(hashIndex.getBuckets()[11].getNext());

        List<Integer> results = hashIndex.search("Biology");
        assertEquals(2, results.size());
        assertTrue(results.contains(5));
        assertTrue(results.contains(13));

        List<Integer> results2 = hashIndex.search("Music");
        assertEquals(2, results2.size());
        assertTrue(results2.contains(4));
        assertTrue(results2.contains(12));

        List<Integer> results3 = hashIndex.search("History");
        assertEquals(1, results3.size());
        assertTrue(results3.contains(3));

        List<Integer> results4 = hashIndex.search("Finance");
        assertEquals(1, results4.size());
        assertTrue(results4.contains(2));


        List<Integer> results5 = hashIndex.search("Electrical");
        assertEquals(1, results5.size());
        assertTrue(results5.contains(1));


        List<Integer> results6 = hashIndex.search("Physics");
        assertEquals(2, results6.size());
        assertTrue(results6.contains(7));
        assertTrue(results6.contains(9));

        List<Integer> results7 = hashIndex.search("Computer");
        assertEquals(4, results7.size());
        assertTrue(results7.contains(6));
        assertTrue(results7.contains(8));
        assertTrue(results7.contains(10));
        assertTrue(results7.contains(11));

    }

    @Test
    public void testSearchWithOverflow() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");
        hashIndex.insert(1, 10);
        hashIndex.insert(1, 20);
        hashIndex.insert(1, 30);
        hashIndex.insert(1, 40); // Causes overflow

        List<Integer> results = hashIndex.search(1);

        assertEquals(4, results.size());
        assertTrue(results.contains(10));
        assertTrue(results.contains(20));
        assertTrue(results.contains(30));
        assertTrue(results.contains(40));
    }

    @Test
    public void testSearchNonExistentKey() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");
        hashIndex.insert(5, 10);
        hashIndex.insert(10, 20);
        hashIndex.insert(15, 30);

        List<Integer> results = hashIndex.search(20);

        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchEdgeCases() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");
        hashIndex.insert(Integer.MIN_VALUE, 1);
        hashIndex.insert(Integer.MAX_VALUE, 2);
        hashIndex.insert(0, 3);

        // Edge case: Min value
        List<Integer> resultsMin = hashIndex.search(Integer.MIN_VALUE);
        assertEquals(1, resultsMin.size());
        assertTrue(resultsMin.contains(1));

        // Edge case: Max value
        List<Integer> resultsMax = hashIndex.search(Integer.MAX_VALUE);
        assertEquals(1, resultsMax.size());
        assertTrue(resultsMax.contains(2));

        // Edge case: Zero value
        List<Integer> resultsZero = hashIndex.search(0);
        assertEquals(1, resultsZero.size());
        assertTrue(resultsZero.contains(3));
    }

    @Test
    public void testBucketSplit() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");

        // Insert keys that will fill up the bucket and cause a split
        hashIndex.insert(5, 1);
        hashIndex.insert(13, 2);
        hashIndex.insert(9, 3);
        hashIndex.insert(21, 4); // Assuming splitting happens here

        // Check consistency after splitting
        List<Integer> results1 = hashIndex.search(5);
        assertNotNull(results1);
        assertTrue(results1.contains(1));

        List<Integer> results2 = hashIndex.search(21);
        assertNotNull(results2);
        assertTrue(results2.contains(4));
    }

    @Test
    public void testGlobalDepthDoubling() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");

        // Insert keys to cause global depth doubling
        hashIndex.insert(1, 10);
        hashIndex.insert(2, 11);
        hashIndex.insert(3, 12);
        hashIndex.insert(4, 13);
        hashIndex.insert(5, 14); // Assuming this causes global depth doubling

        // Verify all keys are retrievable after global depth doubling
        for (int i = 1; i <= 5; i++) {
            List<Integer> results = hashIndex.search(i);
            assertNotNull(results);
            assertTrue(results.contains(10 + i - 1));
        }
    }

    @Test
    public void testComplexInsert() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");

        // Insert a large number of keys to test the scalability and ensure correctness
        int numKeys = 100;
        for (int i = 1; i <= numKeys; i++) {
            hashIndex.insert(i, i * 10);
        }
        hashIndex.printTable();

        // Verify all keys are retrievable
        for (int i = 1; i <= numKeys; i++) {
            List<Integer> results = hashIndex.search(i);
            assertNotNull(results);
            assertTrue(results.contains(i * 10));
        }
    }

    @Test
    public void testReinsertion() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");

        // Insert the same key with different values
        hashIndex.insert(42, 100);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);
        hashIndex.insert(42, 200);


        hashIndex.printTable();
        // Ensure all values can be retrieved
        List<Integer> results = hashIndex.search(42);
        assertNotNull(results);
        assertTrue(results.contains(100));
        assertTrue(results.contains(200));

        // Ensure duplicate values are handled correctly
        hashIndex.insert(42, 100);
        results = hashIndex.search(42);

        // Still should not have duplicate 100s
        assertEquals(2, results.stream().distinct().count());
    }

    @Test
    public void testSearch() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class,"columnName");
        hashIndex.insert(15, 4);
        hashIndex.insert(25, 5);

        List<Integer> results = hashIndex.search(15);
        assertNotNull(results);
        assertTrue(results.contains(4));

        List<Integer> nonExistent = hashIndex.search(100);
        assertTrue(nonExistent.isEmpty());
    }

    @Test
    public void masterTest() {
        ExtendibleHashing<Integer> hashIndex = new ExtendibleHashing<>(Integer.class, "columnName");

        // Insert keys to cause global depth doubling
        hashIndex.insert(46, 0);
        hashIndex.insert(5, 1);
        hashIndex.insert(8, 2);
        hashIndex.insert(13, 3);
        hashIndex.insert(32, 4);
        hashIndex.insert(8, 5);
        hashIndex.insert(3, 6);
        hashIndex.insert(21, 7);
        hashIndex.insert(41, 8);
        hashIndex.insert(50, 9);
        hashIndex.insert(39, 10);
        hashIndex.insert(27, 11);
        hashIndex.insert(46, 12);
        hashIndex.insert(3, 13);
        hashIndex.insert(36, 14);
        hashIndex.insert(37, 15);
        hashIndex.insert(41, 16);
        hashIndex.insert(30, 17);
        hashIndex.insert(41, 18);
        hashIndex.insert(39, 19);

        hashIndex.insert(12, 20);
        hashIndex.insert(24, 21);
        hashIndex.insert(17, 22);

        hashIndex.printTable();

        hashIndex.insert(2, 23);
        hashIndex.insert(28, 24);
        hashIndex.insert(4, 25);
        hashIndex.insert(5, 26);
        hashIndex.insert(3, 27);


        hashIndex.printTable();

    }


}
