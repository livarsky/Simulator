package simulation;

import org.apache.commons.math3.complex.Complex;

public class Signal extends Complex {
	
	private static final long serialVersionUID = -1417902467175505805L;

	public Signal(double real, double imaginary) {
		super(real, imaginary);
	}

	public Signal(Complex x) {
		super(x.getReal(), x.getImaginary());
	}
}
