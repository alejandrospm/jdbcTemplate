package com.jeincrementer.model;

import java.io.Serializable;

public class JEStats implements Serializable{

	private long headerCounter;
	
	private long lineCounter;

	
	public JEStats(long headerCounter, long lineCounter) {
		this.headerCounter = headerCounter;
		this.lineCounter = lineCounter;
	}

	/**
	 * @return the headerCounter
	 */
	public long getHeaderCounter() {
		return headerCounter;
	}

	/**
	 * @param headerCounter the headerCounter to set
	 */
	public void setHeaderCounter(long headerCounter) {
		this.headerCounter = headerCounter;
	}

	/**
	 * @return the lineCounter
	 */
	public long getLineCounter() {
		return lineCounter;
	}

	/**
	 * @param lineCounter the lineCounter to set
	 */
	public void setLineCounter(long lineCounter) {
		this.lineCounter = lineCounter;
	}
	
	
}
