package in.ac.iitd.db362.index;

import in.ac.iitd.db362.parser.QueryNode;
import in.ac.iitd.db362.parser.Operator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the BitmapIndex class.
 * The evaluate method is tested to ensure it processes QueryNode and performs the search as expected.
 */
public class BitmapIndexTest {

    @Test
    public void testEvaluateForSingleValueMatch() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 100);
        index.insert("value1", 5);
        index.insert("value1", 20);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "value1");
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(5, 20), result, "The evaluate method should return rowIds [5, 20] for the given key.");
    }

    @Test
    public void testEvaluateForSingleValueMatchWithInteger() {
        BitmapIndex<Integer> index = new BitmapIndex<>(Integer.class, "testAttribute", 100);
        index.insert(42, 5);
        index.insert(42, 20);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "42");
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(5, 20), result, "The evaluate method should return rowIds [5, 20] for the given key.");
    }

    @Test
    public void testEvaluateForSingleValueMatchWithDouble() {
        BitmapIndex<Double> index = new BitmapIndex<>(Double.class, "testAttribute", 100);
        index.insert(42.5, 5);
        index.insert(42.5, 20);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "42.5");
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(5, 20), result, "The evaluate method should return rowIds [5, 20] for the given double value.");
    }

    @Test
    public void testEvaluateForSingleValueMatchWithLocalDate() {
        BitmapIndex<LocalDate> index = new BitmapIndex<>(LocalDate.class, "testAttribute", 100);
        index.insert(LocalDate.of(2023, 10, 10), 5);
        index.insert(LocalDate.of(2023, 10, 10), 20);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", LocalDate.of(2023, 10, 10).toString());
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(5, 20), result, "The evaluate method should return rowIds [5, 20] for the given date.");
    }

    @Test
    public void testEvaluateForNoMatch() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 100);
        index.insert("value1", 5);
        index.insert("value1", 20);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "value2");
        List<Integer> result = index.evaluate(queryNode);

        assertTrue(result.isEmpty(), "The evaluate method should return an empty list when no match is found.");
    }

    @Test
    public void testEvaluateForNoMatchWithInteger() {
        BitmapIndex<Integer> index = new BitmapIndex<>(Integer.class, "testAttribute", 100);
        index.insert(42, 5);
        index.insert(42, 20);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "99");
        List<Integer> result = index.evaluate(queryNode);

        assertTrue(result.isEmpty(), "The evaluate method should return an empty list when no match is found.");
    }

    @Test
    public void testEvaluateForNoMatchWithDouble() {
        BitmapIndex<Double> index = new BitmapIndex<>(Double.class, "testAttribute", 100);
        index.insert(42.5, 5);
        index.insert(42.5, 20);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "99.9");

        List<Integer> result = index.evaluate(queryNode);
        assertTrue(result.isEmpty(), "The evaluate method should return an empty list when no match is found.");
    }

    @Test
    public void testEvaluateForEmptyIndex() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 100);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "value1");
        List<Integer> result = index.evaluate(queryNode);

        assertTrue(result.isEmpty(), "The evaluate method should return an empty list for an empty index.");
    }

    @Test
    public void testEvaluateForEmptyIndexWithInteger() {
        BitmapIndex<Integer> index = new BitmapIndex<>(Integer.class, "testAttribute", 100);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "42");
        List<Integer> result = index.evaluate(queryNode);

        assertTrue(result.isEmpty(), "The evaluate method should return an empty list for an empty index.");
    }

    @Test
    public void testEvaluateForEmptyIndexWithLocalDate() {
        BitmapIndex<LocalDate> index = new BitmapIndex<>(LocalDate.class, "testAttribute", 100);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", LocalDate.of(2023, 10, 10).toString());
        List<Integer> result = index.evaluate(queryNode);

        assertTrue(result.isEmpty(), "The evaluate method should return an empty list for an empty index.");
    }

    @Test
    public void testEvaluateStringValues() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 100);
        index.insert("value1", 10);
        index.insert("value2", 30);
        index.insert("value1", 50);

        // Test multiple identical keys
        QueryNode queryForValue1 = new QueryNode(Operator.EQUALS, "testAttribute", "value1");
        List<Integer> resultForValue1 = index.evaluate(queryForValue1);
        assertEquals(List.of(10, 50), resultForValue1, "The evaluate method should return rowIds [10, 50] for key 'value1'.");

        // Test single unique key
        QueryNode queryForValue2 = new QueryNode(Operator.EQUALS, "testAttribute", "value2");
        List<Integer> resultForValue2 = index.evaluate(queryForValue2);
        assertEquals(List.of(30), resultForValue2, "The evaluate method should return rowIds [30] for key 'value2'.");

        // Test for empty string key
        index.insert("", 40);
        QueryNode queryForEmptyString = new QueryNode(Operator.EQUALS, "testAttribute", "");
        List<Integer> resultForEmptyString = index.evaluate(queryForEmptyString);
        assertEquals(List.of(40), resultForEmptyString, "The evaluate method should return rowIds [40] for an empty string key.");

//        // Test for null key
//        index.insert(null, 60);
//        QueryNode queryForNull = new QueryNode(Operator.EQUALS, "testAttribute", null);
//        List<Integer> resultForNull = index.evaluate(queryForNull);
//        assertTrue(resultForNull.isEmpty(), "The evaluate method should return an empty list for a null key.");
    }

    @Test
    public void testEvaluateForMultipleKeysInIndexWithInteger() {
        BitmapIndex<Integer> index = new BitmapIndex<>(Integer.class, "testAttribute", 100);
        index.insert(42, 10);
        index.insert(99, 30);
        index.insert(42, 50);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "42");
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(10, 50), result, "The evaluate method should return rowIds [10, 50] for key '42'.");
    }

    @Test
    public void testEvaluateForMultipleKeysInIndexWithLocalDate() {
        BitmapIndex<LocalDate> index = new BitmapIndex<>(LocalDate.class, "testAttribute", 100);
        index.insert(LocalDate.of(2023, 10, 10), 10);
        index.insert(LocalDate.of(2022, 1, 1), 30);
        index.insert(LocalDate.of(2023, 10, 10), 50);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", LocalDate.of(2023, 10, 10).toString());
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(10, 50), result, "The evaluate method should return rowIds [10, 50] for the given date.");
    }

    @Test
    public void testEvaluateForMaxRowIdBoundary() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 64);
        index.insert("value1", 0);
        index.insert("value1", 63);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "value1");
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(0, 63), result, "The evaluate method should correctly handle edge cases at the boundary of maxRowId.");
    }

    @Test
    public void testEvaluateForMaxRowIdBoundaryWithInteger() {
        BitmapIndex<Integer> index = new BitmapIndex<>(Integer.class, "testAttribute", 64);
        index.insert(42, 0);
        index.insert(42, 63);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "42");
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(0, 63), result, "The evaluate method should correctly handle edge cases at the boundary of maxRowId.");
    }

    @Test
    public void testEvaluateForMaxRowIdBoundaryWithLocalDate() {
        BitmapIndex<LocalDate> index = new BitmapIndex<>(LocalDate.class, "testAttribute", 64);
        index.insert(LocalDate.of(2023, 10, 10), 0);
        index.insert(LocalDate.of(2023, 10, 10), 63);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", LocalDate.of(2023, 10, 10).toString());
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(0, 63), result, "The evaluate method should correctly handle edge cases for LocalDate at the rowId boundaries.");
    }

    @Test
    public void testEvaluateForSingleEntry() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 64);
        index.insert("singleValue", 10);

        QueryNode queryNode = new QueryNode(Operator.EQUALS, "testAttribute", "singleValue");
        List<Integer> result = index.evaluate(queryNode);

        assertEquals(List.of(10), result, "The evaluate method should return the single rowId when the index contains only one entry.");
    }


    @Test
    public void testSearchSingleValueMatch() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 100);
        index.insert("value1", 10);

        List<Integer> result = index.search("value1");
        assertEquals(List.of(10), result, "The search method should return rowIds [10] for key 'value1'.");
    }

    @Test
    public void testSearchSingleValueMatchWithDouble() {
        BitmapIndex<Double> index = new BitmapIndex<>(Double.class, "testAttribute", 100);
        index.insert(42.5, 15);

        List<Integer> result = index.search(42.5);
        assertEquals(List.of(15), result, "The search method should return rowIds [15] for key 42.5.");
    }

    @Test
    public void testSearchSingleValueMatchWithLocalDate() {
        BitmapIndex<LocalDate> index = new BitmapIndex<>(LocalDate.class, "testAttribute", 100);
        index.insert(LocalDate.of(2023, 10, 10), 25);

        List<Integer> result = index.search(LocalDate.of(2023, 10, 10));
        assertEquals(List.of(25), result, "The search method should return rowIds [25] for the given LocalDate.");
    }

    @Test
    public void testSearchMultipleValuesForKey() {
        BitmapIndex<Integer> index = new BitmapIndex<>(Integer.class, "testAttribute", 100);
        index.insert(42, 5);
        index.insert(42, 20);

        List<Integer> result = index.search(42);
        assertEquals(List.of(5, 20), result, "The search method should return rowIds [5, 20] for key 42.");
    }

    @Test
    public void testSearchForNonExistentKey() {
        BitmapIndex<Double> index = new BitmapIndex<>(Double.class, "testAttribute", 100);
        index.insert(42.5, 5);
        index.insert(42.5, 20);

        List<Integer> result = index.search(99.9);
        assertTrue(result.isEmpty(), "The search method should return an empty list for a non-existent key.");
    }

    @Test
    public void testSearchForNonExistentKeyWithString() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 100);
        index.insert("value1", 5);
        index.insert("value1", 20);

        List<Integer> result = index.search("value2");
        assertTrue(result.isEmpty(), "The search method should return an empty list for a non-existent key.");
    }

    @Test
    public void testSearchForNonExistentKeyWithLocalDate() {
        BitmapIndex<LocalDate> index = new BitmapIndex<>(LocalDate.class, "testAttribute", 100);
        index.insert(LocalDate.of(2023, 10, 10), 5);
        index.insert(LocalDate.of(2023, 10, 10), 20);

        List<Integer> result = index.search(LocalDate.of(2022, 1, 1));
        assertTrue(result.isEmpty(), "The search method should return an empty list for a non-existent LocalDate key.");
    }

    @Test
    public void testSearchEmptyBitmap() {
        BitmapIndex<LocalDate> index = new BitmapIndex<>(LocalDate.class, "testAttribute", 100);

        List<Integer> result = index.search(LocalDate.of(2023, 10, 10));
        assertTrue(result.isEmpty(), "The search method should return an empty list for an empty index.");
    }

    @Test
    public void testSearchEmptyBitmapWithString() {
        BitmapIndex<String> index = new BitmapIndex<>(String.class, "testAttribute", 100);

        List<Integer> result = index.search("value1");
        assertTrue(result.isEmpty(), "The search method should return an empty list for an empty index.");
    }

    @Test
    public void testSearchEmptyBitmapWithDouble() {
        BitmapIndex<Double> index = new BitmapIndex<>(Double.class, "testAttribute", 100);

        List<Integer> result = index.search(42.5);
        assertTrue(result.isEmpty(), "The search method should return an empty list for an empty index.");
    }

}