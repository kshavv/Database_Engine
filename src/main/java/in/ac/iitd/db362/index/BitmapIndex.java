package in.ac.iitd.db362.index;

import in.ac.iitd.db362.parser.QueryNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Starter code for a BitMap Index
 * Bitmap indexes are typically used for equality queries and rely on a BitSet.
 *
 * @param <T> The type of the key.
 */
public class BitmapIndex<T> implements Index<T> {

    protected static final Logger logger = LogManager.getLogger();

    private final Class<T> type;

    private String attribute;
    private int maxRowId;

    private Map<T, int[]> bitmaps;

    /**
     * Constructor
     *
     * @param type
     * @param attribute
     * @param maxRowId
     */
    public BitmapIndex(Class<T> type, String attribute, int maxRowId) {
        this.type = type;
        this.attribute = attribute;
        this.maxRowId = maxRowId;
        bitmaps = new HashMap<>();
    }

    /**
     * Create a empty bitmap for a given key
     * @param key
     */
    private void createBitmapForKey(T key) {
        int arraySize = (maxRowId + 31) / 32;
        bitmaps.putIfAbsent(key, new int[arraySize]);
    }


    /**
     * This has been done for you.
     * @param key The attribute value.
     * @param rowId The row ID associated with the key.
     */
    public void insert(T key, int rowId) {
        createBitmapForKey(key);
        int index = rowId / 32;
        int bitPosition = rowId % 32;
        bitmaps.get(key)[index] |= (1 << bitPosition);
    }


    @Override
    /**
     * This is only for completeness. Although one can delete a key, it will mess up rowIds
     * If a record is deleted, then an unset bit may lead to ambiguity (is false vs not exists)
     */
    public boolean delete(T key) {
        return false;
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
        logger.info("Evaluating predicate using Bitmap index on attribute " + attribute + " for operator " + node.operator);
        return search(castValueFromString(node.value, type));
    }

    @Override
    public List<Integer> search(T key) {
        if (!bitmaps.containsKey(key)) {
            return List.of(); // Return empty list if key does not exist
        }

        int[] bitmap = bitmaps.get(key);
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < bitmap.length; i++) {
            int bitset = bitmap[i];
            for (int j = 0; j < 32; j++) {
                if ((bitset & (1 << j)) != 0) {
                    result.add(i * 32 + j);
                }
            }
        }
        return result;
    }

    @Override
    public String prettyName() {
        return "BitMap Index";
    }
}