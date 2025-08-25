package in.ac.iitd.db362.optimizer;

import in.ac.iitd.db362.catalog.Catalog;
import in.ac.iitd.db362.api.PlanPrinter;
import in.ac.iitd.db362.operators.Operator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A basic optimizer implementation. Feel free and be creative in designing your optimizer.
 * Do not change the constructor. Use the catalog for various statistics that are available.
 * For everything in your optimization logic, you are free to do what ever you want.
 * Make sure to write efficient code!
 */
public class BasicOptimizer implements Optimizer {

    // Do not remove or rename logger
    protected final Logger logger = LogManager.getLogger(this.getClass());

    // Do not remove or rename catalog. You'll need it in your optimizer
    private final Catalog catalog;

    /**
     * DO NOT CHANGE THE CONSTRUCTOR!
     *
     * @param catalog
     */
    public BasicOptimizer(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Basic optimization that currently does not modify the plan. Your goal is to come up with
     * an optimization strategy that should find an optimal plan. Come up with your own ideas or adopt the ones
     * discussed in the lecture to efficiently enumerate plans, a search strategy along with a cost model.
     *
     * @param plan The original query plan.
     * @return The (possibly) optimized query plan.
     */
    @Override
    public Operator optimize(Operator plan) {
        logger.info("Optimizing Plan:\n{}", PlanPrinter.getPlanString(plan));

        // Apply optimization rules to transform the plan
        Operator optimizedPlan = plan;

        // Apply a series of optimization rules
        optimizedPlan = pushDownFilters(optimizedPlan);
        optimizedPlan = optimizeProjections(optimizedPlan);
        optimizedPlan = reorderJoins(optimizedPlan);

        logger.info("After optimization:\n{}", PlanPrinter.getPlanString(optimizedPlan));

        return optimizedPlan;

    }


    /**
     * Pushes filter predicates down in the query plan to reduce
     * intermediate result sizes as early as possible.
     */
    private Operator pushDownFilters(Operator op) {
        if (op == null) return null;

        // Recursively optimize child operators first
        if (op instanceof FilterOperator) {
            FilterOperator filter = (FilterOperator) op;
            Operator child = filter.getChild();
            Predicate predicate = filter.getPredicate();

            // Base case: if child is a ScanOperator, we can't push down further
            if (child instanceof ScanOperator) {
                return op;
            }

            // If child is a join, try to push filter to either side if possible
            if (child instanceof JoinOperator) {
                JoinOperator join = (JoinOperator) child;

                // Determine which input(s) the predicate applies to
                Set<String> predicateColumns = getPredicateColumns(predicate);
                Set<String> leftColumns = getOperatorOutputColumns(join.getLeftChild());
                Set<String> rightColumns = getOperatorOutputColumns(join.getRightChild());

                boolean appliesToLeft = leftColumns.containsAll(predicateColumns);
                boolean appliesToRight = rightColumns.containsAll(predicateColumns);

                if (appliesToLeft && !appliesToRight) {
                    // Push filter to left side only
                    Operator newLeft = new FilterOperator(join.getLeftChild(), predicate);
                    return new JoinOperator(newLeft, join.getRightChild());
                } else if (!appliesToLeft && appliesToRight) {
                    // Push filter to right side only
                    Operator newRight = new FilterOperator(join.getRightChild(), predicate);
                    return new JoinOperator(join.getLeftChild(), newRight);
                }
                // If predicate applies to both sides or neither, keep filter above join
            }

            // If we can't optimize further, just optimize the child
            filter.setChild(pushDownFilters(child));
            return filter;
        } else if (op instanceof ProjectOperator) {
            ProjectOperator project = (ProjectOperator) op;
            project.setChild(pushDownFilters(project.getChild()));
            return project;
        } else if (op instanceof JoinOperator) {
            JoinOperator join = (JoinOperator) op;
            join.setLeftChild(pushDownFilters(join.getLeftChild()));
            join.setRightChild(pushDownFilters(join.getRightChild()));
            return join;
        }

        // For any other operator type, return unmodified
        return op;
    }

    /**
     * Optimizes projections by eliminating unnecessary columns and
     * merging consecutive projections when possible.
     */
    private Operator optimizeProjections(Operator op) {
        if (op == null) return null;

        if (op instanceof ProjectOperator) {
            ProjectOperator project = (ProjectOperator) op;
            Operator child = project.getChild();

            // Optimize child first
            child = optimizeProjections(child);
            project.setChild(child);

            // If child is also a projection, we might be able to merge them
            if (child instanceof ProjectOperator) {
                ProjectOperator childProject = (ProjectOperator) child;

                // Only merge if both have the same distinct setting or the parent is distinct
                if (project.isDistinct() == childProject.isDistinct() || project.isDistinct()) {
                    // Map columns from parent projection to columns in child projection
                    List<String> newProjection = new ArrayList<>();
                    List<String> parentColumns = project.getProjectedColumns();
                    List<String> childColumns = childProject.getProjectedColumns();

                    for (String col : parentColumns) {
                        int index = childColumns.indexOf(col);
                        if (index >= 0) {
                            newProjection.add(childColumns.get(index));
                        }
                    }

                    // Create a new projection that goes directly to the child's child
                    return new ProjectOperator(childProject.getChild(), newProjection,
                            project.isDistinct() || childProject.isDistinct());
                }
            }

            return project;
        } else if (op instanceof FilterOperator) {
            FilterOperator filter = (FilterOperator) op;
            filter.setChild(optimizeProjections(filter.getChild()));
            return filter;
        } else if (op instanceof JoinOperator) {
            JoinOperator join = (JoinOperator) op;
            join.setLeftChild(optimizeProjections(join.getLeftChild()));
            join.setRightChild(optimizeProjections(join.getRightChild()));
            return join;
        }

        // For any other operator type, return unmodified
        return op;
    }

    /**
     * Reorders joins based on estimated costs to minimize intermediate result sizes.
     * Uses statistics from the catalog to estimate cardinalities.
     */
    private Operator reorderJoins(Operator op) {
        if (op == null) return null;

        // Process children first (bottom-up)
        if (op instanceof FilterOperator) {
            FilterOperator filter = (FilterOperator) op;
            filter.setChild(reorderJoins(filter.getChild()));
            return filter;
        } else if (op instanceof ProjectOperator) {
            ProjectOperator project = (ProjectOperator) op;
            project.setChild(reorderJoins(project.getChild()));
            return project;
        } else if (op instanceof JoinOperator) {
            JoinOperator join = (JoinOperator) op;

            // First optimize the children
            Operator leftChild = reorderJoins(join.getLeftChild());
            Operator rightChild = reorderJoins(join.getRightChild());

            // Calculate the cost of the current order
            double currentCost = estimateJoinCost(leftChild, rightChild);

            // Try the alternative order
            double alternativeCost = estimateJoinCost(rightChild, leftChild);

            // Choose the lower cost option
            if (alternativeCost < currentCost) {
                // The alternative join order is better
                return new JoinOperator(rightChild, leftChild);
            } else {
                // Keep the original join order
                join.setLeftChild(leftChild);
                join.setRightChild(rightChild);
                return join;
            }
        }

        return op;
    }


    /**
     * Estimates the cost of a join operation based on input cardinalities
     * and selectivity estimates.
     */
    private double estimateJoinCost(Operator left, Operator right) {
        double leftSize = estimateCardinality(left);
        double rightSize = estimateCardinality(right);

        // For a simple cost model, use the product of the input sizes
        // as an estimate of the join cost
        return leftSize * rightSize;
    }


    /**
     * Estimates the output cardinality of an operator using catalog statistics.
     */
    private double estimateCardinality(Operator op) {
        if (op instanceof ScanOperator) {
            ScanOperator scan = (ScanOperator) op;
            String tableName = scan.getTableName();

            // Use catalog to get table statistics
            TableStatistics stats = catalog.getTableStatistics(tableName);
            if (stats != null) {
                return stats.getNumRows();
            }
            // Default estimate if no statistics available
            return 1000;
        } else if (op instanceof FilterOperator) {
            FilterOperator filter = (FilterOperator) op;
            Predicate predicate = filter.getPredicate();
            Operator child = filter.getChild();

            // Estimate child cardinality
            double childCardinality = estimateCardinality(child);

            // Estimate predicate selectivity
            double selectivity = estimatePredicateSelectivity(predicate);

            // Apply selectivity to child cardinality
            return childCardinality * selectivity;
        } else if (op instanceof ProjectOperator) {
            ProjectOperator project = (ProjectOperator) op;
            double childCardinality = estimateCardinality(project.getChild());

            // If distinct projection, estimate reduction based on column cardinalities
            if (project.isDistinct()) {
                // Estimate number of distinct combinations of projected columns
                double distinctEstimate = estimateDistinctCombinations(
                        project.getProjectedColumns(), project.getChild());
                return Math.min(childCardinality, distinctEstimate);
            }

            // Non-distinct projection doesn't change cardinality
            return childCardinality;
        } else if (op instanceof JoinOperator) {
            JoinOperator join = (JoinOperator) op;
            double leftCardinality = estimateCardinality(join.getLeftChild());
            double rightCardinality = estimateCardinality(join.getRightChild());

            // For a cross join, the result is the product of input cardinalities
            // TODO: For joins with conditions, apply a join selectivity factor
            return leftCardinality * rightCardinality;
        }

        // Default estimate for unknown operators
        return 1000;
    }

    /**
     * Estimates the selectivity of a predicate based on column statistics.
     */
    private double estimatePredicateSelectivity(Predicate predicate) {
        if (predicate instanceof ComparisonPredicate) {
            ComparisonPredicate comp = (ComparisonPredicate) predicate;
            Object leftOperand = comp.getLeftOperand();
            Object rightOperand = comp.getRightOperand();
            String operator = comp.getOperator();

            // Check if one operand is a column reference
            if (leftOperand instanceof String && catalog.getTableForColumn((String)leftOperand) != null) {
                String columnName = (String) leftOperand;
                String tableName = catalog.getTableForColumn(columnName);
                TableStatistics tableStats = catalog.getTableStatistics(tableName);
                ColumnStatistics<?> colStats = tableStats.getColumnStatistics(columnName);

                // Use column statistics to estimate selectivity
                return estimateComparisonSelectivity(colStats, operator, rightOperand);
            } else if (rightOperand instanceof String && catalog.getTableForColumn((String)rightOperand) != null) {
                String columnName = (String) rightOperand;
                String tableName = catalog.getTableForColumn(columnName);
                TableStatistics tableStats = catalog.getTableStatistics(tableName);
                ColumnStatistics<?> colStats = tableStats.getColumnStatistics(columnName);

                // Invert the operator for right-side column reference
                String invertedOperator = invertOperator(operator);
                return estimateComparisonSelectivity(colStats, invertedOperator, leftOperand);
            }
        }

        // Default selectivity estimate when better information is not available
        return 0.5;
    }


    /**
     * Inverts a comparison operator (e.g., > becomes <)
     */
    private String invertOperator(String operator) {
        switch (operator) {
            case ">": return "<";
            case "<": return ">";
            case ">=": return "<=";
            case "<=": return ">=";
            default: return operator; // = and != are symmetric
        }
    }

    /**
     * Estimates the selectivity of a comparison predicate using column statistics.
     */
    private double estimateComparisonSelectivity(ColumnStatistics<?> colStats, String operator, Object value) {
        int cardinality = colStats.getCardinality();
        int numValues = colStats.getNumValues();

        // Special case for equality
        if (operator.equals("=")) {
            // If value exists, estimate as 1/cardinality (assuming uniform distribution)
            return 1.0 / cardinality;
        }

        // Special case for inequality
        if (operator.equals("!=")) {
            // Complement of equality
            return 1.0 - (1.0 / cardinality);
        }

        // For range comparisons, need to estimate based on column data type
        if (colStats instanceof DoubleColumnStatistics) {
            return estimateDoubleComparisonSelectivity((DoubleColumnStatistics) colStats, operator, value);
        } else if (colStats instanceof IntColumnStatistics) {
            return estimateIntComparisonSelectivity((IntColumnStatistics) colStats, operator, value);
        } else if (colStats instanceof StringColumnStatistics) {
            // String columns don't support range comparisons for selectivity estimation
            // We'll use a default estimate
            return 0.33;
        }

        // Default selectivity when better information is not available
        return 0.33;
    }

    /**
     * Estimates the selectivity of a comparison predicate for a double column.
     */
    private double estimateDoubleComparisonSelectivity(DoubleColumnStatistics stats, String operator, Object value) {
        double min = stats.getMin();
        double max = stats.getMax();
        double range = max - min;

        if (value instanceof Number) {
            double doubleValue = ((Number) value).doubleValue();

            // Handle out-of-range values
            if (doubleValue < min) {
                if (operator.equals(">") || operator.equals(">=")) return 1.0;
                if (operator.equals("<") || operator.equals("<=")) return 0.0;
            }
            if (doubleValue > max) {
                if (operator.equals(">") || operator.equals(">=")) return 0.0;
                if (operator.equals("<") || operator.equals("<=")) return 1.0;
            }

            // Estimate based on position in range (assuming uniform distribution)
            double position = (doubleValue - min) / range;

            if (operator.equals(">")) return 1.0 - position;
            if (operator.equals(">=")) return 1.0 - position;
            if (operator.equals("<")) return position;
            if (operator.equals("<=")) return position;
        }

        // Default estimate if value is not a number
        return 0.33;
    }

    /**
     * Estimates the selectivity of a comparison predicate for an int column.
     */
    private double estimateIntComparisonSelectivity(IntColumnStatistics stats, String operator, Object value) {
        int min = stats.getMin();
        int max = stats.getMax();
        double range = max - min;

        if (value instanceof Number) {
            int intValue = ((Number) value).intValue();

            // Handle out-of-range values
            if (intValue < min) {
                if (operator.equals(">") || operator.equals(">=")) return 1.0;
                if (operator.equals("<") || operator.equals("<=")) return 0.0;
            }
            if (intValue > max) {
                if (operator.equals(">") || operator.equals(">=")) return 0.0;
                if (operator.equals("<") || operator.equals("<=")) return 1.0;
            }

            // For integers, we can use the histogram if available
            int[] histogram = stats.getHistogram();
            if (histogram != null && histogram.length > 0) {
                // Calculate which bucket this value falls into
                int bucketSize = (int) Math.ceil(range / histogram.length);
                int bucket = Math.min((intValue - min) / bucketSize, histogram.length - 1);

                // Get the count in this bucket
                int bucketCount = histogram[bucket];
                int totalCount = stats.getNumValues();

                // Adjust the selectivity based on the operator and histogram data
                double bucketSelectivity = (double) bucketCount / totalCount;

                if (operator.equals(">") || operator.equals(">=")) {
                    // Sum selectivities of all buckets after this one, plus a portion of this bucket
                    double rightPortion = 0.0;
                    for (int i = bucket + 1; i < histogram.length; i++) {
                        rightPortion += (double) histogram[i] / totalCount;
                    }
                    // For '>' we add half of the current bucket, for '>=' we add the whole bucket
                    return rightPortion + (operator.equals(">=") ? bucketSelectivity : bucketSelectivity / 2);
                } else if (operator.equals("<") || operator.equals("<=")) {
                    // Sum selectivities of all buckets before this one, plus a portion of this bucket
                    double leftPortion = 0.0;
                    for (int i = 0; i < bucket; i++) {
                        leftPortion += (double) histogram[i] / totalCount;
                    }
                    // For '<' we add half of the current bucket, for '<=' we add the whole bucket
                    return leftPortion + (operator.equals("<=") ? bucketSelectivity : bucketSelectivity / 2);
                }
            } else {
                // Fall back to position-based estimation if histogram is not available
                double position = (double) (intValue - min) / range;

                if (operator.equals(">")) return 1.0 - position;
                if (operator.equals(">=")) return 1.0 - position;
                if (operator.equals("<")) return position;
                if (operator.equals("<=")) return position;
            }
        }

        // Default estimate if value is not a number or other issues
        return 0.33;
    }

    /**
     * Estimates the number of distinct combinations for a set of columns.
     */
    private double estimateDistinctCombinations(List<String> columns, Operator source) {
        // Start with a high estimate
        double estimate = Double.MAX_VALUE;

        // Look for the column with the smallest cardinality
        for (String col : columns) {
            String tableName = catalog.getTableForColumn(col);
            if (tableName != null) {
                TableStatistics tableStats = catalog.getTableStatistics(tableName);
                ColumnStatistics<?> colStats = tableStats.getColumnStatistics(col);

                if (colStats != null) {
                    int cardinality = colStats.getCardinality();
                    estimate = Math.min(estimate, cardinality);
                }
            }
        }

        if (estimate == Double.MAX_VALUE) {
            // No statistics available, use a reasonable default
            estimate = 100;
        }

        return estimate;
    }

    /**
     * Extracts the set of column names referenced in a predicate.
     */
    private Set<String> getPredicateColumns(Predicate predicate) {
        Set<String> columns = new HashSet<>();

        if (predicate instanceof ComparisonPredicate) {
            ComparisonPredicate comp = (ComparisonPredicate) predicate;

            // Check left operand
            if (comp.getLeftOperand() instanceof String) {
                String leftOp = (String) comp.getLeftOperand();
                if (catalog.getTableForColumn(leftOp) != null) {
                    columns.add(leftOp);
                }
            }

            // Check right operand
            if (comp.getRightOperand() instanceof String) {
                String rightOp = (String) comp.getRightOperand();
                if (catalog.getTableForColumn(rightOp) != null) {
                    columns.add(rightOp);
                }
            }
        }

        return columns;
    }


    /**
     * Determines the set of columns output by an operator.
     */
    private Set<String> getOperatorOutputColumns(Operator op) {
        Set<String> columns = new HashSet<>();

        if (op instanceof ScanOperator) {
            ScanOperator scan = (ScanOperator) op;
            // Add all columns from this table
            String tableName = scan.getTableName();
            // This would require knowing all columns in a table
            // For now, assume we have some way to get this information
        } else if (op instanceof ProjectOperator) {
            ProjectOperator project = (ProjectOperator) op;
            // Output columns are exactly the projected columns
            columns.addAll(project.getProjectedColumns());
        } else if (op instanceof FilterOperator) {
            FilterOperator filter = (FilterOperator) op;
            // Filter doesn't change output columns
            columns.addAll(getOperatorOutputColumns(filter.getChild()));
        } else if (op instanceof JoinOperator) {
            JoinOperator join = (JoinOperator) op;
            // Join outputs columns from both inputs
            columns.addAll(getOperatorOutputColumns(join.getLeftChild()));
            columns.addAll(getOperatorOutputColumns(join.getRightChild()));
        }

        return columns;
    }





}
