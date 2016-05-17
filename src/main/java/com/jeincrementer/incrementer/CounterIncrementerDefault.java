package com.jeincrementer.incrementer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jeincrementer.dao.JEStatsDao;
import com.jeincrementer.model.JEStats;

@Service
public class CounterIncrementerDefault {
	
	@Autowired
	private JEStatsDao jEStatsDao;
	
	public static final long MAX_HEADER_COUNTER_VALUE = 1_500;

	public long getHeaderCounter() {
		
		JEStats jeStats = getJEStatsFirstObjectFromPersistence();
		long newHeaderCounter = getNewHeaderCounter(jeStats.getHeaderCounter());
		jeStats.setHeaderCounter(newHeaderCounter);
		jEStatsDao.update(jeStats);
		return newHeaderCounter;
	}

	private long getNewHeaderCounter(final long currentHeaderCounter) {
		long headerCounter = currentHeaderCounter;
		if(validateIfHeaderShouldBeReseted(headerCounter)){
			headerCounter = 1;
		}
		else{
			headerCounter +=1;
		}
		return headerCounter;
	}

	private boolean validateIfHeaderShouldBeReseted(long headerCounter) {
		return headerCounter == MAX_HEADER_COUNTER_VALUE || headerCounter == 0;
	}

	private JEStats getJEStatsFirstObjectFromPersistence() {
		Optional<JEStats> jeStatsOptional = getFirstCountersFromPreviousData();
		if(!jeStatsOptional.isPresent()){
			jeStatsOptional = Optional.of(createJEStatusInitialData());
		}
		return jeStatsOptional.get();
	}
	
	private Optional<JEStats> getFirstCountersFromPreviousData(){
		try{
			return Optional.ofNullable(jEStatsDao.getFirstElement());
		} catch (DataAccessException dae){
			return Optional.empty();
		}
	}

	private JEStats createJEStatusInitialData() {
		JEStats jeStats = new JEStats(0, 0);
		jeStats = jEStatsDao.create(jeStats);
		return jeStats;
	}

	public long getLineCounter() {

		JEStats jeStats= getJEStatsFirstObjectFromPersistence();
		long newLineCounterValue = jeStats.getLineCounter() + 1;
		jeStats.setLineCounter(newLineCounterValue);
		jEStatsDao.update(jeStats);
		return newLineCounterValue;
	}
	
}
