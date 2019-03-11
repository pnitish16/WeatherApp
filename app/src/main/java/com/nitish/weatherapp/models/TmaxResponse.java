package com.nitish.weatherapp.models;


public class TmaxResponse{
	private int month;
	private int year;
	private double value;

	public void setMonth(int month){
		this.month = month;
	}

	public int getMonth(){
		return month;
	}

	public void setYear(int year){
		this.year = year;
	}

	public int getYear(){
		return year;
	}

	public void setValue(double value){
		this.value = value;
	}

	public double getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"TminResponse{" +
			"month = '" + month + '\'' + 
			",year = '" + year + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}
