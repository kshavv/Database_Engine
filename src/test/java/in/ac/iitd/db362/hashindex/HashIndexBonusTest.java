package in.ac.iitd.db362.hashindex;

import in.ac.iitd.db362.index.hashindex.Bucket;
import in.ac.iitd.db362.index.hashindex.ExtendibleHashing;
import in.ac.iitd.db362.index.hashindex.HashingScheme;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashIndexBonusTest {

    @Test
    @Tag("bonus")
    public void testBasic_IntegerDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, null);
        index.insert(4, 1);
        index.insert(5, 2);
        index.insert(6, 3);
        index.insert(7, 4);
        index.delete(5);

        assertNotNull(index.search(4));
        assertEquals(index.search(4).size(), 1);
        if (index.search(5) != null) assertEquals(index.search(5).size(), 0);
        assertNotNull(index.search(6));
        assertEquals(index.search(6).size(), 1);
        assertNotNull(index.search(7));
        assertEquals(index.search(7).size(), 1);
        if (index.search(8) != null) assertEquals(index.search(8).size(), 0);
        assertEquals(index.search(4).get(0), 1);
        assertEquals(index.search(6).get(0), 3);
        assertEquals(index.search(7).get(0), 4);
    }

    @Test
    @Tag("bonus")
    public void testBasic_DoubleDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Double> index = new ExtendibleHashing<>(Double.class, null);
        index.insert(4.1, 1);
        index.insert(2.1, 2);
        index.insert(3.71, 3);
        index.insert(7.4, 4);
        index.delete(3.71);

        assertNotNull(index.search(4.1));
        assertNotNull(index.search(2.1));
        assertNotNull(index.search(7.4));
        assertEquals(index.search(4.1).size(), 1);
        assertEquals(index.search(2.1).size(), 1);
        if (index.search(3.71) != null) assertEquals(index.search(3.71).size(), 0);
        assertEquals(index.search(7.4).size(), 1);
        if (index.search(4.6) != null) assertEquals(index.search(4.6).size(), 0);
        assertEquals(index.search(4.1).get(0), 1);
        assertEquals(index.search(2.1).get(0), 2);
        assertEquals(index.search(7.4).get(0), 4);
    }

    @Test
    @Tag("bonus")
    public void testBasic_StringDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<String> index = new ExtendibleHashing<>(String.class, null);
        index.insert("Vande", 1);
        index.insert("Mataram", 2);
        index.insert("Jai", 3);
        index.insert("Hind", 4);
        index.delete("Vande");

        if (index.search("Vande") != null) assertEquals(index.search("Vande").size(), 0);
        assertNotNull(index.search("Mataram"));
        assertEquals(index.search("Mataram").size(), 1);
        assertNotNull(index.search("Jai"));
        assertEquals(index.search("Jai").size(), 1);
        assertNotNull(index.search("Hind"));
        assertEquals(index.search("Hind").size(), 1);
        if (index.search("DBMS") != null) assertEquals(index.search("DBMS").size(), 0);
        assertEquals(index.search("Mataram").get(0), 2);
        assertEquals(index.search("Jai").get(0), 3);
        assertEquals(index.search("Hind").get(0), 4);
    }

    @Test
    @Tag("bonus")
    public void testBasic_LocalDateDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<LocalDate> index = new ExtendibleHashing<>(LocalDate.class, null);
        index.insert(LocalDate.parse("2025-01-04"), 1);
        index.insert(LocalDate.parse("2025-01-01"), 2);
        index.insert(LocalDate.parse("2025-01-02"), 3);
        index.insert(LocalDate.parse("2025-01-03"), 4);
        index.delete(LocalDate.parse("2025-01-03"));

        assertNotNull(index.search(LocalDate.parse("2025-01-04")));
        assertEquals(index.search(LocalDate.parse("2025-01-04")).size(), 1);
        assertNotNull(index.search(LocalDate.parse("2025-01-01")));
        assertEquals(index.search(LocalDate.parse("2025-01-01")).size(), 1);
        assertNotNull(index.search(LocalDate.parse("2025-01-02")));
        assertEquals(index.search(LocalDate.parse("2025-01-02")).size(), 1);
        if (index.search(LocalDate.parse("2025-01-03")) != null) assertEquals(index.search(LocalDate.parse("2025-01-03")).size(), 0);
        if (index.search(LocalDate.parse("2025-01-05")) != null) assertEquals(index.search(LocalDate.parse("2025-01-05")).size(), 0);
        assertEquals(index.search(LocalDate.parse("2025-01-04")).get(0), 1);
        assertEquals(index.search(LocalDate.parse("2025-01-01")).get(0), 2);
        assertEquals(index.search(LocalDate.parse("2025-01-02")).get(0), 3);
    }

    @Test
    @Tag("bonus")
    public void testBasic_DuplicateDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        index.insert(4, 1);
        index.insert(4, 2);
        index.insert(4, 3);
        index.delete(4);

        if (index.search(4) != null) assertEquals(index.search(4).size(), 0);
        if (index.search(5) != null) assertEquals(index.search(5).size(), 0);
    }

    @Test
    @Tag("bonus")
    public void testIntermediate_BucketSplittingDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 2);
        assertEquals(index.getBucketCount(), 4);
        index.insert(4, 1);
        index.insert(1, 4);
        index.insert(8, 2);
        index.insert(12, 3);

        index.delete(4);

        assertNotNull(index.search(1));
        assertEquals(index.search(1).size(), 1);
        if (index.search(4) != null) assertEquals(index.search(4).size(), 0);
        assertNotNull(index.search(8));        
        assertEquals(index.search(8).size(), 1);
        assertNotNull(index.search(12));
        assertEquals(index.search(12).size(), 1);
        if (index.search(5) != null) assertEquals(index.search(5).size(), 0);
        assertEquals(index.search(1).get(0), 4);
        assertEquals(index.search(8).get(0), 2);
        assertEquals(index.search(12).get(0), 3);
    }

    @Test
    @Tag("bonus")
    public void testIntermediate_DuplicateBucketSplittingDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 2;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 2);
        assertEquals(index.getBucketCount(), 4);
        index.insert(4, 1);
        index.insert(8, 2);
        index.insert(4, 3);

        index.delete(4);

        if (index.search(4) != null) assertEquals(index.search(4).size(), 0);
        assertNotNull(index.search(8));
        assertEquals(index.search(8).size(), 1);
        assertNotNull(index.search(12));
        assertEquals(index.search(12).size(), 0);
        assertEquals(index.search(8).get(0), 2);
    }

    @Test
    @Tag("bonus")
    public void testIntermediate_DoubleBucketSplittingDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 1;
        ExtendibleHashing.BUCKET_SIZE = 2;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 1);
        assertEquals(index.getBucketCount(), 2);
        index.insert(4, 1);
        index.insert(8, 2);
        index.insert(12, 3);

        index.delete(12);

        assertNotNull(index.search(4));
        assertEquals(index.search(4).size(), 1);
        assertNotNull(index.search(8));
        assertEquals(index.search(8).size(), 1);
        if (index.search(12) != null) assertEquals(index.search(12).size(), 0);
        if (index.search(16) != null) assertEquals(index.search(16).size(), 0);
        assertEquals(index.search(4).get(0), 1);
        assertEquals(index.search(8).get(0), 2);
    }

    @Test
    @Tag("bonus")
    public void testIntermediate_OverflowBucketSplittingDelete() {
        ExtendibleHashing.INITIAL_GLOBAL_DEPTH = 1;
        ExtendibleHashing.BUCKET_SIZE = 1;
        ExtendibleHashing<Integer> index = new ExtendibleHashing<>(Integer.class, "age");
        assertEquals(index.getGlobalDepth(), 1);
        assertEquals(index.getBucketCount(), 2);
        index.insert(16, 1);
        index.insert(16, 3);
        index.insert(18, 2);

        index.delete(16);

        if (index.search(16) != null) assertEquals(index.search(16).size(), 0);
        assertNotNull(index.search(18));
        assertEquals(index.search(18).size(), 1);
        if (index.search(64) != null) assertEquals(index.search(64).size(), 0);
        assertEquals(index.search(18).get(0), 2);
    }
}
