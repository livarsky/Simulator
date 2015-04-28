package simulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Encoder {
	int state;
	public int y;
	Signal[] signalMapper;
	public Integer[] preencodedTable;
	public Map<Integer, Set<Integer> > transitions;
	public int codeWordLen;
	
	public Encoder(int standard) {
		state = 0;
		y = 0;
		initSignalMapper(standard);
		preencodedTable = new Integer[1 << 9];
		transitions = new HashMap<Integer, Set<Integer>>();
		initTransition();
	}
	
	private void initSignalMapper(int s) {
		if (s == 0) {
			codeWordLen = 5;
			signalMapper = new Signal[32];
			signalMapper[0] = new Signal(-4, 1);
			signalMapper[1] = new Signal(0, -3);
			signalMapper[2] = new Signal(0, 1);
			signalMapper[3] = new Signal(4, 1);
			signalMapper[4] = new Signal(4, -1);
	        signalMapper[5] = new Signal( 0 , 3  );
	        signalMapper[6] = new Signal( 0 , -1 );
	        signalMapper[7] = new Signal( -4, -1 );
	        signalMapper[8] = new Signal( -2, 3  );
	        signalMapper[9] = new Signal( -2, -1 );
	        signalMapper[10] = new Signal( 2 , 3  );
	        signalMapper[11] = new Signal( 2 , -1 );
	        signalMapper[12] = new Signal( 2 , -3 );
	        signalMapper[13] = new Signal( 2 , 1  );
	        signalMapper[14] = new Signal( -2, -3 );
	        signalMapper[15] = new Signal( -2, 1  );
	        signalMapper[16] = new Signal( -3, -2 );
	        signalMapper[17] = new Signal( 1 , -2 );
	        signalMapper[18] = new Signal( -3,  2 );
	        signalMapper[19] = new Signal( 1 ,  2 );
	        signalMapper[20] = new Signal( 3,   2 );
	        signalMapper[21] = new Signal( -1,  2 );
	        signalMapper[22] = new Signal( 3,  -2 );
	        signalMapper[23] = new Signal( -1, -2 );
	        signalMapper[24] = new Signal( 1 ,  4 );
	        signalMapper[25] = new Signal( -3,  0 );
	        signalMapper[26] = new Signal( 1 ,  0 );
	        signalMapper[27] = new Signal( 1 , -4 );
	        signalMapper[28] = new Signal( -1, -4 );
	        signalMapper[29] = new Signal( 3 ,  0 );
	        signalMapper[30] = new Signal( -1,  0 );
	        signalMapper[31] = new Signal( -1,  4 );
		} else if (s == 1) {
			codeWordLen = 7;
			signalMapper = new Signal[128];
			signalMapper[0] = new Signal(-8, -3);
			signalMapper[1] = new Signal(8, -3);
			signalMapper[2] = new Signal(4, -3);
			signalMapper[3] = new Signal(4, -7);
			signalMapper[4] = new Signal(-4, -3);
			signalMapper[5] = new Signal(-4, -7);
			signalMapper[6] = new Signal(0, -3);
			signalMapper[7] = new Signal(0, -7);
			signalMapper[8] = new Signal(-8, 1);
			signalMapper[9] = new Signal(8, 1);
			signalMapper[10] = new Signal(4, 1);
			signalMapper[11] = new Signal(4, 5);
			signalMapper[12] = new Signal(-4, 1);
			signalMapper[13] = new Signal(-4, 5);
			signalMapper[14] = new Signal(0, 1);
			signalMapper[15] = new Signal(0, 5);
			signalMapper[16] = new Signal(8, 3);
			signalMapper[17] = new Signal(-8, 3);
			signalMapper[18] = new Signal(-4, 3);
			signalMapper[19] = new Signal(-4, 7);
			signalMapper[20] = new Signal(4, 3);
			signalMapper[21] = new Signal(4, 7);
			signalMapper[22] = new Signal(0, 3);
			signalMapper[23] = new Signal(0, 7);
			signalMapper[24] = new Signal(8, -1);
			signalMapper[25] = new Signal(-8, -1);
			signalMapper[26] = new Signal(-4, -1);
			signalMapper[27] = new Signal(-4, -5);
			signalMapper[28] = new Signal(4, -1);
			signalMapper[29] = new Signal(4, -5);
			signalMapper[30] = new Signal(0, -1);
			signalMapper[31] = new Signal(0, -5);
			signalMapper[32] = new Signal(2, -9);
			signalMapper[33] = new Signal(2, 7);
			signalMapper[34] = new Signal(2, 3);
			signalMapper[35] = new Signal(6, 3);
			signalMapper[36] = new Signal(2, -5);
			signalMapper[37] = new Signal(6, -5);
			signalMapper[38] = new Signal(2, -1);
			signalMapper[39] = new Signal(6, -1);
			signalMapper[40] = new Signal(-2, -9);
			signalMapper[41] = new Signal(-2, 7);
			signalMapper[42] = new Signal(-2, 3);
			signalMapper[43] = new Signal(-6, 3);
			signalMapper[44] = new Signal(-2, -5);
			signalMapper[45] = new Signal(-6, -5);
			signalMapper[46] = new Signal(-2, -1);
			signalMapper[47] = new Signal(-6, -1);
			signalMapper[48] = new Signal(-2, 9);
			signalMapper[49] = new Signal(-2, -7);
			signalMapper[50] = new Signal(-2, -3);
			signalMapper[51] = new Signal(-6, -3);
			signalMapper[52] = new Signal(-2, 5);
			signalMapper[53] = new Signal(-6, 5);
			signalMapper[54] = new Signal(-2, 1);
			signalMapper[55] = new Signal(-6, 1);
			signalMapper[56] = new Signal(2, 9);
			signalMapper[57] = new Signal(2, -7);
			signalMapper[58] = new Signal(2, -3);
			signalMapper[59] = new Signal(6, -3);
			signalMapper[60] = new Signal(2, 5);
			signalMapper[61] = new Signal(6, 5);
			signalMapper[62] = new Signal(2, 1);
			signalMapper[63] = new Signal(6, 1);
			signalMapper[64] = new Signal(9, 2);
			signalMapper[65] = new Signal(-7, 2);
			signalMapper[66] = new Signal(-3, 2);
			signalMapper[67] = new Signal(-3, 6);
			signalMapper[68] = new Signal(5, 2);
			signalMapper[69] = new Signal(5, 6);
			signalMapper[70] = new Signal(1, 2);
			signalMapper[71] = new Signal(1, 6);
			signalMapper[72] = new Signal(9, -2);
			signalMapper[73] = new Signal(-7, -2);
			signalMapper[74] = new Signal(-3, -2);
			signalMapper[75] = new Signal(-3, -6);
			signalMapper[76] = new Signal(5, -2);
			signalMapper[77] = new Signal(5, -6);
			signalMapper[78] = new Signal(1, -2);
			signalMapper[79] = new Signal(1, -6);
			signalMapper[80] = new Signal(-9, -2);
			signalMapper[81] = new Signal(7, -2);
			signalMapper[82] = new Signal(3, -2);
			signalMapper[83] = new Signal(3, -6);
			signalMapper[84] = new Signal(-5, -2);
			signalMapper[85] = new Signal(-5, -6);
			signalMapper[86] = new Signal(-1, -2);
			signalMapper[87] = new Signal(-1, -6);
			signalMapper[88] = new Signal(-9, 2);
			signalMapper[89] = new Signal(7, 2);
			signalMapper[90] = new Signal(3, 2);
			signalMapper[91] = new Signal(3, 6);
			signalMapper[92] = new Signal(-5, 2);
			signalMapper[93] = new Signal(-5, 6);
			signalMapper[94] = new Signal(-1, 2);
			signalMapper[95] = new Signal(-1, 6);
			signalMapper[96] = new Signal(-3, 8);
			signalMapper[97] = new Signal(-3, -8);
			signalMapper[98] = new Signal(-3, -4);
			signalMapper[99] = new Signal(-7, -4);
			signalMapper[100] = new Signal(-3, 4);
			signalMapper[101] = new Signal(-7, 4);
			signalMapper[102] = new Signal(-3, 0);
			signalMapper[103] = new Signal(-7, 0);
			signalMapper[104] = new Signal(1, 8);
			signalMapper[105] = new Signal(1, -8);
			signalMapper[106] = new Signal(1, -4);
			signalMapper[107] = new Signal(5, -4);
			signalMapper[108] = new Signal(1, 4);
			signalMapper[109] = new Signal(5, 4);
			signalMapper[110] = new Signal(1, 0);
			signalMapper[111] = new Signal(5, 0);
			signalMapper[112] = new Signal(3, -8);
			signalMapper[113] = new Signal(3, 8);
			signalMapper[114] = new Signal(3, 4);
			signalMapper[115] = new Signal(7, 4);
			signalMapper[116] = new Signal(3, -4);
			signalMapper[117] = new Signal(7, -4);
			signalMapper[118] = new Signal(3, 0);
			signalMapper[119] = new Signal(7, 0);
			signalMapper[120] = new Signal(-1, -8);
			signalMapper[121] = new Signal(-1, 8);
			signalMapper[122] = new Signal(-1, 4);
			signalMapper[123] = new Signal(-5, 4);
			signalMapper[124] = new Signal(-1, -4);
			signalMapper[125] = new Signal(-5, -4);
			signalMapper[126] = new Signal(-1, 0);
			signalMapper[127] = new Signal(-5, 0);
		}
	}
	
	public Signal encode(int input) {
		Integer[] bstate = new Integer[]{state / 4, (state % 4) / 2, state % 2};
		Integer[] by = new Integer[]{y / 4, (y % 4) / 2, y % 2};
		Integer[] bq = new Integer[]{input / (2 << (codeWordLen - 3)), (input % (2 << (codeWordLen - 3))) / (2 << (codeWordLen - 4))};
		
		//System.out.println(input + " " + bq[0] + " " + bq[1] + " " + (2 << 5));
		
		Integer[] currState = bstate.clone();
		Integer[] currY = by.clone();
		
		by[0] = bstate[0];
		by[1] = (bq[0] + currY[1]) % 2;
		by[2] = ((bq[0] & currY[1]) + currY[2] + bq[1]) % 2;
		
	    bstate[2] = by[0];
	    bstate[1] = (by[1] + by[2] + currState[2] + 
	                (((currState[1] + by[2]) % 2) & currState[0])) % 2;
	    bstate[0] = (currState[1] + by[2] + (by[1] & currState[0])) % 2;
		
	    //q.set(0, y.get(1));
	    //q.set(1, y.get(2));
	    //int encoded = q.getInt() + (y.get(0) ? 1 << q.length() : 0);
	    //assert(state.getInt() + (y.getInt() << 3) < 32);
	    state = bstate[0] * 4 + bstate[1] * 2 + bstate[2];
	    y = by[0] * 4 + by[1] * 2 + by[2];
	    
	   
	    int encoded = y * (2 << (codeWordLen - 4)) + input % (2 << (codeWordLen - 4));
	    //System.out.println("encode " + codeWordLen + " " + encoded);
	    return signalMapper[encoded];
	}
	
	public int simulateEncode(int state_, int y_, int input) {
		
		Integer[] state = new Integer[]{state_ / 4, (state_ % 4) / 2, state_ % 2};
		Integer[] y = new Integer[]{y_ / 4, (y_ % 4) / 2, y_ % 2};
		Integer[] q = new Integer[]{input / 2, input % 2};
		
		//System.out.println(state_ + " " + state[0] + " " + state[1] + " " + state[2]);
		
		Integer[] currState = state.clone();
		Integer[] currY = y.clone();
		
		y[0] = state[0];
		y[1] = (q[0] + currY[1]) % 2;
		y[2] = ((q[0] & currY[1]) + currY[2] + q[1]) % 2;
		
	    state[2] = y[0];
	    state[1] = (y[1] + y[2] + currState[2] + 
	                (((currState[1] + y[2]) % 2) & currState[0])) % 2;
	    state[0] = (currState[1] + y[2] + (y[1] & currState[0])) % 2;
		
	    //q.set(0, y.get(1));
	    //q.set(1, y.get(2));
	    //int encoded = q.getInt() + (y.get(0) ? 1 << q.length() : 0);
	    //assert(state.getInt() + (y.getInt() << 3) < 32);
	    return state[0] * 4 + state[1] * 2 + state[2] + ((y[0] * 4 + y[1] * 2 + y[2]) << 3);
	}
	
	void initTransition() {
		for(int state = 0; state < 8; state++) {
	        Set<Integer> stateTransitions = new HashSet<Integer>();
	        for(int y = 0; y < 8; y++) {
	            for(int q = 0; q < 4; q++) {
	                int nextState = simulateEncode(state, y, q);
	                //System.out.println("aa  " + state + " " + nextState % 8 + " " + nextState / 8);
	                //if (preencodedTable[y * 64 + state * 8 + nextState % 8] != null) System.out.println("WTF");
	                preencodedTable[y * 64 + state * 8 + nextState % 8] = (nextState / 8) * 4 + q;
	                //System.out.println(state + " " + (nextState % 8) + " " + y + " " + (nextState / 8) + " " + q);
	                stateTransitions.add(nextState);
	            }
	        }
	        transitions.put(state, stateTransitions);
	    }
	}
	
	public Signal signalMap(int value) {
		return signalMapper[value];
	}
	
}
