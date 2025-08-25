package in.ac.iitd.db362.operators;

import in.ac.iitd.db362.storage.Tuple;

import java.util.*;

/**
 * The join operator performs a Hash Join.
 * TODO: Implement the open(), next(), and close() methods.
 *
 * Do not change the constructor and member variables or getters
 * Do not remove logging! otherwise your test cases will fail!
 */
public class JoinOperator extends OperatorBase implements Operator {
    private Operator leftChild;
    private Operator rightChild;
    private JoinPredicate predicate;

    // Hash join state variables
    private Map<Object, List<Tuple>> hashTable;
    private Tuple currentLeftTuple;
    private Iterator<Tuple> matchIterator;
    private boolean hashTableBuilt;


    public JoinOperator(Operator leftChild, Operator rightChild, JoinPredicate predicate) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.predicate = predicate;
    }

    @Override
    public void open() {
        // Do not remove logging--
        logger.trace("Open()");
        // ----------------------

        // Open child operators
        leftChild.open();
        rightChild.open();

        // Initialize the hash table for the right relation (build phase)
        hashTable = new HashMap<>();

        // Build the hash table using the right relation (typically smaller one)
        Tuple rightTuple;
        while ((rightTuple = rightChild.next()) != null) {
            // Get the join key from the right tuple
            String rightColumnName = ((EqualityJoinPredicate) predicate).getRightColumn();
            Object joinKey = rightTuple.get(rightColumnName);

            // Add the right tuple to the hash table under its join key
            if (!hashTable.containsKey(joinKey)) {
                hashTable.put(joinKey, new ArrayList<>());
            }
            hashTable.get(joinKey).add(rightTuple);
        }

        // Initialize state for the probe phase
        hashTableBuilt = true;
        currentLeftTuple = null;
        matchIterator = Collections.emptyIterator();


    }

    @Override
    public Tuple next() {
        // Do not remove logging--
        logger.trace("Next()");
        // ----------------------

        // Ensure the hash table has been built
        if (!hashTableBuilt) {
            return null;
        }

        // Probe phase - iterate through left relation and find matches
        while (true) {
            // If we have remaining matches for current left tuple, return the next join result
            if (matchIterator.hasNext()) {
                Tuple rightMatch = matchIterator.next();
                return joinTuples(currentLeftTuple, rightMatch);
            }

            // Get the next tuple from the left relation
            currentLeftTuple = leftChild.next();

            // If no more left tuples, we're done
            if (currentLeftTuple == null) {
                return null;
            }

            // Extract join key from the left tuple
            String leftColumnName = ((EqualityJoinPredicate) predicate).getLeftColumn();
            Object joinKey = currentLeftTuple.get(leftColumnName);

            // Look up matching tuples in the hash table
            List<Tuple> matches = hashTable.getOrDefault(joinKey, Collections.emptyList());
            matchIterator = matches.iterator();

            // If there are no matches for this left tuple, continue to the next left tuple
            // If there are matches, they'll be returned in the next iteration of this loop
        }



    }

    @Override
    public void close() {
        // Do not remove logging ---
        logger.trace("Close()");
        // ------------------------
        // Close child operators
        leftChild.close();
        rightChild.close();

        // Clean up hash join state
        hashTable = null;
        currentLeftTuple = null;
        matchIterator = null;
        hashTableBuilt = false;

    }

    /**
     * Join two tuples into a result tuple
     */
    private Tuple joinTuples(Tuple leftTuple, Tuple rightTuple) {
        // Create lists to hold the combined values and schema
        List<Object> joinedValues = new ArrayList<>(leftTuple.getValues());
        joinedValues.addAll(rightTuple.getValues());

        List<String> joinedSchema = new ArrayList<>(leftTuple.getSchema());
        joinedSchema.addAll(rightTuple.getSchema());

        // Create and return a new tuple with the combined values and schema
        return new Tuple(joinedValues, joinedSchema);
    }


    // Do not remove these methods!
    public Operator getLeftChild() {
        return leftChild;
    }

    public Operator getRightChild() {
        return rightChild;
    }

    public JoinPredicate getPredicate() {
        return predicate;
    }
}
