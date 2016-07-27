package core.framework.database;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class QueryBuilderL {
    private static final String OPERATOR = "AND";
    private final String typeQuery;
    private final List<FieldQuery> fieldQueries = Lists.newArrayList();
    private final List<String> orderByFields = Lists.newArrayList();
    private final List<Boolean> isDescs = Lists.newArrayList();
    private final List<String> groupByFields = Lists.newArrayList();
    private boolean desc = false;
    private boolean skipNullFields = false;
    private boolean skipEmptyFields = false;
    private boolean skipWhere = false;
    private Integer from;
    private Integer size;

    private QueryBuilderL(String typeQuery) {
        this.typeQuery = typeQuery;
    }

    public static QueryBuilderL query(String query) {
        //        Preconditions.checkState(!query.toLowerCase().contains(" where "), "where doesn't allow to be here");
        return new QueryBuilderL(query);
    }

    public QueryBuilderL skipWhere() {
        skipWhere = true;
        return this;
    }

    public QueryBuilderL skipNullFields() {
        skipNullFields = true;
        return this;
    }

    public QueryBuilderL skipEmptyFields() {
        skipNullFields = true;
        skipEmptyFields = true;
        return this;
    }

    public QueryBuilderL orderBy(String... fields) {
        orderByFields.addAll(Arrays.asList(fields));
        return this;
    }

    public QueryBuilderL groupBy(String... fields) {
        groupByFields.addAll(Arrays.asList(fields));
        return this;
    }


    public QueryBuilderL orderBy(String field, boolean isDesc) {
        orderByFields.add(field);
        isDescs.add(isDesc);
        return this;
    }

    public QueryBuilderL desc() {
        desc = true;
        return this;
    }

    public QueryBuilderL append(String field, Object value, String operator) {
        return append(field, value, field, operator);
    }

    public QueryBuilderL append(String field, Object value, String variableName, String operator) {
        fieldQueries.add(new FieldQuery(field, value, variableName, operator, "and"));
        return this;
    }

    public QueryBuilderL append(String field, Object value, String variableName, String operator, String andOr) {
        fieldQueries.add(new FieldQuery(field, value, variableName, operator, andOr));
        return this;
    }

    public QueryBuilderL append(String field, Object value) {
        return append(field, value, "=");
    }

    public QueryBuilderL from(int from) {
        this.from = from;
        return this;
    }

    public QueryBuilderL fetch(int size) {
        this.size = size;
        return this;
    }

    public Query build() {
        StringBuilder b = new StringBuilder(typeQuery).append(' ');
        boolean firstFieldQuery = true;
        Map<String, Object> params = Maps.newHashMap();
        for (FieldQuery fieldQuery : fieldQueries) {
            if (!isFieldSkipped(fieldQuery)) {
                if (firstFieldQuery && !skipWhere) {
                    b.append("WHERE ");
                    firstFieldQuery = false;
                } else {
                    b.append(' ').append(OPERATOR).append(' ');
                }
                if (fieldQuery.getValue() instanceof List) {
                    List list = (List) fieldQuery.getValue();
                    b.append(" (");
                    for (int i = 0; i < list.size(); i++) {
                        b.append(' ').append(fieldQuery.getField()).append(' ');
                        b.append(fieldQuery.getOperator()).append(" :").append(fieldQuery.getVariableName() + i);
                        b.append(' ').append(fieldQuery.getAndOr());
                        params.put(fieldQuery.getVariableName() + i, list.get(i));
                    }
                    b.delete(b.length() - fieldQuery.getAndOr().length(), b.length());
                    b.append(" )");
                } else {
                    b.append(fieldQuery.getField()).append(' ');
                    b.append(fieldQuery.getOperator()).append(" :").append(fieldQuery.getVariableName());
                    params.put(fieldQuery.getVariableName(), fieldQuery.getValue());
                }
            }
        }
        if (!groupByFields.isEmpty()) {
            b.append(" GROUP BY ");
            for (String groupByField : groupByFields) {
                b.append(groupByField);
                b.append(',');
            }
            b.deleteCharAt(b.length() - 1);
        }
        if (!orderByFields.isEmpty()) {
            b.append(" ORDER BY ");
            for (int i = 0; i < orderByFields.size(); i++) {
                b.append(orderByFields.get(i));
                if (!isDescs.isEmpty() && isDescs.size() >= orderByFields.size() && isDescs.get(i)) {
                    b.append(" DESC");
                }
                b.append(',');
            }
            b.deleteCharAt(b.length() - 1);
            if (desc) {
                b.append(" DESC");
            }
        }
        Query query = Query.create(b.toString());
        query.params.putAll(params);
        query.from = from;
        query.size = size;
        return query;
    }

    boolean isFieldSkipped(FieldQuery fieldQuery) {
        if (fieldQuery.getValue() instanceof List) {
            return ((List) fieldQuery.getValue()).isEmpty();
        } else {
            return skipNullFields && fieldQuery.getValue() == null || skipEmptyFields && fieldQuery.getValue() instanceof String && Strings.isNullOrEmpty((String) fieldQuery.getValue());
        }

    }

    private class FieldQuery {
        private final String field;
        private final Object value;
        private final String operator;
        private final String variableName;
        private final String andOr;

        public FieldQuery(String field, Object value, String variableName, String operator, String andOr) {
            this.field = field;
            this.value = value;
            this.operator = operator;
            this.variableName = variableName;
            this.andOr = andOr;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getOperator() {
            return operator;
        }

        public String getVariableName() {
            return variableName;
        }

        public String getAndOr() {
            return andOr;
        }
    }
}
