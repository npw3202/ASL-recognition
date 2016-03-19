package mainPackage;

public class Stats {
	/**
	 * Gets the mean of the data
	 * @param data an array of the data
	 * @return the mean of the data
	 */
	public static float getMean(float[] data){
		float sum = 0;
		for(int i = 0; i < data.length; i++){
			sum += data[i];
		}
		return sum/((float) data.length);
	}
	/**
	 * Gets the standard deviation from the data
	 * @param data an array of the data
	 * @param mean the mean of the data
	 * @return the standard deviation of the data
	 */
	public static float getStdDev(float[] data, float mean){
		float sumOfSquares = 0;
		for(int i = 0; i < data.length; i++){
			sumOfSquares += Math.pow(data[i] - mean,2);
		}
		return (float) Math.sqrt(sumOfSquares/((float)data.length-1));
	}
}
