package com.cque.usedweb.entity;

import java.util.ArrayList;
import java.util.List;

public class BoardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BoardExample() {
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

        public Criteria andBoardNameIsNull() {
            addCriterion("board_name is null");
            return (Criteria) this;
        }

        public Criteria andBoardNameIsNotNull() {
            addCriterion("board_name is not null");
            return (Criteria) this;
        }

        public Criteria andBoardNameEqualTo(String value) {
            addCriterion("board_name =", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotEqualTo(String value) {
            addCriterion("board_name <>", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameGreaterThan(String value) {
            addCriterion("board_name >", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameGreaterThanOrEqualTo(String value) {
            addCriterion("board_name >=", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameLessThan(String value) {
            addCriterion("board_name <", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameLessThanOrEqualTo(String value) {
            addCriterion("board_name <=", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameLike(String value) {
            addCriterion("board_name like", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotLike(String value) {
            addCriterion("board_name not like", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameIn(List<String> values) {
            addCriterion("board_name in", values, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotIn(List<String> values) {
            addCriterion("board_name not in", values, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameBetween(String value1, String value2) {
            addCriterion("board_name between", value1, value2, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotBetween(String value1, String value2) {
            addCriterion("board_name not between", value1, value2, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardDescIsNull() {
            addCriterion("board_desc is null");
            return (Criteria) this;
        }

        public Criteria andBoardDescIsNotNull() {
            addCriterion("board_desc is not null");
            return (Criteria) this;
        }

        public Criteria andBoardDescEqualTo(String value) {
            addCriterion("board_desc =", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescNotEqualTo(String value) {
            addCriterion("board_desc <>", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescGreaterThan(String value) {
            addCriterion("board_desc >", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescGreaterThanOrEqualTo(String value) {
            addCriterion("board_desc >=", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescLessThan(String value) {
            addCriterion("board_desc <", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescLessThanOrEqualTo(String value) {
            addCriterion("board_desc <=", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescLike(String value) {
            addCriterion("board_desc like", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescNotLike(String value) {
            addCriterion("board_desc not like", value, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescIn(List<String> values) {
            addCriterion("board_desc in", values, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescNotIn(List<String> values) {
            addCriterion("board_desc not in", values, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescBetween(String value1, String value2) {
            addCriterion("board_desc between", value1, value2, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andBoardDescNotBetween(String value1, String value2) {
            addCriterion("board_desc not between", value1, value2, "boardDesc");
            return (Criteria) this;
        }

        public Criteria andTopicNumIsNull() {
            addCriterion("topic_num is null");
            return (Criteria) this;
        }

        public Criteria andTopicNumIsNotNull() {
            addCriterion("topic_num is not null");
            return (Criteria) this;
        }

        public Criteria andTopicNumEqualTo(Integer value) {
            addCriterion("topic_num =", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumNotEqualTo(Integer value) {
            addCriterion("topic_num <>", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumGreaterThan(Integer value) {
            addCriterion("topic_num >", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("topic_num >=", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumLessThan(Integer value) {
            addCriterion("topic_num <", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumLessThanOrEqualTo(Integer value) {
            addCriterion("topic_num <=", value, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumIn(List<Integer> values) {
            addCriterion("topic_num in", values, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumNotIn(List<Integer> values) {
            addCriterion("topic_num not in", values, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumBetween(Integer value1, Integer value2) {
            addCriterion("topic_num between", value1, value2, "topicNum");
            return (Criteria) this;
        }

        public Criteria andTopicNumNotBetween(Integer value1, Integer value2) {
            addCriterion("topic_num not between", value1, value2, "topicNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumIsNull() {
            addCriterion("article_num is null");
            return (Criteria) this;
        }

        public Criteria andArticleNumIsNotNull() {
            addCriterion("article_num is not null");
            return (Criteria) this;
        }

        public Criteria andArticleNumEqualTo(Integer value) {
            addCriterion("article_num =", value, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumNotEqualTo(Integer value) {
            addCriterion("article_num <>", value, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumGreaterThan(Integer value) {
            addCriterion("article_num >", value, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("article_num >=", value, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumLessThan(Integer value) {
            addCriterion("article_num <", value, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumLessThanOrEqualTo(Integer value) {
            addCriterion("article_num <=", value, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumIn(List<Integer> values) {
            addCriterion("article_num in", values, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumNotIn(List<Integer> values) {
            addCriterion("article_num not in", values, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumBetween(Integer value1, Integer value2) {
            addCriterion("article_num between", value1, value2, "articleNum");
            return (Criteria) this;
        }

        public Criteria andArticleNumNotBetween(Integer value1, Integer value2) {
            addCriterion("article_num not between", value1, value2, "articleNum");
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