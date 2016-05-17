package com.jeincrementer.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jeincrementer.model.JEStats;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring_test.xml"})
public class JEStatsDaoTest {

	@Autowired
	private JEStatsDao toTest;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	private static final long HEADER_FIRST_ELEMENT = 2L;
	private static final long LINE_FIRST_ELEMENT = 3L;
	private static final String JE_INSERT_QUERY = "INSERT INTO JE_STATS (HEADER_CNT, LINE_CNT) VALUES (?,?)";
	private static final String JE_TRUNCATE_QUERY = "TRUNCATE TABLE JE_STATS";
	
	@Before
	public void setup(){
		insertFirstDataInTestTable(HEADER_FIRST_ELEMENT, LINE_FIRST_ELEMENT);
	}
	
	@After
	public void tearDown(){
		truncateTableJEStats();
	}

	private void insertFirstDataInTestTable(long headerCounter, long lineCounter){
		jdbcTemplate.update(JE_INSERT_QUERY, headerCounter, lineCounter);	
	}
	
	private void truncateTableJEStats() {
		jdbcTemplate.execute(JE_TRUNCATE_QUERY);
	}
	
	@Test(expected=DataAccessException.class)
	public void shouldThrowDataAccessExceptionBecauseDoesNotExistAnyData(){
		truncateTableJEStats();
		toTest.getFirstElement();
	}
	
	@Test
	public void shouldInsertAJEStatsDataInTheTable(){
		truncateTableJEStats();
		JEStats jeStats = toTest.create(new JEStats(2L, 3L));
		assertThat(jeStats.getHeaderCounter(), equalTo(HEADER_FIRST_ELEMENT));
		assertThat(jeStats.getLineCounter(), equalTo(LINE_FIRST_ELEMENT));
	}
	@Test
	public void shouldReturnFirstElementInTheTableJEStats(){
		JEStats jeStatsRetrieved = toTest.getFirstElement();
		assertThat(jeStatsRetrieved.getHeaderCounter(), equalTo(HEADER_FIRST_ELEMENT));
		assertThat(jeStatsRetrieved.getLineCounter(), equalTo(LINE_FIRST_ELEMENT));
	}
	
	@Test
	public void shouldUpdateFirstJEStatsDataInDatabase(){
		JEStats jeStatsUpdated =  new JEStats(HEADER_FIRST_ELEMENT + 1, LINE_FIRST_ELEMENT +1);
		JEStats jeStatsRetrieved = toTest.update(jeStatsUpdated);
		JEStats first = toTest.getFirstElement();
		assertThat(jeStatsRetrieved.getHeaderCounter(), equalTo(HEADER_FIRST_ELEMENT+1));
		assertThat(jeStatsRetrieved.getLineCounter(), equalTo(LINE_FIRST_ELEMENT+1));
	}
}
