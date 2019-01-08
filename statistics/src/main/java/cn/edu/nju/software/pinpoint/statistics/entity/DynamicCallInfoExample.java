package cn.edu.nju.software.pinpoint.statistics.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicCallInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DynamicCallInfoExample() {
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

        public Criteria andCallerIsNull() {
            addCriterion("caller is null");
            return (Criteria) this;
        }

        public Criteria andCallerIsNotNull() {
            addCriterion("caller is not null");
            return (Criteria) this;
        }

        public Criteria andCallerEqualTo(String value) {
            addCriterion("caller =", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotEqualTo(String value) {
            addCriterion("caller <>", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerGreaterThan(String value) {
            addCriterion("caller >", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerGreaterThanOrEqualTo(String value) {
            addCriterion("caller >=", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerLessThan(String value) {
            addCriterion("caller <", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerLessThanOrEqualTo(String value) {
            addCriterion("caller <=", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerLike(String value) {
            addCriterion("caller like", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotLike(String value) {
            addCriterion("caller not like", value, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerIn(List<String> values) {
            addCriterion("caller in", values, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotIn(List<String> values) {
            addCriterion("caller not in", values, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerBetween(String value1, String value2) {
            addCriterion("caller between", value1, value2, "caller");
            return (Criteria) this;
        }

        public Criteria andCallerNotBetween(String value1, String value2) {
            addCriterion("caller not between", value1, value2, "caller");
            return (Criteria) this;
        }

        public Criteria andCalleeIsNull() {
            addCriterion("callee is null");
            return (Criteria) this;
        }

        public Criteria andCalleeIsNotNull() {
            addCriterion("callee is not null");
            return (Criteria) this;
        }

        public Criteria andCalleeEqualTo(String value) {
            addCriterion("callee =", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotEqualTo(String value) {
            addCriterion("callee <>", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeGreaterThan(String value) {
            addCriterion("callee >", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeGreaterThanOrEqualTo(String value) {
            addCriterion("callee >=", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeLessThan(String value) {
            addCriterion("callee <", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeLessThanOrEqualTo(String value) {
            addCriterion("callee <=", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeLike(String value) {
            addCriterion("callee like", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotLike(String value) {
            addCriterion("callee not like", value, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeIn(List<String> values) {
            addCriterion("callee in", values, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotIn(List<String> values) {
            addCriterion("callee not in", values, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeBetween(String value1, String value2) {
            addCriterion("callee between", value1, value2, "callee");
            return (Criteria) this;
        }

        public Criteria andCalleeNotBetween(String value1, String value2) {
            addCriterion("callee not between", value1, value2, "callee");
            return (Criteria) this;
        }

        public Criteria andCountIsNull() {
            addCriterion("`count` is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("`count` is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Integer value) {
            addCriterion("`count` =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Integer value) {
            addCriterion("`count` <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Integer value) {
            addCriterion("`count` >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("`count` >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Integer value) {
            addCriterion("`count` <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Integer value) {
            addCriterion("`count` <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Integer> values) {
            addCriterion("`count` in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Integer> values) {
            addCriterion("`count` not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Integer value1, Integer value2) {
            addCriterion("`count` between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Integer value1, Integer value2) {
            addCriterion("`count` not between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidIsNull() {
            addCriterion("dynamicAnalysisInfoId is null");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidIsNotNull() {
            addCriterion("dynamicAnalysisInfoId is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidEqualTo(String value) {
            addCriterion("dynamicAnalysisInfoId =", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidNotEqualTo(String value) {
            addCriterion("dynamicAnalysisInfoId <>", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidGreaterThan(String value) {
            addCriterion("dynamicAnalysisInfoId >", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidGreaterThanOrEqualTo(String value) {
            addCriterion("dynamicAnalysisInfoId >=", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidLessThan(String value) {
            addCriterion("dynamicAnalysisInfoId <", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidLessThanOrEqualTo(String value) {
            addCriterion("dynamicAnalysisInfoId <=", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidLike(String value) {
            addCriterion("dynamicAnalysisInfoId like", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidNotLike(String value) {
            addCriterion("dynamicAnalysisInfoId not like", value, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidIn(List<String> values) {
            addCriterion("dynamicAnalysisInfoId in", values, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidNotIn(List<String> values) {
            addCriterion("dynamicAnalysisInfoId not in", values, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidBetween(String value1, String value2) {
            addCriterion("dynamicAnalysisInfoId between", value1, value2, "dynamicanalysisinfoid");
            return (Criteria) this;
        }

        public Criteria andDynamicanalysisinfoidNotBetween(String value1, String value2) {
            addCriterion("dynamicAnalysisInfoId not between", value1, value2, "dynamicanalysisinfoid");
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

        public Criteria andIsincludeIsNull() {
            addCriterion("isInclude is null");
            return (Criteria) this;
        }

        public Criteria andIsincludeIsNotNull() {
            addCriterion("isInclude is not null");
            return (Criteria) this;
        }

        public Criteria andIsincludeEqualTo(Integer value) {
            addCriterion("isInclude =", value, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeNotEqualTo(Integer value) {
            addCriterion("isInclude <>", value, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeGreaterThan(Integer value) {
            addCriterion("isInclude >", value, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeGreaterThanOrEqualTo(Integer value) {
            addCriterion("isInclude >=", value, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeLessThan(Integer value) {
            addCriterion("isInclude <", value, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeLessThanOrEqualTo(Integer value) {
            addCriterion("isInclude <=", value, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeIn(List<Integer> values) {
            addCriterion("isInclude in", values, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeNotIn(List<Integer> values) {
            addCriterion("isInclude not in", values, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeBetween(Integer value1, Integer value2) {
            addCriterion("isInclude between", value1, value2, "isinclude");
            return (Criteria) this;
        }

        public Criteria andIsincludeNotBetween(Integer value1, Integer value2) {
            addCriterion("isInclude not between", value1, value2, "isinclude");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(Integer value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Integer value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Integer value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Integer value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Integer value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Integer> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Integer> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Integer value1, Integer value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
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