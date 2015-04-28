package simulation;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

public class NoiseChannel {
	
	AbstractRealDistribution noise;
	RandomGenerator rng;
	
	public NoiseChannel(double sd) {
		rng = new MersenneTwister(System.currentTimeMillis());
		noise = new NormalDistribution(rng, 1.0, sd);
	}
	
	public Signal noise(Signal s) {
		double rnd = noise.sample();
		return new Signal(s.multiply(rnd));
	}
}
