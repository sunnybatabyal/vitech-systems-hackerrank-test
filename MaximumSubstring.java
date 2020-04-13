public class MaximumSubstring {

	/*
	 * Complete the 'maxSubstring' function below.
	 *
	 * The function is expected to return a STRING. The function accepts STRING s as
	 * parameter.
	 */

	public static String maxSubstring(String s) {
		String max = "", sub = "";

		for (int i = 0; i < s.length(); i++) {
			sub = s.substring(i);
			if (max.compareTo(sub) <= 0) {
				max = sub;
			}
		}

		return max;
	}

}
