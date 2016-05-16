package com.jeincrementer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jeincrementer.model.JEStats;

@Repository
public class JEStatsDao implements GenericDao<JEStats> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String JE_STATS_INSERT_QUERY = "INSERT INTO JE_STATS (HEADER_CNT, LINE_CNT) VALUES (?,?)";
	private static final String JE_STATS_SELECT_FIRST_QUERY = "SELECT * FROM JE_STATS LIMIT 1";
	private static final String JE_STATS_UPDATE_COUNTERS_QUERY = "UPDATE JE_STATS SET HEADER_CNT = ?, LINE_CNT = ? LIMIT 1";

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
