package simulation;

import java.util.ArrayList;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

public class Simulator {
	Encoder encoder;
	Decoder decoder;
	
	public Simulator(int s) {	
		encoder = new Encoder(s);
		decoder = new Decoder(encoder);
	}
	public double simulate(int N, double eps) {
		RandomGenerator rnd = new MersenneTwister(System.currentTimeMillis());
		ArrayList<Signal> sigs = new ArrayList<Signal>(); 
		NoiseChannel ch = new NoiseChannel(eps);
		Integer[] in = new Integer[N];
		for(int i = 0; i < N; i++) {
			in[i] = rnd.nextInt(encoder.codeWordLen == 5 ? 16 : 64);
			//System.out.println("in " + in[i]);
			Signal sig = encoder.encode(in[i]);
			sigs.add(ch.noise(sig));
		}
		Integer[] out = decoder.decode(sigs.toArray(new Signal[1]));
		int errors = 0;
		for(int i = 0; i < N; i++) {
			errors += (in[i] == out[i]) ? 0 : 1;
			//System.out.println("out " + out[i]);
		}
		//System.out.println(errors * 1.0 / N);
		return errors * 1.0 / N;
	}
}
