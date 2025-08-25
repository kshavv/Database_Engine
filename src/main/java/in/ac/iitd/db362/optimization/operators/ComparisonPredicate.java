package in.ac.iitd.db362.operators;

import in.ac.iitd.db362.storage.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Note: ONLY IMPLEMENT THE EVALUATE METHOD.
 * TODO: Implement the evaluate() method
 *
 * DO NOT CHANGE the constructor or existing member variables.
 *
 * A comparison predicate for simple atomic predicates.
 */
public class ComparisonPredicate implements Predicate {

    protected final static Logger logger = LogManager.getLogger();
    private final Object leftOperand;   // Either a constant or a column reference (String)
    private final String operator;        // One of: =, >, >=, <, <=, !=
    private final Object rightOperand;  // Either a constant or a column reference (String)

    public ComparisonPredicate(Object leftOperand, String operator, Object rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    /**
     * Evaluate a tuple
     * @param tuple the tuple to evaluate
     * @return return true if leftOperator operator righOperand holds in that tuple
     */
    @Override
    public boolean evaluate(Tuple tuple) {
        // DO NOT REMOVE LOGGING ---
        logger.trace("Evaluating tuple " + tuple.getValues() + " with schema " + tuple.getSchema());
        logger.trace("[Predicate] " + leftOperand + " " + operator + " " + rightOperand);
        // -------------------------

        // Resolve actual values based on operands
        Object leftValue = resolveOperand(leftOperand, tuple);
        Object rightValue = resolveOperand(rightOperand, tuple);

        // Handle null values - most SQL operations with nulls return false
        if (leftValue == null || rightValue == null) {
            return false;
        }

        // Ensure values are comparable
        if (!(leftValue instanceof Comparable) || !(rightValue instanceof Comparable)) {
            throw new IllegalArgumentException("Operands must be comparable");
        }

        // Need to ensure values are of the same type for proper comparison
        if (!leftValue.getClass().isAssignableFrom(rightValue.getClass()) &&
                !rightValue.getClass().isAssignableFrom(leftValue.getClass())) {
            throw new IllegalArgumentException("Cannot compare " + leftValue.getClass() + " with " + rightValue.getClass());
        }

        // Cast to Comparable for comparison operations
        @SuppressWarnings("unchecked")
        Comparable<Object> comparableLeft = (Comparable<Object>) leftValue;

        // Perform the comparison based on the operator
        int comparison = comparableLeft.compareTo(rightValue);

        switch (operator) {
            case "=":
                return comparison == 0;
            case ">":
                return comparison > 0;
            case ">=":
                return comparison >= 0;
            case "<":
                return comparison < 0;
            case "<=":
                return comparison <= 0;
            case "!=":
                return comparison != 0;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }

    }

    /**
     * Resolves an operand, which can be either a column name (String) or a constant value
     */
    private Object resolveOperand(Object operand, Tuple tuple) {
        if (operand instanceof String) {
            String columnName = (String) operand;
            // Check if the column name exists in the tuple's schema
            if (tuple.getSchema().contains(columnName)) {
                return tuple.get(columnName);
            }
            // If not in the schema, treat as a string constant
            return operand;
        }
        // If not a string, it's a constant value
        return operand;
    }


    // DO NOT REMOVE these functions! ---
    @Override
    public String toString() {
        return "ComparisonPredicate[" +
                "leftOperand=" + leftOperand +
                ", operator='" + operator + '\'' +
                ", rightOperand=" + rightOperand +
                ']';
    }
    public Object getLeftOperand() {
        return leftOperand;
    }

    public String getOperator() {
        return operator;
    }
    public Object getRightOperand() {
        return rightOperand;
    }

}
