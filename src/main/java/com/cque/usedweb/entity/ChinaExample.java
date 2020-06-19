package com.cque.usedweb.entity;

import java.util.ArrayList;
import java.util.List;

public class ChinaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChinaExample() {
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

        public Criteria andCnIdIsNull() {
            addCriterion("cn_id is null");
            return (Criteria) this;
        }

        public Criteria andCnIdIsNotNull() {
            addCriterion("cn_id is not null");
            return (Criteria) this;
        }

        public Criteria andCnIdEqualTo(Integer value) {
            addCriterion("cn_id =", value, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdNotEqualTo(Integer value) {
            addCriterion("cn_id <>", value, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdGreaterThan(Integer value) {
            addCriterion("cn_id >", value, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cn_id >=", value, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdLessThan(Integer value) {
            addCriterion("cn_id <", value, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdLessThanOrEqualTo(Integer value) {
            addCriterion("cn_id <=", value, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdIn(List<Integer> values) {
            addCriterion("cn_id in", values, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdNotIn(List<Integer> values) {
            addCriterion("cn_id not in", values, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdBetween(Integer value1, Integer value2) {
            addCriterion("cn_id between", value1, value2, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cn_id not between", value1, value2, "cnId");
            return (Criteria) this;
        }

        public Criteria andCnNameIsNull() {
            addCriterion("cn_name is null");
            return (Criteria) this;
        }

        public Criteria andCnNameIsNotNull() {
            addCriterion("cn_name is not null");
            return (Criteria) this;
        }

        public Criteria andCnNameEqualTo(String value) {
            addCriterion("cn_name =", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameNotEqualTo(String value) {
            addCriterion("cn_name <>", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameGreaterThan(String value) {
            addCriterion("cn_name >", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameGreaterThanOrEqualTo(String value) {
            addCriterion("cn_name >=", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameLessThan(String value) {
            addCriterion("cn_name <", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameLessThanOrEqualTo(String value) {
            addCriterion("cn_name <=", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameLike(String value) {
            addCriterion("cn_name like", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameNotLike(String value) {
            addCriterion("cn_name not like", value, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameIn(List<String> values) {
            addCriterion("cn_name in", values, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameNotIn(List<String> values) {
            addCriterion("cn_name not in", values, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameBetween(String value1, String value2) {
            addCriterion("cn_name between", value1, value2, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnNameNotBetween(String value1, String value2) {
            addCriterion("cn_name not between", value1, value2, "cnName");
            return (Criteria) this;
        }

        public Criteria andCnPidIsNull() {
            addCriterion("cn_pid is null");
            return (Criteria) this;
        }

        public Criteria andCnPidIsNotNull() {
            addCriterion("cn_pid is not null");
            return (Criteria) this;
        }

        public Criteria andCnPidEqualTo(Integer value) {
            addCriterion("cn_pid =", value, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidNotEqualTo(Integer value) {
            addCriterion("cn_pid <>", value, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidGreaterThan(Integer value) {
            addCriterion("cn_pid >", value, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("cn_pid >=", value, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidLessThan(Integer value) {
            addCriterion("cn_pid <", value, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidLessThanOrEqualTo(Integer value) {
            addCriterion("cn_pid <=", value, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidIn(List<Integer> values) {
            addCriterion("cn_pid in", values, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidNotIn(List<Integer> values) {
            addCriterion("cn_pid not in", values, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidBetween(Integer value1, Integer value2) {
            addCriterion("cn_pid between", value1, value2, "cnPid");
            return (Criteria) this;
        }

        public Criteria andCnPidNotBetween(Integer value1, Integer value2) {
            addCriterion("cn_pid not between", value1, value2, "cnPid");
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