package com.jeincrementer.incrementer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import com.jeincrementer.dao.JEStatsDao;
import com.jeincrementer.model.JEStats;

public class CounterIncrementerDefaultTest {
	
	@InjectMocks
	private CounterIncrementerDefault toTest;
	
	@Mock(name="jEStatsDao")
	private JEStatsDao jEStatsDao;
	
	@Before
	public void setup(){
		
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getHeaderCounterOneWhenThereAreNotValues() {
		
		JEStats jeStatsMock = new JEStats(0, 0);
		when(jEStatsDao.getFirstElement()).thenThrow(mock(DataAccessException.class));
		when(jEStatsDao.create(any(JEStats.class))).thenReturn(jeStatsMock);
		when(jEStatsDao.update(jeStatsMock)).thenReturn(jeStatsMock);
		long newHeaderCounter = toTest.getHeaderCounter();
		assertThat(newHeaderCounter, equalTo(1L));
	}
	
	@Test
	public void getHeaderCounterIncrementedByOne(){
		
		JEStats jeStatsMock = new JEStats(1, 1);
		when(jEStatsDao.getFirstElement()).thenReturn(jeStatsMock);
		long  currentHeaderCounterValue = jeStatsMock.getHeaderCounter();
		long newHeaderCounter = toTest.getHeaderCounter();
		assertThat(newHeaderCounter, equalTo(currentHeaderCounterValue + 1));
	}
	
	@Test
	public void resetHeaderCounterWhenIsEqualToMaxValue(){
		
		JEStats jeStatsMock = new JEStats(CounterIncrementerDefault.MAX_HEADER_COUNTER_VALUE, 1);
		when(jEStatsDao.getFirstElement()).thenReturn(jeStatsMock);
		long newHeaderCounter = toTest.getHeaderCounter();
		assertThat(newHeaderCounter, equalTo(1L));
	}
	
	@Test
	public void getLineCounterOneWhenThereAreNotValues() {
		
		JEStats jeStatsMock = new JEStats(0, 0);
		when(jEStatsDao.getFirstElement()).thenThrow(mock(DataAccessException.class));
		when(jEStatsDao.create(any(JEStats.class))).thenReturn(jeStatsMock);
		when(jEStatsDao.update(jeStatsMock)).thenReturn(jeStatsMock);
		long newHeaderCounter = toTest.getLineCounter();
		assertThat(newHeaderCounter, equalTo(1L));
	}
	
	@Test
	public void getLineCounterIncrementedByOne(){
		
		JEStats jeStatsMock = new JEStats(1, 1);
		when(jEStatsDao.getFirstElement()).thenReturn(jeStatsMock);
		long currentLineCounter = jeStatsMock.getLineCounter();
		long newLineCounter = toTest.getLineCounter();
		assertThat(newLineCounter, equalTo(currentLineCounter + 1));
	}

	@Test
	public void resetLineCounterWhenHeaderCounterIsIncremented(){
		
		JEStats jeStatsMock = new JEStats(1, 4);
		when(jEStatsDao.getFirstElement()).thenReturn(jeStatsMock);
		long currentHeaderCounter = jeStatsMock.getHeaderCounter();
		
		long newHeaderCounter = toTest.getHeaderCounter();
		
		assertThat(jeStatsMock.getLineCounter(), equalTo(1L));
		assertThat(newHeaderCounter, equalTo(currentHeaderCounter + 1));
	}
}
