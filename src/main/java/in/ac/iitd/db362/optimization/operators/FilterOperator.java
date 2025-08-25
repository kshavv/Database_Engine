package in.ac.iitd.db362.operators;

import in.ac.iitd.db362.storage.Tuple;

/**
 * The filter operator produces tuples that satisfy the predicate
 * TODO: Implement the open(), next(), and close() methods.
 *
 * Do not change the constructor and member variables or getters
 * Do not remove logging! otherwise your test cases will fail!
 */
public class FilterOperator extends OperatorBase implements Operator {
    private Operator child;
    private Predicate predicate;

    public FilterOperator(Operator child, Predicate predicate) {
        this.child = child;
        this.predicate = predicate;
    }

    @Override
    public void open() {
        // DO NOT REMOVE LOGGING ---
        logger.trace("Open()");
        // ------------------------
        // Initialize the child operator
        child.open();

    }

    @Override
    public Tuple next() {
        // DO NOT REMOVE LOGGING ---
        logger.trace("Next()");
        // -------------------------

        // Keep fetching tuples from the child until we find one that satisfies the predicate
        // or until there are no more tuples
        Tuple tuple;
        while ((tuple = child.next()) != null) {
            // Check if the tuple satisfies the predicate
            if (predicate.evaluate(tuple)) {
                // Return the tuple if it passes the filter
                return tuple;
            }
            // If not, continue to the next tuple
        }

        // Return null if no more tuples satisfy the predicate
        return null;

    }

    @Override
    public void close() {
        // DO NOT REMOVE LOGGING ---
        logger.trace("Close()");
        // -------------------------
        // Close the child operator
        if (child != null) {
            child.close();
        }

    }


    // Do not remove these methods!
    public Operator getChild() {
        return child;
    }

    public Predicate getPredicate() {
        return predicate;
    }
}
