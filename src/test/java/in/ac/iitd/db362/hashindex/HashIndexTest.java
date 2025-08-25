package in.ac.iitd.db362.hashindex;

import in.ac.iitd.db362.index.hashindex.Bucket;
import in.ac.iitd.db362.index.hashindex.ExtendibleHashing;
import in.ac.iitd.db362.index.hashindex.HashingScheme;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashIndexTest {

    @Test
    public void testBasic_IntegerInsert() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, null);
        index.insert(4, 1);
        index.insert(5, 2);
        index.insert(6, 3);
        index.insert(7, 4);

        assertNotNull(index.search(4));
        assertEquals(index.search(4).size(), 1);
        assertNotNull(index.search(5));
        assertEquals(index.search(5).size(), 1);
        assertNotNull(index.search(6));
        assertEquals(index.search(6).size(), 1);
        assertNotNull(index.search(7));
        assertEquals(index.search(7).size(), 1);
        if (index.search(8) != null) assertEquals(index.search(8).size(), 0);
        assertEquals(index.search(4).get(0), 1);
        assertEquals(index.search(5).get(0), 2);
        assertEquals(index.search(6).get(0), 3);
        assertEquals(index.search(7).get(0), 4);
    }

    @Test
    public void testBasic_DoubleInsert() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Double> index = new ExtendibleHashing<>(Double.class, null);
        index.insert(4.1, 1);
        index.insert(2.1, 2);
        index.insert(3.71, 3);
        index.insert(7.4, 4);

        // int mask = (1 << ExtendibleHashing.INITIAL_GLOBAL_DEPTH) - 1;
        // System.out.println(Double.hashCode(4.1) & mask);
        // System.out.println(Double.hashCode(2.1) & mask);
        // System.out.println(Double.hashCode(3.71) & mask);
        // System.out.println(Double.hashCode(7.4) & mask);

        assertNotNull(index.search(4.1));
        assertEquals(index.search(4.1).size(), 1);
        assertNotNull(index.search(2.1));
        assertEquals(index.search(2.1).size(), 1);
        assertNotNull(index.search(3.71));
        assertEquals(index.search(3.71).size(), 1);
        assertNotNull(index.search(7.4));
        assertEquals(index.search(7.4).size(), 1);
        if (index.search(4.6) != null) assertEquals(index.search(4.6).size(), 0);
        assertEquals(index.search(4.1).get(0), 1);
        assertEquals(index.search(2.1).get(0), 2);
        assertEquals(index.search(3.71).get(0), 3);
        assertEquals(index.search(7.4).get(0), 4);
    }

    @Test
    public void testBasic_StringInsert() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<String> index = new ExtendibleHashing<>(String.class, null);
        index.insert("Vande", 1);
        index.insert("Mataram", 2);
        index.insert("Jai", 3);
        index.insert("Hind", 4);

        // int mask = (1 << ExtendibleHashing.INITIAL_GLOBAL_DEPTH) - 1;
        // System.out.println("Vande".hashCode() & mask);
        // System.out.println("Mataram".hashCode() & mask);
        // System.out.println("Jai".hashCode() & mask);
        // System.out.println("Hind".hashCode() & mask);

        assertNotNull(index.search("Vande"));
        assertEquals(index.search("Vande").size(), 1);
        assertNotNull(index.search("Mataram"));
        assertEquals(index.search("Mataram").size(), 1);
        assertNotNull(index.search("Jai"));
        assertEquals(index.search("Jai").size(), 1);
        assertNotNull(index.search("Hind"));
        assertEquals(index.search("Hind").size(), 1);
        if (index.search("DBMS") != null) assertEquals(index.search("DBMS").size(), 0);
        assertEquals(index.search("Vande").get(0), 1);
        assertEquals(index.search("Mataram").get(0), 2);
        assertEquals(index.search("Jai").get(0), 3);
        assertEquals(index.search("Hind").get(0), 4);
    }

    @Test
    public void testBasic_LocalDateInsert() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<LocalDate> index = new ExtendibleHashing<>(LocalDate.class, null);
        index.insert(LocalDate.parse("2025-01-04"), 1);
        index.insert(LocalDate.parse("2025-01-01"), 2);
        index.insert(LocalDate.parse("2025-01-02"), 3);
        index.insert(LocalDate.parse("2025-01-03"), 4);

        // int mask = (1 << ExtendibleHashing.INITIAL_GLOBAL_DEPTH) - 1;
        // System.out.println(LocalDate.parse("2025-01-04").hashCode() & mask);
        // System.out.println(LocalDate.parse("2025-01-01").hashCode() & mask);
        // System.out.println(LocalDate.parse("2025-01-02").hashCode() & mask);
        // System.out.println(LocalDate.parse("2025-01-03").hashCode() & mask);

        assertNotNull(index.search(LocalDate.parse("2025-01-04")));
        assertEquals(index.search(LocalDate.parse("2025-01-04")).size(), 1);
        assertNotNull(index.search(LocalDate.parse("2025-01-01")));
        assertEquals(index.search(LocalDate.parse("2025-01-01")).size(), 1);
        assertNotNull(index.search(LocalDate.parse("2025-01-02")));
        assertEquals(index.search(LocalDate.parse("2025-01-02")).size(), 1);
        assertNotNull(index.search(LocalDate.parse("2025-01-03")));
        assertEquals(index.search(LocalDate.parse("2025-01-03")).size(), 1);
        if (index.search(LocalDate.parse("2025-01-05")) != null) assertEquals(index.search(LocalDate.parse("2025-01-05")).size(), 0);
        assertEquals(index.search(LocalDate.parse("2025-01-04")).get(0), 1);
        assertEquals(index.search(LocalDate.parse("2025-01-01")).get(0), 2);
        assertEquals(index.search(LocalDate.parse("2025-01-02")).get(0), 3);
        assertEquals(index.search(LocalDate.parse("2025-01-03")).get(0), 4);
    }

    @Test
    public void testBasic_DuplicateInsert() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        index.insert(4, 1);
        index.insert(4, 2);
        index.insert(4, 3);

        assertNotNull(index.search(4));
        assertEquals(index.search(4).size(), 3);
        if (index.search(5) != null) assertEquals(index.search(5).size(), 0);
        List<Integer> ans = index.search(4);
        assertEquals(ans.get(0), 1);
        assertEquals(ans.get(1), 2);
        assertEquals(ans.get(2), 3);
    }

    @Test
    public void testIntermediate_BucketSplitting() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 2);
        assertEquals(index.getBucketCount(), 4);
        index.insert(4, 1);
        index.insert(1, 4);
        index.insert(8, 2);
        index.insert(12, 3);

        assertEquals(index.getGlobalDepth(), 3);
        assertEquals(index.getBucketCount(), 8);
        for (int i = 0; i < 8; i++) {
            Bucket<Integer> bucket = index.getBuckets()[i];
            Object[] keys = bucket.getKeys();
            if (bucket.getSize() != 0) {                
                if (keys[0].equals(1)) {
                    assertEquals(bucket.getSize(), 1);
                    assertEquals(bucket.getLocalDepth(), 2);
                } else if (keys[0].equals(4)) {
                    assertEquals(bucket.getSize(), 2);
                    assertEquals(keys[1], 12);
                    assertEquals(bucket.getLocalDepth(), 3);
                } else if (keys[0].equals(8)) {
                    assertEquals(bucket.getSize(), 1);
                    assertEquals(bucket.getLocalDepth(), 3);
                } else {
                    assertEquals(bucket.getSize(), 2);
                    assertEquals(keys[0], 12);
                    assertEquals(keys[1], 4);
                    assertEquals(bucket.getLocalDepth(), 3);
                }
            }
        }
        assertNotNull(index.search(1));
        assertEquals(index.search(1).size(), 1);
        assertNotNull(index.search(4));
        assertEquals(index.search(4).size(), 1);
        assertNotNull(index.search(8));
        assertEquals(index.search(8).size(), 1);
        assertNotNull(index.search(12));
        assertEquals(index.search(12).size(), 1);
        if (index.search(5) != null) assertEquals(index.search(5).size(), 0);
        assertEquals(index.search(1).get(0), 4);
        assertEquals(index.search(4).get(0), 1);
        assertEquals(index.search(8).get(0), 2);
        assertEquals(index.search(12).get(0), 3);
    }

    @Test
    public void testIntermediate_DuplicateBucketSplitting() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 2);
        assertEquals(index.getBucketCount(), 4);
        index.insert(4, 1);
        index.insert(8, 2);
        index.insert(4, 3);

        assertEquals(index.getGlobalDepth(), 3);
        assertEquals(index.getBucketCount(), 8);
        for (int i = 0; i < 8; i++) {
            Bucket<Integer> bucket = index.getBuckets()[i];
            Object[] keys = bucket.getKeys();
            if (bucket.getSize() != 0) {
                if (keys[0].equals(4)) {
                    assertEquals(bucket.getSize(), 2);
                    assertEquals(keys[1], 4);
                    assertEquals(bucket.getLocalDepth(), 3);
                } else {
                    assertEquals(bucket.getSize(), 1);
                    assertEquals(keys[0], 8);
                    assertEquals(bucket.getLocalDepth(), 3);
                }
            }
        }
        assertNotNull(index.search(4));
        assertEquals(index.search(4).size(), 2);
        assertNotNull(index.search(8));
        assertEquals(index.search(8).size(), 1);
        if (index.search(12) != null) assertEquals(index.search(12).size(), 0);
        assertEquals(index.search(8).get(0), 2);
        List<Integer> ans = index.search(4);
        assertEquals(ans.get(0), 1);
        assertEquals(ans.get(1), 3);
    }

    @Test
    public void testIntermediate_DoubleBucketSplitting() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 1;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 1);
        assertEquals(index.getBucketCount(), 2);
        index.insert(4, 1);
        index.insert(8, 2);
        index.insert(12, 3);

        assertNotNull(index.search(4));
        assertEquals(index.search(4).size(), 1);
        assertNotNull(index.search(8));
        assertEquals(index.search(8).size(), 1);
        assertNotNull(index.search(12));
        assertEquals(index.search(12).size(), 1);
        if (index.search(16) != null) assertEquals(index.search(16).size(), 0);
        assertEquals(index.search(4).get(0), 1);
        assertEquals(index.search(8).get(0), 2);
        assertEquals(index.search(12).get(0), 3);
    }

    @Test
    public void testIntermediate_QuadrupleBucketSplitting() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 1;
        ExtendibleHashing.BUCKET_SIZE = 1;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 1);
        assertEquals(index.getBucketCount(), 2);
        index.insert(16, 1);
        index.insert(48, 2);

        assertNotNull(index.search(16));
        assertEquals(index.search(16).size(), 1);
        assertNotNull(index.search(48));
        assertEquals(index.search(48).size(), 1);
        if (index.search(64) != null) assertEquals(index.search(64).size(), 0);
        assertEquals(index.search(16).get(0), 1);
        assertEquals(index.search(48).get(0), 2);
    }

    @Test
    public void testIntermediate_OverflowBucketSplitting() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 1;
        ExtendibleHashing.BUCKET_SIZE = 1;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 1);
        assertEquals(index.getBucketCount(), 2);
        index.insert(16, 1);
        index.insert(16, 3);
        index.insert(18, 2);

        assertEquals(index.getGlobalDepth(), 2);
        assertEquals(index.getBucketCount(), 4);
        for (int i = 0; i < 4; i++) {
            Bucket<Integer> bucket = index.getBuckets()[i];
            Object[] keys = bucket.getKeys();
            if (bucket.getSize() != 0) {
                if (keys[0].equals(16)) {
                    assertEquals(bucket.getSize(), 1);
                    assertEquals(bucket.getNext().getSize(), 1);
                    Bucket<Integer> overflowBucket = bucket.getNext();
                    Object[] overflowKeys = overflowBucket.getKeys();
                    assertEquals(overflowKeys[0], 16);
                    assertEquals(bucket.getLocalDepth(), 2);
                } else {
                    assertEquals(bucket.getSize(), 1);
                    assertEquals(keys[0], 18);
                    assertEquals(bucket.getLocalDepth(), 2);
                }
            }
        }
        assertNotNull(index.search(16));
        assertEquals(index.search(16).size(), 2);
        assertNotNull(index.search(18));
        assertEquals(index.search(18).size(), 1);
        if (index.search(64) != null) assertEquals(index.search(64).size(), 0);
        assertEquals(index.search(18).get(0), 2);
        List<Integer> ans = index.search(16);
        assertEquals(ans.get(0), 1);
        assertEquals(ans.get(1), 3);
    }

    @Test
    public void testIndexMapping() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "testAttr");

        index.insert(2147483647, 1);
        index.insert(-2147483648, 2);
        index.insert(0, 3);
        index.insert(1, 4);
        index.insert(-1, 5);
        index.insert(123456789, 6);

        assertEquals(2, index.getGlobalDepth());
        assertEquals(4, index.getBucketCount());
        assertEquals(1, (int) index.search(2147483647).get(0));
        assertEquals(2, (int) index.search(-2147483648).get(0));
        assertEquals(3, (int) index.search(0).get(0));
        assertEquals(4, (int) index.search(1).get(0));
        assertEquals(5, (int) index.search(-1).get(0));
        assertEquals(6, (int) index.search(123456789).get(0));

        index.insert(-987654321, 7);

        if ((index.getGlobalDepth() == 5)) {
            int idx4_new = HashingScheme.getDirectoryIndex(-1, index.getGlobalDepth());
            int idx6_new = HashingScheme.getDirectoryIndex(2147483647, index.getGlobalDepth());
            assertEquals(31, idx4_new);
            assertEquals(5, index.getLocalDepth(idx4_new));
            assertEquals(31, idx6_new);
            assertEquals(5, index.getLocalDepth(idx6_new));
        } else {
            int idx4_current = HashingScheme.getDirectoryIndex(-1, index.getGlobalDepth());
            Bucket<Integer> bucket = index.getBuckets()[idx4_current];
            boolean chained = false;
            Bucket<Integer> overflow = bucket.getNext();
            if (overflow != null) {
                for (int i = 0; i < overflow.getSize(); i++) {
                    if (overflow.getKeys()[i].equals(-987654321)) {
                        chained = true;
                        break;
                    }
                }
            }
            assertTrue(chained);
        }

    }

    @Test
    public void testIndexMappingAndSplitting() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "testAttr");

        index.insert(1, 1);
        index.insert(2, 2);
        index.insert(12, 3);
        index.insert(5, 4);

        assertEquals(2, index.getGlobalDepth());
        assertEquals(4, index.getBucketCount());

        int idx12 = HashingScheme.getDirectoryIndex(12, 2);
        assertEquals(0, idx12);
        assertEquals(2, index.getLocalDepth(idx12));

        int idx1 = HashingScheme.getDirectoryIndex(1, 2);
        int idx5 = HashingScheme.getDirectoryIndex(5, 2);
        assertEquals(1, idx1);
        assertEquals(1, idx5);
        assertEquals(2, index.getLocalDepth(idx1));

        int idx2 = HashingScheme.getDirectoryIndex(2, 2);
        assertEquals(2, idx2);
        assertEquals(2, index.getLocalDepth(idx2));

        int idx3 = 3;
        assertEquals(0, index.getBuckets()[idx3].getSize());
        assertEquals(2, index.getLocalDepth(idx3));

        index.insert(9, 5);

        assertEquals(3, index.getGlobalDepth());

        int newIdx1 = HashingScheme.getDirectoryIndex(1, 3);
        int newIdx9 = HashingScheme.getDirectoryIndex(9, 3);
        assertEquals(1, newIdx1);
        assertEquals(1, newIdx9);
        assertEquals(3, index.getLocalDepth(newIdx1));

        int newIdx5 = HashingScheme.getDirectoryIndex(5, 3);
        assertEquals(5, newIdx5);
        assertEquals(3, index.getLocalDepth(newIdx5));

        index.insert(25, 6);

        assertEquals(4, index.getGlobalDepth());

        int idx9 = HashingScheme.getDirectoryIndex(9, 4);
        int idx25 = HashingScheme.getDirectoryIndex(25, 4);
        assertEquals(9, idx9);
        assertEquals(9, idx25);
        assertEquals(4, index.getLocalDepth(idx9));

        int idx1_4 = HashingScheme.getDirectoryIndex(1, 4);
        assertEquals(1, idx1_4);
        assertEquals(4, index.getLocalDepth(idx1_4));

        index.insert(19, 7);
        index.insert(11, 8);

        int idx19 = HashingScheme.getDirectoryIndex(19, 4);
        int idx11 = HashingScheme.getDirectoryIndex(11, 4);
        assertEquals(2, index.getLocalDepth(idx11));
        assertEquals(2, index.getLocalDepth(idx19));

        index.insert(7, 9);

        int idx19_after = HashingScheme.getDirectoryIndex(19, 4);
        int idx7_after = HashingScheme.getDirectoryIndex(7, 4);
        int idx11_after = HashingScheme.getDirectoryIndex(11, 4);
        assertEquals(3, index.getLocalDepth(idx7_after));
        assertEquals(3, index.getLocalDepth(idx19_after));
        // assertNotSame(index.getBuckets()[idx19_after],
        // index.getBuckets()[idx11_after]);
        assertEquals(3, index.getLocalDepth(idx11_after));
    }

    @Test
    public void testMappingAndSearch() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "testAttr");

        index.insert(1, 1);
        index.insert(2, 2);
        index.insert(3, 3);
        index.insert(4, 4);
        index.insert(5, 5);
        index.insert(6, 6);
        index.insert(7, 7);
        index.insert(8, 8);

        assertEquals(2, index.getGlobalDepth());
        assertEquals(4, index.getBucketCount());
        assertEquals(0, HashingScheme.getDirectoryIndex(4, 2));
        assertEquals(0, HashingScheme.getDirectoryIndex(8, 2));
        assertEquals(1, HashingScheme.getDirectoryIndex(1, 2));
        assertEquals(1, HashingScheme.getDirectoryIndex(5, 2));
        assertEquals(2, HashingScheme.getDirectoryIndex(2, 2));
        assertEquals(2, HashingScheme.getDirectoryIndex(6, 2));
        assertEquals(3, HashingScheme.getDirectoryIndex(3, 2));
        assertEquals(3, HashingScheme.getDirectoryIndex(7, 2));

        index.insert(9, 9);
        assertEquals(3, index.getGlobalDepth());
        assertEquals(8, index.getBucketCount());
        Bucket<Integer>[] buckets = index.getBuckets();
        assertEquals(1, HashingScheme.getDirectoryIndex(1, 3));
        assertEquals(1, HashingScheme.getDirectoryIndex(9, 3));

        for (int i = 0; i < buckets.length; i++) {
            if (i != 1 && i != 5) {
                assertEquals(2, index.getLocalDepth(i));
            }
        }

        index.insert(10, 10);
        index.insert(11, 11);
        index.insert(12, 12);
        index.insert(13, 13);
        index.insert(14, 14);
        index.insert(15, 15);
        index.insert(16, 16);
        assertEquals(0, HashingScheme.getDirectoryIndex(8, 3));
        assertEquals(0, HashingScheme.getDirectoryIndex(16, 3));
        assertEquals(2, HashingScheme.getDirectoryIndex(2, 3));
        assertEquals(2, HashingScheme.getDirectoryIndex(10, 3));
        assertEquals(3, HashingScheme.getDirectoryIndex(3, 3));
        assertEquals(3, HashingScheme.getDirectoryIndex(11, 3));
        assertEquals(4, HashingScheme.getDirectoryIndex(4, 3));
        assertEquals(4, HashingScheme.getDirectoryIndex(12, 3));
        assertEquals(5, HashingScheme.getDirectoryIndex(5, 3));
        assertEquals(5, HashingScheme.getDirectoryIndex(13, 3));
        assertEquals(6, HashingScheme.getDirectoryIndex(6, 3));
        assertEquals(6, HashingScheme.getDirectoryIndex(14, 3));
        assertEquals(7, HashingScheme.getDirectoryIndex(7, 3));
        assertEquals(7, HashingScheme.getDirectoryIndex(15, 3));
        buckets = index.getBuckets();
        for (int i = 0; i < buckets.length; i++) {
            assertEquals(2, buckets[i].getSize());
        }
        for (int k = 1; k <= 16; k++) {
            List<Integer> res = index.search(k);
            assertNotNull(res);

        }
    }

    @Test
    public void testDoubleInsertCase() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 1;
        ExtendibleHashing<Double> index = new ExtendibleHashing<>(Double.class, "testAttr");

        index.insert(1.0e40, 1);
        index.insert(1.0e18, 2);
        index.insert(1.0e-40, 3);
        index.insert(1.0e17, 4);

        assertEquals(2, index.getGlobalDepth());
        assertEquals(4, index.getBucketCount());
        int idx1 = HashingScheme.getDirectoryIndex(1.0e40, index.getGlobalDepth());
        int idx2 = HashingScheme.getDirectoryIndex(1.0e18, index.getGlobalDepth());
        int idx3 = HashingScheme.getDirectoryIndex(1.0e-40, index.getGlobalDepth());
        int idx4 = HashingScheme.getDirectoryIndex(1.0e17, index.getGlobalDepth());
        assertEquals(0, idx1);
        assertEquals(1, idx2);
        assertEquals(2, idx3);
        assertEquals(3, idx4);
        assertEquals(2, index.getLocalDepth(idx1));
        assertEquals(2, index.getLocalDepth(idx2));
        assertEquals(2, index.getLocalDepth(idx3));
        assertEquals(2, index.getLocalDepth(idx4));

        index.insert(1.07, 5);

        if (index.getGlobalDepth() == 5) {
            int idx4_new = HashingScheme.getDirectoryIndex(1.0e17, index.getGlobalDepth());
            int idx6_new = HashingScheme.getDirectoryIndex(1.07, index.getGlobalDepth());
            assertEquals(23, idx4_new);
            assertEquals(5, index.getLocalDepth(idx4_new));
            assertEquals(7, idx6_new);
            assertEquals(5, index.getLocalDepth(idx6_new));
        } else {
            int idx4_current = HashingScheme.getDirectoryIndex(1.0e17, index.getGlobalDepth());
            Bucket<Double> bucket = index.getBuckets()[idx4_current];
            boolean chained = false;
            Bucket<Double> overflow = bucket.getNext();
            if (overflow != null) {
                for (int i = 0; i < overflow.getSize(); i++) {
                    if (overflow.getKeys()[i].equals(1.07)) {
                        chained = true;
                        break;
                    }
                }
            }
            assertTrue(chained);
        }
    }

    @Test
    public void testRandomDateInsertsAndNonExistentSearches() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 3;
        ExtendibleHashing.BUCKET_SIZE = 3;
        ExtendibleHashing<LocalDate> index = new ExtendibleHashing<>(LocalDate.class, "dateIndex");

        LocalDate d1 = LocalDate.of(2003, 5, 17);
        LocalDate d2 = LocalDate.of(1997, 12, 4);
        LocalDate d3 = LocalDate.of(2020, 2, 29);
        LocalDate d4 = LocalDate.of(2010, 7, 20);
        LocalDate d5 = LocalDate.of(2005, 11, 3);
        LocalDate d6 = LocalDate.of(2015, 1, 10);
        LocalDate d7 = LocalDate.of(1999, 8, 30);
        LocalDate d8 = LocalDate.of(2030, 3, 15);
        LocalDate d9 = LocalDate.of(2040, 6, 25);
        LocalDate d10 = LocalDate.of(2077, 7, 7);

        index.insert(d1, 1);
        index.insert(d2, 2);
        index.insert(d3, 3);
        index.insert(d4, 4);
        index.insert(d5, 5);
        index.insert(d6, 6);
        index.insert(d7, 7);
        index.insert(d8, 8);
        index.insert(d9, 9);
        index.insert(d10, 10);

        List<Integer> res1 = index.search(d1);
        assertEquals(1, res1.size());
        assertEquals(1, (int) res1.get(0));
        List<Integer> res2 = index.search(d2);
        assertEquals(1, res2.size());
        assertEquals(2, (int) res2.get(0));
        List<Integer> res3 = index.search(d3);
        assertEquals(1, res3.size());
        assertEquals(3, (int) res3.get(0));
        List<Integer> res4 = index.search(d4);
        assertEquals(1, res4.size());
        assertEquals(4, (int) res4.get(0));
        List<Integer> res5 = index.search(d5);
        assertEquals(1, res5.size());
        assertEquals(5, (int) res5.get(0));
        List<Integer> res6 = index.search(d6);
        assertEquals(1, res6.size());
        assertEquals(6, (int) res6.get(0));
        List<Integer> res7 = index.search(d7);
        assertEquals(1, res7.size());
        assertEquals(7, (int) res7.get(0));
        List<Integer> res8 = index.search(d8);
        assertEquals(1, res8.size());
        assertEquals(8, (int) res8.get(0));
        List<Integer> res9 = index.search(d9);
        assertEquals(1, res9.size());
        assertEquals(9, (int) res9.get(0));
        List<Integer> res10 = index.search(d10);
        assertEquals(1, res10.size());
        assertEquals(10, (int) res10.get(0));

        LocalDate n1 = LocalDate.of(2001, 4, 1);
        LocalDate n2 = LocalDate.of(2002, 2, 2);
        LocalDate n3 = LocalDate.of(2011, 11, 11);
        LocalDate n4 = LocalDate.of(2033, 3, 3);
        LocalDate n5 = LocalDate.of(2055, 5, 5);

        List<Integer> resN1 = index.search(n1);
        assertEquals(0, resN1.size());
        List<Integer> resN2 = index.search(n2);
        assertEquals(0, resN2.size());
        List<Integer> resN3 = index.search(n3);
        assertEquals(0, resN3.size());
        List<Integer> resN4 = index.search(n4);
        assertEquals(0, resN4.size());
        List<Integer> resN5 = index.search(n5);
        assertEquals(0, resN5.size());
    }

    @Test
    public void testBigRandomDoubleInsertSearch() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Double> index = new ExtendibleHashing<>(Double.class, "bigDoubleTest");

        double x1 = 3.14159;
        double x2 = -2.71828;
        double x3 = 0.000123;
        double x4 = 99999.99;
        double x5 = -100000.001;
        double x6 = 42.42;
        double x7 = 1.2345678;
        double x8 = -7.7777;
        double x9 = 1234.567;
        double x10 = -456.789;
        double x11 = 987.654321;
        double x12 = -321.123;
        double x13 = 66.66;
        double x14 = -66.67;
        double x15 = 999999.9999;
        double x16 = -999999.99;
        double x17 = 0.999999;
        double x18 = -0.999999;
        double x19 = 12.345678;
        double x20 = -0.000001;
        double x21 = 31415.9265;
        double x22 = -27182.81828;
        double x23 = 123456.789;
        double x24 = -123456.789;
        double x25 = 9999.9999;
        double x26 = -9999.9999;
        double x27 = 555.555;
        double x28 = -555.556;
        double x29 = 0.1234567;
        double x30 = -0.1234567;
        double x31 = 77.7777;
        double x32 = -88.8888;
        double x33 = 2.3456789;
        double x34 = -3.210987;
        double x35 = 111.111;
        double x36 = -111.111;
        double x37 = 999.0001;
        double x38 = -999.0001;
        double x39 = 13.37;
        double x40 = -13.37;
        double x41 = 123.000456;
        double x42 = -456.000123;
        double x43 = 789.123;
        double x44 = -789.123;
        double x45 = 2468.135;
        double x46 = -2468.135;
        double x47 = 98765.4321;
        double x48 = -98765.4321;
        double x49 = 54321.98765;
        double x50 = -54321.98765;

        index.insert(x1, 1);
        index.insert(x2, 2);
        index.insert(x3, 3);
        index.insert(x4, 4);
        index.insert(x5, 5);
        index.insert(x6, 6);
        index.insert(x7, 7);
        index.insert(x8, 8);
        index.insert(x9, 9);
        index.insert(x10, 10);
        index.insert(x11, 11);
        index.insert(x12, 12);
        index.insert(x13, 13);
        index.insert(x14, 14);
        index.insert(x15, 15);
        index.insert(x16, 16);
        index.insert(x17, 17);
        index.insert(x18, 18);
        index.insert(x19, 19);
        index.insert(x20, 20);
        index.insert(x21, 21);
        index.insert(x22, 22);
        index.insert(x23, 23);
        index.insert(x24, 24);
        index.insert(x25, 25);
        index.insert(x26, 26);
        index.insert(x27, 27);
        index.insert(x28, 28);
        index.insert(x29, 29);
        index.insert(x30, 30);
        index.insert(x31, 31);
        index.insert(x32, 32);
        index.insert(x33, 33);
        index.insert(x34, 34);
        index.insert(x35, 35);
        index.insert(x36, 36);
        index.insert(x37, 37);
        index.insert(x38, 38);
        index.insert(x39, 39);
        index.insert(x40, 40);
        index.insert(x41, 41);
        index.insert(x42, 42);
        index.insert(x43, 43);
        index.insert(x44, 44);
        index.insert(x45, 45);
        index.insert(x46, 46);
        index.insert(x47, 47);
        index.insert(x48, 48);
        index.insert(x49, 49);
        index.insert(x50, 50);

        List<Integer> r1 = index.search(x1);
        assertEquals(1, r1.size());
        assertEquals(1, (int) r1.get(0));
        List<Integer> r2 = index.search(x2);
        assertEquals(1, r2.size());
        assertEquals(2, (int) r2.get(0));
        List<Integer> r3 = index.search(x3);
        assertEquals(1, r3.size());
        assertEquals(3, (int) r3.get(0));
        List<Integer> r4 = index.search(x4);
        assertEquals(1, r4.size());
        assertEquals(4, (int) r4.get(0));
        List<Integer> r5 = index.search(x5);
        assertEquals(1, r5.size());
        assertEquals(5, (int) r5.get(0));
        List<Integer> r6 = index.search(x6);
        assertEquals(1, r6.size());
        assertEquals(6, (int) r6.get(0));
        List<Integer> r7 = index.search(x7);
        assertEquals(1, r7.size());
        assertEquals(7, (int) r7.get(0));
        List<Integer> r8 = index.search(x8);
        assertEquals(1, r8.size());
        assertEquals(8, (int) r8.get(0));
        List<Integer> r9 = index.search(x9);
        assertEquals(1, r9.size());
        assertEquals(9, (int) r9.get(0));
        List<Integer> r10 = index.search(x10);
        assertEquals(1, r10.size());
        assertEquals(10, (int) r10.get(0));
        List<Integer> r11 = index.search(x11);
        assertEquals(1, r11.size());
        assertEquals(11, (int) r11.get(0));
        List<Integer> r12 = index.search(x12);
        assertEquals(1, r12.size());
        assertEquals(12, (int) r12.get(0));
        List<Integer> r13 = index.search(x13);
        assertEquals(1, r13.size());
        assertEquals(13, (int) r13.get(0));
        List<Integer> r14 = index.search(x14);
        assertEquals(1, r14.size());
        assertEquals(14, (int) r14.get(0));
        List<Integer> r15 = index.search(x15);
        assertEquals(1, r15.size());
        assertEquals(15, (int) r15.get(0));
        List<Integer> r16 = index.search(x16);
        assertEquals(1, r16.size());
        assertEquals(16, (int) r16.get(0));
        List<Integer> r17 = index.search(x17);
        assertEquals(1, r17.size());
        assertEquals(17, (int) r17.get(0));
        List<Integer> r18 = index.search(x18);
        assertEquals(1, r18.size());
        assertEquals(18, (int) r18.get(0));
        List<Integer> r19 = index.search(x19);
        assertEquals(1, r19.size());
        assertEquals(19, (int) r19.get(0));
        List<Integer> r20 = index.search(x20);
        assertEquals(1, r20.size());
        assertEquals(20, (int) r20.get(0));
        List<Integer> r21 = index.search(x21);
        assertEquals(1, r21.size());
        assertEquals(21, (int) r21.get(0));
        List<Integer> r22 = index.search(x22);
        assertEquals(1, r22.size());
        assertEquals(22, (int) r22.get(0));
        List<Integer> r23 = index.search(x23);
        assertEquals(1, r23.size());
        assertEquals(23, (int) r23.get(0));
        List<Integer> r24 = index.search(x24);
        assertEquals(1, r24.size());
        assertEquals(24, (int) r24.get(0));
        List<Integer> r25 = index.search(x25);
        assertEquals(1, r25.size());
        assertEquals(25, (int) r25.get(0));
        List<Integer> r26 = index.search(x26);
        assertEquals(1, r26.size());
        assertEquals(26, (int) r26.get(0));
        List<Integer> r27 = index.search(x27);
        assertEquals(1, r27.size());
        assertEquals(27, (int) r27.get(0));
        List<Integer> r28 = index.search(x28);
        assertEquals(1, r28.size());
        assertEquals(28, (int) r28.get(0));
        List<Integer> r29 = index.search(x29);
        assertEquals(1, r29.size());
        assertEquals(29, (int) r29.get(0));
        List<Integer> r30 = index.search(x30);
        assertEquals(1, r30.size());
        assertEquals(30, (int) r30.get(0));
        List<Integer> r31 = index.search(x31);
        assertEquals(1, r31.size());
        assertEquals(31, (int) r31.get(0));
        List<Integer> r32 = index.search(x32);
        assertEquals(1, r32.size());
        assertEquals(32, (int) r32.get(0));
        List<Integer> r33 = index.search(x33);
        assertEquals(1, r33.size());
        assertEquals(33, (int) r33.get(0));
        List<Integer> r34 = index.search(x34);
        assertEquals(1, r34.size());
        assertEquals(34, (int) r34.get(0));
        List<Integer> r35 = index.search(x35);
        assertEquals(1, r35.size());
        assertEquals(35, (int) r35.get(0));
        List<Integer> r36 = index.search(x36);
        assertEquals(1, r36.size());
        assertEquals(36, (int) r36.get(0));
        List<Integer> r37 = index.search(x37);
        assertEquals(1, r37.size());
        assertEquals(37, (int) r37.get(0));
        List<Integer> r38 = index.search(x38);
        assertEquals(1, r38.size());
        assertEquals(38, (int) r38.get(0));
        List<Integer> r39 = index.search(x39);
        assertEquals(1, r39.size());
        assertEquals(39, (int) r39.get(0));
        List<Integer> r40 = index.search(x40);
        assertEquals(1, r40.size());
        assertEquals(40, (int) r40.get(0));
        List<Integer> r41 = index.search(x41);
        assertEquals(1, r41.size());
        assertEquals(41, (int) r41.get(0));
        List<Integer> r42 = index.search(x42);
        assertEquals(1, r42.size());
        assertEquals(42, (int) r42.get(0));
        List<Integer> r43 = index.search(x43);
        assertEquals(1, r43.size());
        assertEquals(43, (int) r43.get(0));
        List<Integer> r44 = index.search(x44);
        assertEquals(1, r44.size());
        assertEquals(44, (int) r44.get(0));
        List<Integer> r45 = index.search(x45);
        assertEquals(1, r45.size());
        assertEquals(45, (int) r45.get(0));
        List<Integer> r46 = index.search(x46);
        assertEquals(1, r46.size());
        assertEquals(46, (int) r46.get(0));
        List<Integer> r47 = index.search(x47);
        assertEquals(1, r47.size());
        assertEquals(47, (int) r47.get(0));
        List<Integer> r48 = index.search(x48);
        assertEquals(1, r48.size());
        assertEquals(48, (int) r48.get(0));
        List<Integer> r49 = index.search(x49);
        assertEquals(1, r49.size());
        assertEquals(49, (int) r49.get(0));
        List<Integer> r50 = index.search(x50);
        assertEquals(1, r50.size());
        assertEquals(50, (int) r50.get(0));

        double y1 = 2023.2023;
        double y2 = -999.999999;
        double y3 = 77.77777777;
        double y4 = -888.888888;
        double y5 = 456.7891011;
        double y6 = -456.7891011;
        double y7 = 0.00004567;
        double y8 = -0.999999999;
        double y9 = 5555.5555;
        double y10 = -1234.56789;
        double y11 = 987654.321;
        double y12 = -987654.321;
        double y13 = 123.123;
        double y14 = -9999999.9999;
        double y15 = 3141.59265;

        List<Integer> ry1 = index.search(y1);
        assertTrue(ry1.isEmpty());
        List<Integer> ry2 = index.search(y2);
        assertTrue(ry2.isEmpty());
        List<Integer> ry3 = index.search(y3);
        assertTrue(ry3.isEmpty());
        List<Integer> ry4 = index.search(y4);
        assertTrue(ry4.isEmpty());
        List<Integer> ry5 = index.search(y5);
        assertTrue(ry5.isEmpty());
        List<Integer> ry6 = index.search(y6);
        assertTrue(ry6.isEmpty());
        List<Integer> ry7 = index.search(y7);
        assertTrue(ry7.isEmpty());
        List<Integer> ry8 = index.search(y8);
        assertTrue(ry8.isEmpty());
        List<Integer> ry9 = index.search(y9);
        assertTrue(ry9.isEmpty());
        List<Integer> ry10 = index.search(y10);
        assertTrue(ry10.isEmpty());
        List<Integer> ry11 = index.search(y11);
        assertTrue(ry11.isEmpty());
        List<Integer> ry12 = index.search(y12);
        assertTrue(ry12.isEmpty());
        List<Integer> ry13 = index.search(y13);
        assertTrue(ry13.isEmpty());
        List<Integer> ry14 = index.search(y14);
        assertTrue(ry14.isEmpty());
        List<Integer> ry15 = index.search(y15);
        assertTrue(ry15.isEmpty());
    }

    @Test
    public void testCityNamesInsertsAndNonExistingSearches() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 4;
        ExtendibleHashing.BUCKET_SIZE = 3;
        ExtendibleHashing<String> index = new ExtendibleHashing<>(String.class, "cityIndex");

        index.insert("London", 1);
        index.insert("Paris", 2);
        index.insert("Tokyo", 3);
        index.insert("Beijing", 4);
        index.insert("Moscow", 5);
        index.insert("Berlin", 6);
        index.insert("Rome", 7);
        index.insert("Madrid", 8);
        index.insert("Sydney", 9);
        index.insert("Melbourne", 10);
        index.insert("Dubai", 11);
        index.insert("Mumbai", 12);
        index.insert("Delhi", 13);
        index.insert("Bangkok", 14);
        index.insert("Singapore", 15);
        index.insert("Hong Kong", 16);
        index.insert("Seoul", 17);
        index.insert("Istanbul", 18);
        index.insert("Cairo", 19);
        index.insert("Johannesburg", 20);
        index.insert("Lagos", 21);
        index.insert("Nairobi", 22);
        index.insert("Buenos Aires", 23);
        index.insert("Sao Paulo", 24);
        index.insert("Mexico City", 25);
        index.insert("Toronto", 26);
        index.insert("Vancouver", 27);
        index.insert("Lisbon", 28);
        index.insert("Athens", 29);
        index.insert("Vienna", 30);
        index.insert("Zurich", 31);
        index.insert("Amsterdam", 32);
        index.insert("Prague", 33);
        index.insert("Brussels", 34);
        index.insert("Stockholm", 35);

        assertEquals(1, index.search("London").size());
        assertEquals(1, index.search("Paris").size());
        assertEquals(1, index.search("Tokyo").size());
        assertEquals(1, index.search("Beijing").size());
        assertEquals(1, index.search("Moscow").size());
        assertEquals(1, index.search("Berlin").size());
        assertEquals(1, index.search("Rome").size());
        assertEquals(1, index.search("Madrid").size());
        assertEquals(1, index.search("Sydney").size());
        assertEquals(1, index.search("Melbourne").size());
        assertEquals(1, index.search("Dubai").size());
        assertEquals(1, index.search("Mumbai").size());
        assertEquals(1, index.search("Delhi").size());
        assertEquals(1, index.search("Bangkok").size());
        assertEquals(1, index.search("Singapore").size());
        assertEquals(1, index.search("Hong Kong").size());
        assertEquals(1, index.search("Seoul").size());
        assertEquals(1, index.search("Istanbul").size());
        assertEquals(1, index.search("Cairo").size());
        assertEquals(1, index.search("Johannesburg").size());
        assertEquals(1, index.search("Lagos").size());
        assertEquals(1, index.search("Nairobi").size());
        assertEquals(1, index.search("Buenos Aires").size());
        assertEquals(1, index.search("Sao Paulo").size());
        assertEquals(1, index.search("Mexico City").size());
        assertEquals(1, index.search("Toronto").size());
        assertEquals(1, index.search("Vancouver").size());
        assertEquals(1, index.search("Lisbon").size());
        assertEquals(1, index.search("Athens").size());
        assertEquals(1, index.search("Vienna").size());
        assertEquals(1, index.search("Zurich").size());
        assertEquals(1, index.search("Amsterdam").size());
        assertEquals(1, index.search("Prague").size());
        assertEquals(1, index.search("Brussels").size());
        assertEquals(1, index.search("Stockholm").size());

        assertEquals(0, index.search("Kuala Lumpur").size());
        assertEquals(0, index.search("Hanoi").size());
        assertEquals(0, index.search("Budapest").size());
        assertEquals(0, index.search("Edinburgh").size());
        assertEquals(0, index.search("Oslo").size());
        assertEquals(0, index.search("Dublin").size());
        assertEquals(0, index.search("Copenhagen").size());
        assertEquals(0, index.search("Reykjavik").size());
        assertEquals(0, index.search("Accra").size());
        assertEquals(0, index.search("Casablanca").size());
        assertEquals(0, index.search("Kiev").size());
        assertEquals(0, index.search("Manila").size());
        assertEquals(0, index.search("Bogota").size());
        assertEquals(0, index.search("Lima").size());
        assertEquals(0, index.search("Santiago").size());
    }

    @Test
    public void testDoubleHashingScenarioForDoubles() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Double> index = new ExtendibleHashing<>(Double.class, "testAttr");

        index.insert(1.0, 1);
        index.insert(98.7654321, 2);
        index.insert(999.99, 3);
        index.insert(86.42, 4);
        index.insert(-68.402, 5);
        index.insert(246.8, 6);

        assertEquals(2, index.getGlobalDepth());
        assertEquals(4, index.getBucketCount());

        int idx0_1 = HashingScheme.getDirectoryIndex(1.0, index.getGlobalDepth());
        int idx0_2 = HashingScheme.getDirectoryIndex(98.7654321, index.getGlobalDepth());
        assertEquals(0, idx0_1);
        assertEquals(0, idx0_2);

        int idx1 = HashingScheme.getDirectoryIndex(999.99, index.getGlobalDepth());
        assertEquals(1, idx1);

        int idx2 = HashingScheme.getDirectoryIndex(86.42, index.getGlobalDepth());
        assertEquals(2, idx2);

        int idx3_1 = HashingScheme.getDirectoryIndex(-68.402, index.getGlobalDepth());
        int idx3_2 = HashingScheme.getDirectoryIndex(246.8, index.getGlobalDepth());
        assertEquals(3, idx3_1);
        assertEquals(3, idx3_2);

        // Bucket<Double>[] buckets = index.getBuckets();

        // // {
        // // Double[] arr = buckets[0].getKeys();
        // // int sz = buckets[0].getSize();
        // // boolean foundA = false, foundB = false;
        // // for (int i = 0; i < sz; i++) {
        // // if (arr[i].equals(1.0))
        // // foundA = true;
        // // if (arr[i].equals(98.7654321))
        // // foundB = true;
        // // }
        // // assertTrue(foundA);
        // // assertTrue(foundB);
        // // }

        // {
        // Double[] arr = buckets[1].getKeys();
        // int sz = buckets[1].getSize();
        // boolean found = false;
        // for (int i = 0; i < sz; i++) {
        // if (arr[i].equals(999.99))
        // found = true;
        // }
        // assertTrue(found);
        // }

        // {
        // Double[] arr = buckets[2].getKeys();
        // int sz = buckets[2].getSize();
        // boolean found = false;
        // for (int i = 0; i < sz; i++) {
        // if (arr[i].equals(86.42))
        // found = true;
        // }
        // assertTrue(found);
        // }

        // {
        // Double[] arr = buckets[3].getKeys();
        // int sz = buckets[3].getSize();
        // boolean foundA = false, foundB = false;
        // for (int i = 0; i < sz; i++) {
        // if (arr[i].equals(-68.402))
        // foundA = true;
        // if (arr[i].equals(246.8))
        // foundB = true;
        // }
        // assertTrue(foundA);
        // assertTrue(foundB);
        // }

        index.insert(8.6, 7);
        assertEquals(3, index.getGlobalDepth());

        int newIdx0_1 = HashingScheme.getDirectoryIndex(1.0, index.getGlobalDepth());
        int newIdx0_2 = HashingScheme.getDirectoryIndex(8.6, index.getGlobalDepth());
        assertEquals(0, newIdx0_1);
        assertEquals(0, newIdx0_2);

        int newIdx4 = HashingScheme.getDirectoryIndex(98.7654321, index.getGlobalDepth());
        assertEquals(4, newIdx4);

        // buckets = index.getBuckets();

        // {
        // Double[] arr = buckets[0].getKeys();
        // int sz = buckets[0].getSize();
        // boolean foundA = false, foundB = false;
        // for (int i = 0; i < sz; i++) {
        // if (arr[i].equals(1.0))
        // foundA = true;
        // if (arr[i].equals(8.6))
        // foundB = true;
        // }
        // assertTrue(foundA);
        // assertTrue(foundB);
        // }

        // {
        // Double[] arr = buckets[4].getKeys();
        // int sz = buckets[4].getSize();
        // boolean found = false;
        // for (int i = 0; i < sz; i++) {
        // if (arr[i].equals(98.7654321))
        // found = true;
        // }
        // assertTrue(found);
        // }

        index.insert(102938.4756, 8);

        int idx7_1 = HashingScheme.getDirectoryIndex(-68.402, index.getGlobalDepth());
        int idx7_2 = HashingScheme.getDirectoryIndex(102938.4756, index.getGlobalDepth());
        int idx3 = HashingScheme.getDirectoryIndex(246.8, index.getGlobalDepth());
        assertEquals(7, idx7_1);
        assertEquals(7, idx7_2);
        assertEquals(3, idx3);

        // {
        // Double[] arr = buckets[7].getKeys();
        // int sz = buckets[7].getSize();
        // boolean foundA = false, foundB = false;
        // for (int i = 0; i < sz; i++) {
        // if (arr[i].equals(-68.402))
        // foundA = true;
        // if (arr[i].equals(102938.4756))
        // foundB = true;
        // }
        // assertTrue(foundA);
        // assertTrue(foundB);
        // }

        // {
        // Double[] arr = buckets[3].getKeys();
        // int sz = buckets[3].getSize();
        // boolean found = false;
        // for (int i = 0; i < sz; i++) {
        // if (arr[i].equals(246.8))
        // found = true;
        // }
        // assertTrue(found);
        // }
    }

    @Test
    public void testCityIndexMapping() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<String> index = new ExtendibleHashing<>(String.class, "testAttr");

        index.insert("London", 1);
        index.insert("Delhi", 2);
        index.insert("Dubai", 3);
        index.insert("Mumbai", 4);
        index.insert("Istanbul", 5);
        index.insert("Kolkata", 6);
        index.insert("Zurich", 7);

        assertEquals(2, index.getGlobalDepth());
        assertEquals(4, index.getBucketCount());
        int idxLondon = HashingScheme.getDirectoryIndex("London", index.getGlobalDepth());
        int idxDelhi = HashingScheme.getDirectoryIndex("Delhi", index.getGlobalDepth());
        assertEquals(0, idxLondon);
        assertEquals(0, idxDelhi);
        assertSame(index.getBuckets()[idxLondon], index.getBuckets()[idxDelhi]);
        assertEquals(2, index.getLocalDepth(idxLondon));
        int idxDubai = HashingScheme.getDirectoryIndex("Dubai", index.getGlobalDepth());
        int idxMumbai = HashingScheme.getDirectoryIndex("Mumbai", index.getGlobalDepth());
        assertEquals(1, idxDubai);
        assertEquals(1, idxMumbai);
        assertSame(index.getBuckets()[idxDubai], index.getBuckets()[idxMumbai]);
        assertEquals(2, index.getLocalDepth(idxDubai));
        int idxIstanbul = HashingScheme.getDirectoryIndex("Istanbul", index.getGlobalDepth());
        assertEquals(2, idxIstanbul);
        assertEquals(2, index.getLocalDepth(idxIstanbul));
        int idxKolkata = HashingScheme.getDirectoryIndex("Kolkata", index.getGlobalDepth());
        int idxZurich = HashingScheme.getDirectoryIndex("Zurich", index.getGlobalDepth());
        assertEquals(3, idxKolkata);
        assertEquals(3, idxZurich);
        assertSame(index.getBuckets()[idxKolkata], index.getBuckets()[idxZurich]);
        assertEquals(2, index.getLocalDepth(idxKolkata));
        assertEquals(2, index.getGlobalDepth());
        index.insert("Cairo", 8);
        assertEquals(3, index.getGlobalDepth());
        int idxLondonNew = HashingScheme.getDirectoryIndex("London", index.getGlobalDepth());
        int idxCairo = HashingScheme.getDirectoryIndex("Cairo", index.getGlobalDepth());
        assertEquals(0, idxLondonNew);
        assertEquals(0, idxCairo);
        assertSame(index.getBuckets()[idxLondonNew], index.getBuckets()[idxCairo]);
        assertEquals(3, index.getLocalDepth(idxLondonNew));
        int idxDelhiNew = HashingScheme.getDirectoryIndex("Delhi", index.getGlobalDepth());
        assertEquals(4, idxDelhiNew);
        assertEquals(3, index.getLocalDepth(idxDelhiNew));

        index.insert("Toronto", 9);
        int idxDubaiNew = HashingScheme.getDirectoryIndex("Dubai", index.getGlobalDepth());
        int idxToronto = HashingScheme.getDirectoryIndex("Toronto", index.getGlobalDepth());
        assertEquals(1, idxDubaiNew);
        assertEquals(1, idxToronto);
        assertSame(index.getBuckets()[idxDubaiNew], index.getBuckets()[idxToronto]);
        assertEquals(3, index.getLocalDepth(idxDubaiNew));
        int idxMumbaiNew = HashingScheme.getDirectoryIndex("Mumbai", index.getGlobalDepth());
        assertEquals(5, idxMumbaiNew);
        assertEquals(3, index.getLocalDepth(idxMumbaiNew));

        index.insert("Beijing", 10);
        int idxIstanbulNew = HashingScheme.getDirectoryIndex("Istanbul", index.getGlobalDepth());
        int idxBeijing = HashingScheme.getDirectoryIndex("Beijing", index.getGlobalDepth());
        // assertEquals(2, idxIstanbulNew);
        // assertEquals(2, idxBeijing);
        assertSame(index.getBuckets()[idxIstanbulNew], index.getBuckets()[idxBeijing]);
        assertEquals(2, index.getLocalDepth(idxIstanbulNew));

        index.insert("Nairobi", 11);
        int idxIstanbulAfter = HashingScheme.getDirectoryIndex("Istanbul", index.getGlobalDepth());
        int idxNairobi = HashingScheme.getDirectoryIndex("Nairobi", index.getGlobalDepth());
        // assertEquals(2, idxIstanbulAfter);
        // assertEquals(2, idxNairobi);
        assertSame(index.getBuckets()[idxIstanbulAfter], index.getBuckets()[idxNairobi]);
        assertEquals(3, index.getLocalDepth(idxIstanbulAfter));
        int idxBeijingAfter = HashingScheme.getDirectoryIndex("Beijing", index.getGlobalDepth());
        assertEquals(6, idxBeijingAfter);
        assertEquals(3, index.getLocalDepth(idxBeijingAfter));

        index.insert("Paris", 12);
        index.insert("Sydney", 13);
        index.insert("Athens", 14);
        index.insert("Tokyo", 15);
        index.insert("Bangkok", 16);
        assertEquals(8, index.getBucketCount());
    }

    @Test
    public void testLocalDateIndexingScenario() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<LocalDate> index = new ExtendibleHashing<>(LocalDate.class, null);

        LocalDate d1 = LocalDate.of(1990, 9, 8);
        LocalDate d2 = LocalDate.of(1800, 2, 28);
        LocalDate d3 = LocalDate.of(1400, 12, 1);
        LocalDate d4 = LocalDate.of(2000, 2, 29);
        LocalDate d5 = LocalDate.of(2000, 10, 10);
        LocalDate d6 = LocalDate.of(2000, 6, 7);
        LocalDate d7 = LocalDate.of(2023, 11, 11);

        index.insert(d1, 1);
        index.insert(d2, 2);
        index.insert(d3, 3);
        index.insert(d4, 4);
        index.insert(d5, 5);
        index.insert(d6, 6);
        index.insert(d7, 7);

        assertEquals(2, index.getGlobalDepth());
        assertEquals(4, index.getBucketCount());
        int idx1_d1 = HashingScheme.getDirectoryIndex(d1, index.getGlobalDepth());
        int idx1_d2 = HashingScheme.getDirectoryIndex(d2, index.getGlobalDepth());
        assertEquals(0, idx1_d1);
        assertEquals(0, idx1_d2);
        assertEquals(2, index.getLocalDepth(0));
        int idx1_d3 = HashingScheme.getDirectoryIndex(d3, index.getGlobalDepth());
        int idx1_d4 = HashingScheme.getDirectoryIndex(d4, index.getGlobalDepth());
        assertEquals(1, idx1_d3);
        assertEquals(1, idx1_d4);
        assertEquals(2, index.getLocalDepth(1));
        int idx1_d5 = HashingScheme.getDirectoryIndex(d5, index.getGlobalDepth());
        assertEquals(2, idx1_d5);
        assertEquals(2, index.getLocalDepth(2));
        int idx1_d6 = HashingScheme.getDirectoryIndex(d6, index.getGlobalDepth());
        int idx1_d7 = HashingScheme.getDirectoryIndex(d7, index.getGlobalDepth());
        assertEquals(3, idx1_d6);
        assertEquals(3, idx1_d7);
        assertEquals(2, index.getLocalDepth(3));

        LocalDate d8 = LocalDate.of(1600, 10, 8);
        index.insert(d8, 8);
        assertEquals(3, index.getGlobalDepth());
        int idx2_d1 = HashingScheme.getDirectoryIndex(d1, index.getGlobalDepth());
        int idx2_d8 = HashingScheme.getDirectoryIndex(d8, index.getGlobalDepth());
        assertEquals(0, idx2_d1);
        assertEquals(0, idx2_d8);
        assertEquals(3, index.getLocalDepth(0));
        int idx2_d2 = HashingScheme.getDirectoryIndex(d2, index.getGlobalDepth());
        assertEquals(4, idx2_d2);
        assertEquals(3, index.getLocalDepth(4));

        LocalDate d9 = LocalDate.of(2008, 1, 1);
        index.insert(d9, 9);
        int idx2_d3 = HashingScheme.getDirectoryIndex(d3, index.getGlobalDepth());
        int idx2_d9 = HashingScheme.getDirectoryIndex(d9, index.getGlobalDepth());
        assertEquals(1, idx2_d3);
        assertEquals(1, idx2_d9);
        assertEquals(3, index.getLocalDepth(1));
        int idx2_d4 = HashingScheme.getDirectoryIndex(d4, index.getGlobalDepth());
        assertEquals(5, idx2_d4);
        assertEquals(3, index.getLocalDepth(5));

        LocalDate d10 = LocalDate.of(2020, 6, 6);
        index.insert(d10, 10);
        int idx2_d5 = HashingScheme.getDirectoryIndex(d5, index.getGlobalDepth());
        int idx2_d10 = HashingScheme.getDirectoryIndex(d10, index.getGlobalDepth());
        // assertEquals(2, idx2_d5);
        // assertEquals(2, idx2_d10);
        assertEquals(2, index.getLocalDepth(2));

        LocalDate d11 = LocalDate.of(2022, 2, 2);
        index.insert(d11, 11);
        int idx2_d5_after = HashingScheme.getDirectoryIndex(d5, index.getGlobalDepth());
        int idx2_d11 = HashingScheme.getDirectoryIndex(d11, index.getGlobalDepth());
        assertEquals(2, idx2_d5_after);
        assertEquals(2, idx2_d11);
        assertEquals(3, index.getLocalDepth(2));
        int idx2_d10_new = HashingScheme.getDirectoryIndex(d10, index.getGlobalDepth());
        assertEquals(6, idx2_d10_new);
        assertEquals(3, index.getLocalDepth(6));

        LocalDate d12 = LocalDate.of(2024, 2, 29);
        LocalDate d13 = LocalDate.of(2009, 9, 11);
        // LocalDate d14 = LocalDate.of(1700, 2, 29);
        LocalDate d15 = LocalDate.of(2005, 5, 22);
        LocalDate d16 = LocalDate.of(2005, 8, 23);
        index.insert(d12, 12);
        index.insert(d13, 13);
        // index.insert(d14, 14);
        index.insert(d15, 15);
        index.insert(d16, 16);
        assertEquals(8, index.getBucketCount());
    }
}
