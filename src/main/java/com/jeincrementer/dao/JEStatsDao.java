package com.jeincrementer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jeincrementer.model.JEStats;

@Repository
public class JEStatsDao implements GenericDao<JEStats> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${sql.insert_first}")
	private String JE_STATS_INSERT_QUERY;
	@Value("${sql.select_first}")
	private String JE_STATS_SELECT_FIRST_QUERY;
	@Value("${sql.update_first}")
	private String JE_STATS_UPDATE_COUNTERS_QUERY;

	public JEStats create(final JEStats jEStats) {
		jdbcTemplate.update(JE_STATS_INSERT_QUERY, 
				new Object[]{jEStats.getHeaderCounter(), jEStats.getLineCounter()});
		return getFirstElement();
	}

	public JEStats getFirstElement(){
		return jdbcTemplate.queryForObject(JE_STATS_SELECT_FIRST_QUERY, 
				(resultSet, number) -> new JEStats(resultSet.getLong(1), resultSet.getLong(2)));
	}

	@Override
	public JEStats update(JEStats jeStats) {
		jdbcTemplate.update(JE_STATS_UPDATE_COUNTERS_QUERY, 
				new Object []{jeStats.getHeaderCounter(), jeStats.getLineCounter()} );
		return getFirstElement();
	}
	

}
