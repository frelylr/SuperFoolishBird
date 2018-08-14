package org.frelylr.sfb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class BaseDao {

    @Autowired
    @Qualifier("coreMasterJdbcTemplate")
    protected NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * selectForObject
     */
    public <T>T selectForObject(StringBuilder sql, Class<T> t) {

        try {
            return jdbcTemplate.queryForObject(sql.toString(), new HashMap<>(), t);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * selectForObject
     */
    public <T>T selectForObject(StringBuilder sql, Map<String, ?> paramMap, Class<T> t) {

        try {
            return jdbcTemplate.queryForObject(sql.toString(), paramMap, t);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * selectForEntity
     */
    public <T>T selectForEntity(StringBuilder sql, Class<T> t) {

        try {
            return jdbcTemplate.queryForObject(sql.toString(), new HashMap<>(), BeanPropertyRowMapper.newInstance(t));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * selectForEntity
     */
    public <T>T selectForEntity(StringBuilder sql, Map<String, ?> paramMap, Class<T> t) {

        try {
            return jdbcTemplate.queryForObject(sql.toString(), paramMap, BeanPropertyRowMapper.newInstance(t));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * selectForObjectList
     */
    public <T>List<T> selectForObjectList(StringBuilder sql, Class<T> t) {

        return jdbcTemplate.query(sql.toString(), SingleColumnRowMapper.newInstance(t));
    }

    /**
     * selectForObjectList
     */
    public <T>List<T> selectForObjectList(StringBuilder sql, Map<String, ?> paramMap, Class<T> t) {

        return jdbcTemplate.query(sql.toString(), paramMap, SingleColumnRowMapper.newInstance(t));
    }

    /**
     * selectForEntityList
     */
    public <T>List<T> selectForEntityList(StringBuilder sql, Class<T> t) {

        return jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(t));
    }

    /**
     * selectForEntityList
     */
    public <T>List<T> selectForEntityList(StringBuilder sql, Map<String, ?> paramMap, Class<T> t) {

        return jdbcTemplate.query(sql.toString(), paramMap, BeanPropertyRowMapper.newInstance(t));
    }

}
