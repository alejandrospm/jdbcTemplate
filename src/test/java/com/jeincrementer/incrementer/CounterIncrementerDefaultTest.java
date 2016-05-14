package com.jeincrementer.incrementer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jeincrementer.dao.JEStatsDao;
import com.jeincrementer.model.JEStats;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
public class CounterIncrementerDefaultTest {
	
	@Autowired
	@Qualifier("counterIncrementerDefault")
	private CounterIncrementerDefault toTest;
	
	@Autowired
	private JEStatsDao jEStatsDao;

	@Test
	public void getHeaderCounterOneWhenThereAreNotValues() {
		
		long newHeaderCounter = toTest.getHeaderCounter();
		assertThat(newHeaderCounter, equalTo(1L));
	}
	
	@Test
	public void getHeaderCounterIncrementedByOne(){
		
		JEStats currentJEStats = jEStatsDao.getFirstElement();
		long  currentHeaderCounterValue = currentJEStats.getHeaderCounter();
		long newHeaderCounter = toTest.getHeaderCounter();
		assertThat(newHeaderCounter, equalTo(currentHeaderCounterValue + 1));
	}
	
	@Test
	public void resetHeaderCounterWhenIsEqualToMaxValue(){
		
		long newHeaderCounter = toTest.getHeaderCounter();
		assertThat(newHeaderCounter, equalTo(1L));
	}
	
	@Test
	public void getLineCounterOneWhenThereAreNotValues() {
		
		long newHeaderCounter = toTest.getLineCounter();
		assertThat(newHeaderCounter, equalTo(1L));
	}
	
	@Test
	public void getLineCounterIncrementedByOne(){
		
		JEStats jeStats = jEStatsDao.getFirstElement();
		long currentLineCounter = jeStats.getLineCounter();
		long newLineCounter = toTest.getLineCounter();
		assertThat(newLineCounter, equalTo(currentLineCounter + 1));
	}

	@Test
	public void resetLineCounterWhenHeaderCounterIsIncremented(){
		
		JEStats jeStats = jEStatsDao.getFirstElement();
		long currentHeaderCounter = jeStats.getHeaderCounter();
		
		toTest.getHeaderCounter();
		
		jeStats = jEStatsDao.getFirstElement();
		long newHeaderCounter = jeStats.getHeaderCounter();
		
		assertThat(jeStats.getLineCounter(), equalTo(1L));
		assertThat(newHeaderCounter, equalTo(currentHeaderCounter + 1));
	}
}
