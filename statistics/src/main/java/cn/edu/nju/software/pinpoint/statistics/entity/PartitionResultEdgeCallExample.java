package cn.edu.nju.software.pinpoint.statistics.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartitionResultEdgeCallExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PartitionResultEdgeCallExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEdgeidIsNull() {
            addCriterion("edgeId is null");
            return (Criteria) this;
        }

        public Criteria andEdgeidIsNotNull() {
            addCriterion("edgeId is not null");
            return (Criteria) this;
        }

        public Criteria andEdgeidEqualTo(String value) {
            addCriterion("edgeId =", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidNotEqualTo(String value) {
            addCriterion("edgeId <>", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidGreaterThan(String value) {
            addCriterion("edgeId >", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidGreaterThanOrEqualTo(String value) {
            addCriterion("edgeId >=", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidLessThan(String value) {
            addCriterion("edgeId <", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidLessThanOrEqualTo(String value) {
            addCriterion("edgeId <=", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidLike(String value) {
            addCriterion("edgeId like", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidNotLike(String value) {
            addCriterion("edgeId not like", value, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidIn(List<String> values) {
            addCriterion("edgeId in", values, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidNotIn(List<String> values) {
            addCriterion("edgeId not in", values, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidBetween(String value1, String value2) {
            addCriterion("edgeId between", value1, value2, "edgeid");
            return (Criteria) this;
        }

        public Criteria andEdgeidNotBetween(String value1, String value2) {
            addCriterion("edgeId not between", value1, value2, "edgeid");
            return (Criteria) this;
        }

        public Criteria andCallidIsNull() {
            addCriterion("callId is null");
            return (Criteria) this;
        }

        public Criteria andCallidIsNotNull() {
            addCriterion("callId is not null");
            return (Criteria) this;
        }

        public Criteria andCallidEqualTo(String value) {
            addCriterion("callId =", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidNotEqualTo(String value) {
            addCriterion("callId <>", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidGreaterThan(String value) {
            addCriterion("callId >", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidGreaterThanOrEqualTo(String value) {
            addCriterion("callId >=", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidLessThan(String value) {
            addCriterion("callId <", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidLessThanOrEqualTo(String value) {
            addCriterion("callId <=", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidLike(String value) {
            addCriterion("callId like", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidNotLike(String value) {
            addCriterion("callId not like", value, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidIn(List<String> values) {
            addCriterion("callId in", values, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidNotIn(List<String> values) {
            addCriterion("callId not in", values, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidBetween(String value1, String value2) {
            addCriterion("callId between", value1, value2, "callid");
            return (Criteria) this;
        }

        public Criteria andCallidNotBetween(String value1, String value2) {
            addCriterion("callId not between", value1, value2, "callid");
            return (Criteria) this;
        }

        public Criteria andCalltypeIsNull() {
            addCriterion("callType is null");
            return (Criteria) this;
        }

        public Criteria andCalltypeIsNotNull() {
            addCriterion("callType is not null");
            return (Criteria) this;
        }

        public Criteria andCalltypeEqualTo(Integer value) {
            addCriterion("callType =", value, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeNotEqualTo(Integer value) {
            addCriterion("callType <>", value, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeGreaterThan(Integer value) {
            addCriterion("callType >", value, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("callType >=", value, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeLessThan(Integer value) {
            addCriterion("callType <", value, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeLessThanOrEqualTo(Integer value) {
            addCriterion("callType <=", value, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeIn(List<Integer> values) {
            addCriterion("callType in", values, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeNotIn(List<Integer> values) {
            addCriterion("callType not in", values, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeBetween(Integer value1, Integer value2) {
            addCriterion("callType between", value1, value2, "calltype");
            return (Criteria) this;
        }

        public Criteria andCalltypeNotBetween(Integer value1, Integer value2) {
            addCriterion("callType not between", value1, value2, "calltype");
            return (Criteria) this;
        }

        public Criteria andCreatedatIsNull() {
            addCriterion("createdAt is null");
            return (Criteria) this;
        }

        public Criteria andCreatedatIsNotNull() {
            addCriterion("createdAt is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedatEqualTo(Date value) {
            addCriterion("createdAt =", value, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatNotEqualTo(Date value) {
            addCriterion("createdAt <>", value, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatGreaterThan(Date value) {
            addCriterion("createdAt >", value, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatGreaterThanOrEqualTo(Date value) {
            addCriterion("createdAt >=", value, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatLessThan(Date value) {
            addCriterion("createdAt <", value, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatLessThanOrEqualTo(Date value) {
            addCriterion("createdAt <=", value, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatIn(List<Date> values) {
            addCriterion("createdAt in", values, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatNotIn(List<Date> values) {
            addCriterion("createdAt not in", values, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatBetween(Date value1, Date value2) {
            addCriterion("createdAt between", value1, value2, "createdat");
            return (Criteria) this;
        }

        public Criteria andCreatedatNotBetween(Date value1, Date value2) {
            addCriterion("createdAt not between", value1, value2, "createdat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatIsNull() {
            addCriterion("updatedAt is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedatIsNotNull() {
            addCriterion("updatedAt is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedatEqualTo(Date value) {
            addCriterion("updatedAt =", value, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatNotEqualTo(Date value) {
            addCriterion("updatedAt <>", value, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatGreaterThan(Date value) {
            addCriterion("updatedAt >", value, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatGreaterThanOrEqualTo(Date value) {
            addCriterion("updatedAt >=", value, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatLessThan(Date value) {
            addCriterion("updatedAt <", value, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatLessThanOrEqualTo(Date value) {
            addCriterion("updatedAt <=", value, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatIn(List<Date> values) {
            addCriterion("updatedAt in", values, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatNotIn(List<Date> values) {
            addCriterion("updatedAt not in", values, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatBetween(Date value1, Date value2) {
            addCriterion("updatedAt between", value1, value2, "updatedat");
            return (Criteria) this;
        }

        public Criteria andUpdatedatNotBetween(Date value1, Date value2) {
            addCriterion("updatedAt not between", value1, value2, "updatedat");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}