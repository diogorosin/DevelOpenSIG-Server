package br.com.developen.sig.util;

import java.util.ArrayList;
import java.util.List;

import br.com.developen.sig.exception.InvalidRangeException;

public class RangeUtil {

	/* REQUEST------------------- 
	GET http://localhost:8080/pre/rest/organization HTTP/1.1
	Accept-Encoding: gzip,deflate
	Range: rows=1-1
	Host: localhost:8080
	Connection: Keep-Alive
	User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
	 */
	/* RESPONSE------------------
	Date	Tue, 28 Nov 2017 21:02:31 GMT
	Content-Length	60
	#status#	HTTP/1.1 206
	Content-Type	application/json
	Accept-Ranges	rows
	Content-Range	rows=1-1/5/5
	 */
	public static List<Range> splitRanges(String rangeString, int length) throws InvalidRangeException {

		//Return list of ranges
		List<Range> ranges = new ArrayList<>();

		// Range header should match format "rows=n-n,n-n,n-n...". If not, then return 416.
		if (!rangeString.matches("^rows=\\d*-\\d*(,\\d*-\\d*)*$"))

			throw new InvalidRangeException();

		for (String part : rangeString.substring(5).split(",")) {

			// Assuming a file with length of 100, the following examples returns bytes at:
			// 50-80 (50 to 80), 40- (40 to length=100), -20 (length-20=80 to length=100).
			int start = Range.sublong(part, 0, part.indexOf("-"));

			int end = Range.sublong(part, part.indexOf("-") + 1, part.length());

			if (start == -1) {

				start = 1;

				end = length;

			} else if (end == -1 || end > length) {

				end = length;

			}

			// Check if Range is syntactically valid. If not, then return 416.
			if (start > end)

				throw new InvalidRangeException();

			// Add range.                    
			ranges.add(new Range(start, end));

		}

		return ranges;

	}

}