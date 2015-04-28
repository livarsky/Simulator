package simulation;

import java.util.ArrayList;
import java.util.Arrays;

public class Decoder {
	Encoder encoder;
	
	public Decoder(Encoder enc) {
		encoder = enc;
	}
	
	double distance(Signal a, Signal b) {
		return a.subtract(b).abs();
	}
	
	int getNearest(Signal s, int codeWordPart) {
		double _min = 9999999.0;
		int nearestWord = -1;
		for(int part = 0; part < (1 << (encoder.codeWordLen - 3)); part++) {
			int codeWord = (codeWordPart << (encoder.codeWordLen - 3)) + part;
			//System.out.println("code=" + codeWord + " " + codeWordPart + " " + part);
			double cost = distance(s, encoder.signalMap(codeWord));
			if (cost < _min) {
				_min = cost;
				nearestWord = codeWord;
			}
		}
		return nearestWord;
	}
	
	private class Transition {
		public double cost;
		public int to;
		public int word;
		
		public Transition(int s, double c, int w) {
			cost = c;
			to = s;
			word = w;
		}
	}
	
	public Integer[] decode(Signal[] signals) {
		
		ArrayList< ArrayList< ArrayList <Transition> > > lattice = new ArrayList< ArrayList< ArrayList <Transition> > >();
		int signalsCount = signals.length;
		
		for(Signal sig : signals) {
			ArrayList< ArrayList<Transition> > layer = new ArrayList< ArrayList<Transition> >();
			for(int state = 0; state < 8; state++) {
				ArrayList<Transition> transitions = new ArrayList<Transition>();
				
				for(int nextState : encoder.transitions.get(state)) {
					//System.out.println("next state " +  state + " " + nextState % 8);
					int nearestCodeWord = getNearest(sig, nextState / 8);
					//System.out.println("signal " +  sig.getReal() + " " + sig.getImaginary() + " " + nearestCodeWord);
					double cost = distance(sig, encoder.signalMap(nearestCodeWord));
					//System.out.println("sig cost " + sig + " " + cost);
					transitions.add(new Transition(nextState % 8, cost, nearestCodeWord));
				}
				//System.out.println("state changed");
				layer.add(transitions);
			}
			//System.out.println("signal changed");
			
			lattice.add(layer);
		}
		Double[] totalCost = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		ArrayList< ArrayList<Integer> > from = new ArrayList< ArrayList<Integer> >();
		ArrayList< ArrayList<Integer> > codeWords = new ArrayList< ArrayList<Integer> >();
		
		for(ArrayList< ArrayList<Transition> > layer : lattice) {
			Integer[] prev = new Integer[8];
			Integer[] codeWord = new Integer[8];
			Double[] minCost = new Double[8];
			Arrays.fill(minCost, 9999999.0);
			for(int state = 0; state < 8; state++) {
				for(Transition s : layer.get(state)) {
					double cost = totalCost[state] + s.cost;
					//System.out.println("WTF: " +  s.cost);
					if (cost < minCost[s.to]) {
						minCost[s.to] = cost;
						//System.out.println("prev= " + s.to + " " + state + " " + prev.size());
						prev[s.to] = state;
						codeWord[s.to] = s.word;
					}
				}
			}
			//for(int i = 0; i < 8; i++) {
			//	System.out.println(i + ": " + totalCost[i] + " -> " + minCost[i] + "  " + prev[i] + "  " + codeWord[i]);
			//}
			//System.out.println("");
			totalCost = minCost;
			from.add(new ArrayList<Integer>(Arrays.asList(prev)));
			codeWords.add(new ArrayList<Integer>(Arrays.asList(codeWord)));
		}
		
		double minStateCost = 999990.9;
		int minState = -1;
		for(int i = 0; i < 8; i++) {
			if (minStateCost > totalCost[i]) {
				minStateCost = totalCost[i];
				minState = i;
			}
		}
		
		//System.out.println("minstate " + minStateCost +  " " + minState);
		
		Integer[] path = new Integer[signalsCount + 1];
		path[path.length - 1] = minState;
		for(int i = signalsCount - 1; i >= 0; i--) {
			int nextMinState = from.get(i).get(minState);
			//System.out.println(codeWord);
			path[i] = nextMinState;
			minState = nextMinState;
		}
		//System.out.println("minstate 0? : " + minState);
		
		Integer[] result = new Integer[signalsCount];
		
		int y = minState;
		int mult = 1 << (encoder.codeWordLen - 3);
		for(int i = 0; i < signalsCount; i++) {
			int nextMinState = path[i];
			int codeWord = codeWords.get(i).get(path[i + 1]);
			//System.out.println("aas " + y + " " + nextMinState + " " +  path[i + 1] + " " + codeWord / 4);
			//System.out.println("preenc " + codeWord + " " + encoder.preencodedTable[(y * 64 + nextMinState * 8 + path[i + 1])]);
			result[i] = (encoder.preencodedTable[y * 64 + nextMinState * 8 + path[i + 1]] % 4) * mult + codeWord % mult;
			y = codeWord / mult;
			//System.out.println(encoder.preencodedTable[y * 64 + nextMinState * 8 + path[i + 1]] / 4 + " " + y);
			//minState = nextMinState;
		}
		return result;
	}
	
}
