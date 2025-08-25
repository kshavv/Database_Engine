package in.ac.iitd.db362.operators;

import in.ac.iitd.db362.storage.Tuple;

import java.util.*;

/**
 * Implementation of a simple project operator that implements the operator interface.
 *
 *
 * TODO: Implement the open(), next(), and close() methods!
 * Do not change the constructor or existing member variables.
 */
public class ProjectOperator extends OperatorBase implements Operator {
    private Operator child;
    private List<String> projectedColumns;
    private boolean distinct;

    // For distinct projection
    private Set<List<Object>> seenTuples;


    /**
     * Project operator. If distinct is set to true, it does duplicate elimination
     * @param child
     * @param projectedColumns
     * @param distinct
     */
    public ProjectOperator(Operator child, List<String> projectedColumns, boolean distinct) {
        this.child = child;
        this.projectedColumns = projectedColumns;
        this.distinct = distinct;
    }

    @Override
    public void open() {
        // DO NOT REMOVE LOGGING ---
        logger.trace("Open()");
        // -------------------------

        child.open();

        // Initialize set to track distinct tuples if needed
        if (distinct) {
            seenTuples = new HashSet<>();
        }

    }

    @Override
    public Tuple next() {
        // DO NOT REMOVE LOGGING ---
        logger.trace("Next()");
        // ------------------------

        Tuple childTuple;

        while ((childTuple = child.next()) != null) {
            // Project the tuple to keep only the requested columns
            Tuple projectedTuple = projectTuple(childTuple);

            if (distinct) {
                // For distinct projection, check if this tuple has been seen before
                List<Object> valueList = projectedTuple.getValues();

                if (seenTuples.contains(valueList)) {
                    // Skip this tuple if already seen
                    continue;
                }

                // Add to seen tuples if not encountered before
                seenTuples.add(valueList);
            }

            // Return the projected tuple
            return projectedTuple;
        }

        // No more tuples from child
        return null;

    }

    @Override
    public void close() {
        // DO NOT REMOVE LOGGING ---
        logger.trace("Close()");
        // ------------------------

        // Close the child operator
        if (child != null) {
            child.close();
        }

        // Clean up resources
        if (distinct) {
            seenTuples = null;
        }

    }

    /**
     * Creates a new tuple with only the projected columns
     */
    private Tuple projectTuple(Tuple input) {
        List<Object> projectedValues = new ArrayList<>();
        List<String> schema = input.getSchema();

        // For each projected column, find its value in the input tuple
        for (String columnName : projectedColumns) {
            int columnIndex = schema.indexOf(columnName);
            if (columnIndex >= 0) {
                projectedValues.add(input.get(columnIndex));
            } else {
                throw new IllegalArgumentException("Column " + columnName + " not found in input schema");
            }
        }

        // Create a new tuple with the projected values
        return new Tuple(projectedValues, projectedColumns);
    }


    // do not remvoe these methods!
    public Operator getChild() {
        return child;
    }

    public List<String> getProjectedColumns() {
        return projectedColumns;
    }

    public boolean isDistinct() {
        return distinct;
    }
}
