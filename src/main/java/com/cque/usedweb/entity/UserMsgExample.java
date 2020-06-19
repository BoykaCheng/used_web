package com.cque.usedweb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserMsgExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFromUserIsNull() {
            addCriterion("from_user is null");
            return (Criteria) this;
        }

        public Criteria andFromUserIsNotNull() {
            addCriterion("from_user is not null");
            return (Criteria) this;
        }

        public Criteria andFromUserEqualTo(Integer value) {
            addCriterion("from_user =", value, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserNotEqualTo(Integer value) {
            addCriterion("from_user <>", value, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserGreaterThan(Integer value) {
            addCriterion("from_user >", value, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("from_user >=", value, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserLessThan(Integer value) {
            addCriterion("from_user <", value, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserLessThanOrEqualTo(Integer value) {
            addCriterion("from_user <=", value, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserIn(List<Integer> values) {
            addCriterion("from_user in", values, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserNotIn(List<Integer> values) {
            addCriterion("from_user not in", values, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserBetween(Integer value1, Integer value2) {
            addCriterion("from_user between", value1, value2, "fromUser");
            return (Criteria) this;
        }

        public Criteria andFromUserNotBetween(Integer value1, Integer value2) {
            addCriterion("from_user not between", value1, value2, "fromUser");
            return (Criteria) this;
        }

        public Criteria andToUserIsNull() {
            addCriterion("to_user is null");
            return (Criteria) this;
        }

        public Criteria andToUserIsNotNull() {
            addCriterion("to_user is not null");
            return (Criteria) this;
        }

        public Criteria andToUserEqualTo(Integer value) {
            addCriterion("to_user =", value, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserNotEqualTo(Integer value) {
            addCriterion("to_user <>", value, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserGreaterThan(Integer value) {
            addCriterion("to_user >", value, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("to_user >=", value, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserLessThan(Integer value) {
            addCriterion("to_user <", value, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserLessThanOrEqualTo(Integer value) {
            addCriterion("to_user <=", value, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserIn(List<Integer> values) {
            addCriterion("to_user in", values, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserNotIn(List<Integer> values) {
            addCriterion("to_user not in", values, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserBetween(Integer value1, Integer value2) {
            addCriterion("to_user between", value1, value2, "toUser");
            return (Criteria) this;
        }

        public Criteria andToUserNotBetween(Integer value1, Integer value2) {
            addCriterion("to_user not between", value1, value2, "toUser");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNull() {
            addCriterion("display is null");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNotNull() {
            addCriterion("display is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayEqualTo(Integer value) {
            addCriterion("display =", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotEqualTo(Integer value) {
            addCriterion("display <>", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThan(Integer value) {
            addCriterion("display >", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThanOrEqualTo(Integer value) {
            addCriterion("display >=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThan(Integer value) {
            addCriterion("display <", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThanOrEqualTo(Integer value) {
            addCriterion("display <=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayIn(List<Integer> values) {
            addCriterion("display in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotIn(List<Integer> values) {
            addCriterion("display not in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayBetween(Integer value1, Integer value2) {
            addCriterion("display between", value1, value2, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotBetween(Integer value1, Integer value2) {
            addCriterion("display not between", value1, value2, "display");
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