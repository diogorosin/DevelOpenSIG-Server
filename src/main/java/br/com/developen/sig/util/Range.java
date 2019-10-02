package br.com.developen.sig.util;

public class Range {

	private int start;

	private int end;

	public Range(int start, int end) {

		this.setStart(start);

		this.setEnd(end);

	}

	public static int sublong(String value, int beginIndex, int endIndex) {

		String substring = value.substring(beginIndex, endIndex);

		return (substring.length() > 0) ? Integer.parseInt(substring) : -1;

	} 

	public int getStart() {

		return start;

	}

	public void setStart(int start) {

		this.start = start;

	}

	public int getEnd() {

		return end;

	}

	public void setEnd(int end) {

		this.end = end;

	}

	public String toString(){

		return this.getStart() + "-" + getEnd();

	}

}