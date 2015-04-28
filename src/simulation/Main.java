package simulation;

import java.awt.*;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.*;
import org.jfree.ui.*;

public class Main extends ApplicationFrame  {
	public Main(String s) {
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		getContentPane().add(jpanel);
	}

	public static JPanel createDemoPanel() {
		NumberAxis numberaxis = new NumberAxis("Параметр шума");
		numberaxis.setAutoRangeIncludesZero(false);
		NumberAxis numberaxis1 = new NumberAxis("Успешное декодирование, %");
		numberaxis1.setAutoRangeIncludesZero(false);
		XYSplineRenderer xysplinerenderer = new XYSplineRenderer();
		XYPlot xyplot = new XYPlot(createSampleData(), numberaxis, numberaxis1, xysplinerenderer);
		XYLineAndShapeRenderer renderer =
			    (XYLineAndShapeRenderer) xyplot.getRenderer();
			renderer.setBaseShapesVisible(false);
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
		JFreeChart jfreechart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
		ChartUtilities.applyCurrentTheme(jfreechart);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		return chartpanel;
	}

	private static XYDataset createSampleData()
	{
		XYSeries v32 = new XYSeries("V32");
		Simulator s = new Simulator(0);
		v32.add(0.0, 1.0);
		for(double eps = 0.05; eps <= 1.5; eps += 0.05) {
			double errs = s.simulate(100000, eps);
			v32.add(eps, 1.0 - errs);
			//System.out.println("v32: " + eps + ", " + (1.0 - errs));
		}
		
		XYSeries v32bis = new XYSeries("V32bis");
		s = new Simulator(1);
		v32bis.add(0.0, 1.0);
		for(double eps = 0.05; eps <= 1.5; eps += 0.05) {
			double errs = s.simulate(100000, eps);
			v32bis.add(eps, 1.0 - errs);
			//System.out.println("v32bis:" + eps + ", " + (1.0 - errs));
		}
		
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(v32);
		xyseriescollection.addSeries(v32bis);
		return xyseriescollection;
	}

	public static void main(String args[]) {
		Main xysplinerendererdemo1a = new Main("Simulation");
		xysplinerendererdemo1a.pack();
		RefineryUtilities.centerFrameOnScreen(xysplinerendererdemo1a);
		xysplinerendererdemo1a.setVisible(true);
	}
}
