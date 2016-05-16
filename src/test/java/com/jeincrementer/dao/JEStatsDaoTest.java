package com.jeincrementer.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jeincrementer.model.JEStats;

public class JEStatsDaoTest {

	@InjectMocks
	private JEStatsDao toTest;
	
	@Mock(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	private static final String JE_STATS_INSERT_QUERY = "INSERT INTO JE_STATS (HEADER_CNT, LINE_CNT) VALUES (?,?)";
	private static final String JE_STATS_UPDATE_COUNTERS_QUERY = "UPDATE JE_STATS SET HEADER_CNT = ?, LINE_CNT = ? LIMIT 1";
	private static final String JE_STATS_SELECT_FIRST_QUERY = "SELECT * FROM JE_STATS LIMIT 1";
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldInsertARegistryOfJEStats(){
		JEStats jeStats = new JEStats(1,1);
		when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
		toTest.create(jeStats);
		verify(jdbcTemplate, times(1)).update(JE_STATS_INSERT_QUERY, new Object[]{1L, 1L});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldUpdateAnEntityJEStats() {

		JEStats jeStats = new JEStats(5,10);
		when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
		when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class))).thenReturn(jeStats);
		JEStats jeStatsUpdated = toTest.update(jeStats);
		verify(jdbcTemplate, times(1)).update(JE_STATS_UPDATE_COUNTERS_QUERY, new Object[]{5L, 10L});
		assertThat(jeStatsUpdated.getHeaderCounter(), equalTo(jeStats.getHeaderCounter()));
		assertThat(jeStatsUpdated.getLineCounter(), equalTo(jeStats.getLineCounter()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetThFirstRegistryOfJEStats() throws SQLException{
		
		JEStats jeStats = new JEStats(1,1);
		ResultSet resultSetMock = mock(ResultSet.class);
		when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class))).thenReturn(jeStats);
		when(resultSetMock.getLong(1)).thenReturn(1L);
		when(resultSetMock.getLong(2)).thenReturn(1L);
		toTest.getFirstElement();
	}
	
}
