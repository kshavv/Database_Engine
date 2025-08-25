package in.ac.iitd.db362.BplusTree;
//package in.ac.iitd.db362.index.bplustree;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import org.junit.jupiter.api.Test;
import in.ac.iitd.db362.parser.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BPlusTreeIndexTest {
    private static BPlusTreeIndex<Double> createSampleBPlusTreeDouble() {
        BPlusTreeIndex<Double> bPlusTree = new BPlusTreeIndex<>(Double.class, "testAttribute");
        bPlusTree.insert(1.0, 1);
        bPlusTree.insert(2.0, 2);
        bPlusTree.insert(3.0, 3);
        bPlusTree.insert(4.0, 4);
        bPlusTree.insert(4.0, 14); // Duplicate key
        bPlusTree.insert(5.0, 5);
        bPlusTree.insert(6.0, 6);
        bPlusTree.insert(6.5, 65);
        bPlusTree.insert(6.5, 165); // Duplicate key
        bPlusTree.insert(7.0, 7);
        bPlusTree.insert(7.5, 75);  // New key
        bPlusTree.insert(7.5, 175); // New duplicate key
        bPlusTree.insert(7.5, 175); // New duplicate key
        bPlusTree.insert(7.5, 175); // New duplicate key
        bPlusTree.insert(7.5, 175); // New duplicate key
        bPlusTree.insert(8.0, 8);
        bPlusTree.insert(9.0, 9);   // New key
        bPlusTree.insert(9.5, 95);  // New key
        bPlusTree.insert(10.0, 100); // New key
        bPlusTree.insert(10.0, 200); // New duplicate key


        System.out.println(bPlusTree.getAllKeys());
        
        System.out.println(bPlusTree.getHeight());

        String visualization = bPlusTree.visualize();
        System.out.println(visualization);

        return bPlusTree;
    }
    private static BPlusTreeIndex<String> createSampleBPlusTreeString() {
        BPlusTreeIndex<String> bPlusTree = new BPlusTreeIndex<>(String.class, "testAttribute");
        bPlusTree.insert("1", 1);
        bPlusTree.insert("2", 2);
        bPlusTree.insert("3", 3);
        bPlusTree.insert("4", 4);
        bPlusTree.insert("5", 5);
        bPlusTree.insert("5", 51);

        bPlusTree.insert("6", 6);
        bPlusTree.insert("7", 7);
        bPlusTree.insert("8", 8);
        bPlusTree.insert("9", 11);
        bPlusTree.insert("10", 22);

        // Keys with duplicates
        bPlusTree.insert("11", 3);
        bPlusTree.insert("11", 13);
        bPlusTree.insert("11", 113);
        bPlusTree.insert("11", 1113);
        bPlusTree.insert("11", 11113);

        bPlusTree.insert("12", 14);
        bPlusTree.insert("12", 114);
        bPlusTree.insert("12", 1114);

        bPlusTree.insert("13", 4);
        bPlusTree.insert("14", 41);
        bPlusTree.insert("15", 42);
        bPlusTree.insert("16", 43);
        System.out.println(bPlusTree.getAllKeys());

        System.out.println(bPlusTree.getHeight());


        String visualization = bPlusTree.visualize();
        System.out.println(visualization);

        return bPlusTree;
    }
    private static BPlusTreeIndex<Integer> createSampleBPlusTree() {
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        bPlusTree.insert(1, 1);
        bPlusTree.insert(2, 2);
        bPlusTree.insert(3, 3);
        bPlusTree.insert(4, 4);
        bPlusTree.insert(5, 5);
        bPlusTree.insert(6, 6);
        bPlusTree.insert(7, 7);
        bPlusTree.insert(8, 8);
        bPlusTree.insert(9, 1);
        bPlusTree.insert(10, 2);

        // Keys with duplicates
        bPlusTree.insert(11, 3);
        bPlusTree.insert(11, 13);
        bPlusTree.insert(11, 113);
        bPlusTree.insert(11, 1113);
        bPlusTree.insert(11, 11113);

        bPlusTree.insert(12, 14);
        bPlusTree.insert(12, 114);
        bPlusTree.insert(12, 1114);

        bPlusTree.insert(13, 4);
        bPlusTree.insert(14, 41);
        bPlusTree.insert(15, 42);
        bPlusTree.insert(16, 43);

        System.out.println(bPlusTree.getAllKeys());
        System.out.println(bPlusTree.getHeight());


        String visualization = bPlusTree.visualize();
        System.out.println(visualization);

        return bPlusTree;
    }
    private static BPlusTreeIndex<LocalDate> createSampleBPlusTreeDate() {
        BPlusTreeIndex<LocalDate> bPlusTree = new BPlusTreeIndex<>(LocalDate.class, "testAttribute");

        bPlusTree.insert(LocalDate.of(2023, 1, 1), 101);
        bPlusTree.insert(LocalDate.of(2023, 1, 2), 102);
        bPlusTree.insert(LocalDate.of(2023, 1, 3), 103);
        bPlusTree.insert(LocalDate.of(2023, 1, 4), 104);
        bPlusTree.insert(LocalDate.of(2023, 1, 5), 105);

        // Add some duplicate keys
        bPlusTree.insert(LocalDate.of(2023, 1, 5), 205);
        bPlusTree.insert(LocalDate.of(2023, 1, 5), 305);

        // More entries
        bPlusTree.insert(LocalDate.of(2023, 1, 6), 106);
        bPlusTree.insert(LocalDate.of(2023, 1, 7), 107);
        bPlusTree.insert(LocalDate.of(2023, 1, 8), 108);

        // Print details of the tree
        System.out.println(bPlusTree.getAllKeys());  // Displays all keys in the tree
        System.out.println(bPlusTree.getHeight());   // Displays height of the tree

        // Visualize the tree structure
        String visualization = bPlusTree.visualize();
        System.out.println(visualization);

        return bPlusTree;
    }
    


    /**test cases for search method*/
    @Test
    void testSearchExistingKey() {
        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();

        List<Integer> result = bPlusTree.search(11);
        assertEquals(5, result.size());
        assertTrue(result.contains(3));
        assertTrue(result.contains(113));
        assertTrue(result.contains(113));
        assertTrue(result.contains(1113));
        assertTrue(result.contains(11113));

    }

    @Test
    void testSearchNonExistentKey() {
        BPlusTreeIndex<Integer> bPlusTreeIndex = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        bPlusTreeIndex.insert(10, 1);
        bPlusTreeIndex.insert(20, 2);
        bPlusTreeIndex.insert(30, 3);

        List<Integer> result = bPlusTreeIndex.search(40);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchEmptyTree() {
        BPlusTreeIndex<Integer> bPlusTreeIndex = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        List<Integer> result = bPlusTreeIndex.search(10);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchDuplicateKeysWithOverflow() {
        BPlusTreeIndex<Integer> bPlusTreeIndex = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        for (int i = 0; i < 10; i++) {
            bPlusTreeIndex.insert(15, i);
        }

        List<Integer> result = bPlusTreeIndex.search(15);
        assertEquals(10, result.size());
        for (int i = 0; i < 10; i++) {
            assertTrue(result.contains(i));
        }
    }

    /**
     * Tests the search functionality of the BPlusTreeIndex after a split operation occurs due to multiple insertions.
     *
     * This test inserts multiple key-value pairs into the BPlusTreeIndex, ensuring that the structure undergoes a split.
     * It then verifies that a search for a specific key returns the correct associated value and maintains the tree's integrity.
     */
    @Test
    void testSearchAfterSplit() {
        BPlusTreeIndex<Integer> bPlusTreeIndex = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        bPlusTreeIndex.insert(10, 1);
        bPlusTreeIndex.insert(20, 2);
        bPlusTreeIndex.insert(30, 3);
        bPlusTreeIndex.insert(40, 4);

        List<Integer> result = bPlusTreeIndex.search(30);
        assertEquals(1, result.size());
        assertTrue(result.contains(3));
    }

    @Test
    void testSearchAcrossMultipleNodes() {
        BPlusTreeIndex<Integer> bPlusTreeIndex = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        bPlusTreeIndex.insert(10, 1);
        bPlusTreeIndex.insert(20, 2);
        bPlusTreeIndex.insert(30, 3);
        bPlusTreeIndex.insert(40, 4);
        bPlusTreeIndex.insert(50, 5);

        List<Integer> result = bPlusTreeIndex.search(40);
        assertEquals(1, result.size());
        assertTrue(result.contains(4));

        result = bPlusTreeIndex.search(50);
        assertEquals(1, result.size());
        assertTrue(result.contains(5));


    }



    /**
     * rangeQuery method test cases
     */
    @Test
    void testRangeQuery() {
        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();

        // Test range query [3, 12] inclusive
        List<Integer> result = bPlusTree.rangeQuery(3, true, 12, true);
        assertEquals(16, result.size());
        assertTrue(result.contains(3)); // Check values within range
        assertTrue(result.contains(1114)); // Check overflow values

        // Test range query (3, 12) exclusive
        result = bPlusTree.rangeQuery(3, false, 12, false);
        assertEquals(12, result.size());

        // Test range query [3, 12) - start inclusive, end exclusive
        result = bPlusTree.rangeQuery(3, true, 12, false);
        assertEquals(13, result.size());

        //Test range query [2,5]
        result = bPlusTree.rangeQuery(2, true, 5, true);
        assertEquals(4, result.size());
    }

    @Test
    void testRangeQueryEdgeCases() {
        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();

        // Test range query with no lower bound (null, 11]
        List<Integer> result = bPlusTree.rangeQuery(null, false, 11, true);
        assertEquals(15, result.size());

        // Test range query with no upper bound [12, null)
        result = bPlusTree.rangeQuery(12, true, null, false);
        assertEquals(7, result.size());

        // Test range query with no upper bound (12, null)
        result = bPlusTree.rangeQuery(12, false, null, false);
        assertEquals(4, result.size());

        // Test range query with no upper bound (12, null) endinclusive: true
        result = bPlusTree.rangeQuery(12, false, null, true);
        assertEquals(4, result.size());
    }

    @Test
    void testRangeQueryEmptyTree() {
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");
        List<Integer> result = bPlusTree.rangeQuery(10, true, 20, true);
        assertTrue(result.isEmpty());
    }



    /**
     * evaluate function test cases
     */

    @Test
    void testEvaluateEquals() {
        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();
        QueryNode equalsNode = new QueryNode(Operator.EQUALS, "testAttribute", "12");
        List<Integer> result = bPlusTree.evaluate(equalsNode);

        assertEquals(3, result.size());

        //handle for double and string here
        BPlusTreeIndex<Double> bPlusTreeDouble = createSampleBPlusTreeDouble();
        QueryNode doubleEqualsNode = new QueryNode(Operator.EQUALS, "testAttribute", "6.5");
        List<Integer> doubleResult = bPlusTreeDouble.evaluate(doubleEqualsNode);
        assertEquals(2, doubleResult.size());
        assertTrue(doubleResult.contains(65));
        assertTrue(doubleResult.contains(165));

        BPlusTreeIndex<String> bPlusTreeString = createSampleBPlusTreeString();
        QueryNode stringEqualsNode = new QueryNode(Operator.EQUALS, "testAttribute", "5");
        List<Integer> stringResult = bPlusTreeString.evaluate(stringEqualsNode);
        assertEquals(2, stringResult.size());
        assertTrue(stringResult.contains(5));
        assertTrue(stringResult.contains(51));

        // Handle for LocalDate type
        BPlusTreeIndex<LocalDate> bPlusTreeDate = createSampleBPlusTreeDate();
        QueryNode dateEqualsNode = new QueryNode(Operator.EQUALS, "testAttribute", "2023-01-05");
        List<Integer> dateResult = bPlusTreeDate.evaluate(dateEqualsNode);
        assertEquals(3, dateResult.size());
        assertTrue(dateResult.contains(105));
        assertTrue(dateResult.contains(205));
        assertTrue(dateResult.contains(305));
        
    }

    @Test
    void testEvaluateLessThan() {
        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();


        QueryNode ltNode = new QueryNode(Operator.LT, "testAttribute", "12");
        List<Integer> result = bPlusTree.evaluate(ltNode);

        assertEquals(15, result.size());

        // Handle for double type
        BPlusTreeIndex<Double> bPlusTreeDouble = createSampleBPlusTreeDouble();
        QueryNode doubleLtNode = new QueryNode(Operator.LT, "testAttribute", "7.0");
        List<Integer> doubleResult = bPlusTreeDouble.evaluate(doubleLtNode);
        assertEquals(9, doubleResult.size());
        assertTrue(doubleResult.contains(165));

        // Handle for string type
        BPlusTreeIndex<String> bPlusTreeString = createSampleBPlusTreeString();
        QueryNode stringLtNode = new QueryNode(Operator.LT, "testAttribute", "10");
        List<Integer> stringResult = bPlusTreeString.evaluate(stringLtNode);
        assertEquals(1, stringResult.size());
        assertTrue(stringResult.contains(1));
    }

    @Test
    void testEvaluateGreaterThan() {

        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();

        QueryNode gtNode = new QueryNode(Operator.GT, "testAttribute", "11");
        List<Integer> result = bPlusTree.evaluate(gtNode);

        assertEquals(7, result.size());

        // Handle for double type
        BPlusTreeIndex<Double> bPlusTreeDouble = createSampleBPlusTreeDouble();
        QueryNode doubleGtNode = new QueryNode(Operator.GT, "testAttribute", "6.5");
        List<Integer> doubleResult = bPlusTreeDouble.evaluate(doubleGtNode);
        assertEquals(11, doubleResult.size());
        assertTrue(doubleResult.contains(7));
        assertTrue(doubleResult.contains(75));
        assertTrue(doubleResult.contains(175));

        // Handle for string type
        BPlusTreeIndex<String> bPlusTreeString = createSampleBPlusTreeString();
        QueryNode stringGtNode = new QueryNode(Operator.GT, "testAttribute", "10");
        List<Integer> stringResult = bPlusTreeString.evaluate(stringGtNode);
        assertEquals(21, stringResult.size());
//        assertTrue(stringResult.contains(13));
//        assertTrue(stringResult.contains(15));
    }

    @Test
    void testEvaluateRange() {
        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();

        QueryNode rangeNode = new QueryNode(Operator.RANGE, "testAttribute", "11");
        rangeNode.secondValue = "15";

        List<Integer> result = bPlusTree.evaluate(rangeNode);

        assertEquals(5, result.size());
        //        assertTrue(result.contains(2));
        //        assertTrue(result.contains(3));

        // Test range evaluation for Double values
        BPlusTreeIndex<Double> bPlusTreeDouble = createSampleBPlusTreeDouble();

        QueryNode doubleRangeNode = new QueryNode(Operator.RANGE, "testAttribute", "6.0");
        doubleRangeNode.secondValue = "8.0";

        List<Integer> doubleResult = bPlusTreeDouble.evaluate(doubleRangeNode);

        assertEquals(8, doubleResult.size());
        //        assertTrue(doubleResult.contains(6.0));
        //        assertTrue(doubleResult.contains(7.5));

        // Similarly, handle for string as well
        BPlusTreeIndex<String> bPlusTreeString = createSampleBPlusTreeString();
        QueryNode stringRangeNode = new QueryNode(Operator.RANGE, "testAttribute", "1");
        stringRangeNode.secondValue = "12";
        List<Integer> stringResult = bPlusTreeString.evaluate(stringRangeNode);
        assertEquals(6, stringResult.size());
        //        assertTrue(stringResult.contains(1));
        //        assertTrue(stringResult.contains(114));

        // Test range evaluation for LocalDate values
        BPlusTreeIndex<LocalDate> bPlusTreeDate = createSampleBPlusTreeDate();

        QueryNode dateRangeNode = new QueryNode(Operator.RANGE, "testAttribute", "2023-01-03");
        dateRangeNode.secondValue = "2023-01-06";

        List<Integer> dateResult = bPlusTreeDate.evaluate(dateRangeNode);

        assertEquals(4, dateResult.size());
        assertTrue(dateResult.contains(104));
        assertTrue(dateResult.contains(105));
        assertTrue(dateResult.contains(205));
        assertTrue(dateResult.contains(305));

    }

    @Test
    void testEvaluateWrongAttribute() {
        //check when tree is empty
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");
//        bPlusTree.insert(10, 1);
//        bPlusTree.insert(20, 2);

        QueryNode wrongAttributeNode = new QueryNode(Operator.EQUALS, "testAttribute", "20");
        List<Integer> result = bPlusTree.evaluate(wrongAttributeNode);

        assertTrue(result.isEmpty());
    }

    @Test
    void testEvaluateUnsupportedOperator() {
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");
        bPlusTree.insert(10, 1);
        bPlusTree.insert(20, 2);

        QueryNode unsupportedNode = new QueryNode(Operator.AND, "testAttribute", "20");
        List<Integer> result = bPlusTree.evaluate(unsupportedNode);

        assertTrue(result.isEmpty());
    }


    /**
     * Test for the getHeight method in various scenarios.
     */
    @Test
    void testHeightEmptyTree() {
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        // Height should be -1 for an empty tree (no levels).
        assertEquals(0, bPlusTree.getHeight());
    }

    @Test
    void testHeightSingleLevelTree() {
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        // Insert a single node in the tree
        bPlusTree.insert(10, 1);

        // Height should be 0 (root node is a leaf node).
        assertEquals(0, bPlusTree.getHeight());
    }

    @Test
    void testHeightMultiLevelTree() {
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");
        // Insert multiple nodes to cause the tree to grow in height
        for (int i = 1; i <= 30; i++) {
            bPlusTree.insert(i, i);
        }

        String visualization = bPlusTree.visualize();
        System.out.println(visualization);
        // Since the ORDER is 4, and more than 16 insertions will cause at least 2 splits,
        // the height should now be 2 (root -> internal -> leaf).
        assertEquals(3, bPlusTree.getHeight());
    }


    @Test
    void testEmptyTreeOperations() {
        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");

        // Test search on empty tree
        assertTrue(bPlusTree.search(1).isEmpty(), "Search on empty tree should return empty list");

        // Test rangeQuery on empty tree
        assertTrue(bPlusTree.rangeQuery(1, true, 10, true).isEmpty(), "Range query on empty tree should return empty list");

        // Test getHeight on empty tree
        assertEquals(0, bPlusTree.getHeight(), "Height of an empty tree should be 0");

        // Test getAllKeys on empty tree
        assertTrue(bPlusTree.getAllKeys().isEmpty(), "getAllKeys on empty tree should return an empty list");
    }

    @Test
    void testDeleteSingleKey() {
        BPlusTreeIndex<Integer> bPlusTree = createSampleBPlusTree();

        // Delete an existing key
        boolean isDeleted = bPlusTree.delete(11);
        assertTrue(isDeleted);

        // Verify the key is deleted
        List<Integer> result = bPlusTree.search(11);
        assertTrue(result.isEmpty());

        // Verify the tree structure remains valid
        List<Integer> allKeys = bPlusTree.getAllKeys();
        assertFalse(allKeys.contains(11));

        String visualization = bPlusTree.visualize();
        System.out.println(visualization);
    }
//
//    @Test
//    void testDeleteKeyWithDuplicates() {
//        BPlusTreeIndex<Double> bPlusTree = createSampleBPlusTreeDouble();
//
//        // Delete a key with duplicates
//        boolean isDeleted = bPlusTree.delete(6.5);
//        assertTrue(isDeleted);
//
//        // Verify the key is deleted
//        List<Integer> result = bPlusTree.search(6.5);
//        assertTrue(result.isEmpty());
//
//        // Verify the tree structure is still valid
//        List<Double> allKeys = bPlusTree.getAllKeys();
//        assertFalse(allKeys.contains(6.5));
//    }
//
//    @Test
//    void testDeleteNonExistentKey() {
//        BPlusTreeIndex<String> bPlusTree = createSampleBPlusTreeString();
//
//        // Attempt to delete a non-existent key
//        boolean isDeleted = bPlusTree.delete("100");
//        assertFalse(isDeleted);
//
//        // Verify the tree structure is unaffected
//        List<String> allKeys = bPlusTree.getAllKeys();
//        assertEquals(20, allKeys.size()); // No change to keys
//    }
//
//    @Test
//    void testDeleteCausingUnderflow() {
//        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");
//
//        // Insert keys to create a multi-level tree
//        for (int i = 1; i <= 10; i++) {
//            bPlusTree.insert(i, i);
//        }
//
//        // Cause an underflow by deleting keys
//        for (int i = 10; i >= 6; i--) {
//            bPlusTree.delete(i);
//        }
//
//        // Verify remaining keys
//        List<Integer> allKeys = bPlusTree.getAllKeys();
//        assertEquals(5, allKeys.size());
//        assertFalse(allKeys.contains(6));
//    }
//
//    @Test
//    void testDeleteFromEmptyTree() {
//        BPlusTreeIndex<Integer> bPlusTree = new BPlusTreeIndex<>(Integer.class, "testAttribute");
//
//        // Attempt to delete a key from an empty tree
//        boolean isDeleted = bPlusTree.delete(1);
//        assertFalse(isDeleted);
//
//        // Verify the tree remains empty
//        assertTrue(bPlusTree.getAllKeys().isEmpty());
//    }
}