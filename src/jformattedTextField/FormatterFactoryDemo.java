package jformattedTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class FormatterFactoryDemo extends JPanel implements PropertyChangeListener{
	//Values for the text fields
	private double amount = 100000;
	private double rate = .075;  //7.5 %
	private int numPeriods = 30;

	//Labels to identify the fields
	private JLabel amountLabel;
	private JLabel rateLabel;
	private JLabel numPeriodsLabel;
	private JLabel paymentLabel;

	//Strings for the labels
	private static String amountString = "Loan Amount: ";
	private static String rateString = "APR (%): ";
	private static String numPeriodsString = "Years: ";
	private static String paymentString = "Monthly Payment: ";

	//Fields for data entry
	private JFormattedTextField amountField;
	private JFormattedTextField rateField;
	private JFormattedTextField numPeriodsField;
	private JFormattedTextField paymentField;

	//Formats to format and parse numbers
	private NumberFormat amountDisplayFormat;
	private NumberFormat amountEditFormat;
	private NumberFormat percentDisplayFormat;
	private NumberFormat percentEditFormat;
	private NumberFormat paymentFormat;

	public FormatterFactoryDemo() {
		super(new BorderLayout());
		setUpFormat();
		double payment = computePayment(amount,
				rate,
				numPeriods);

		//Create the labels.
		amountLabel = new JLabel(amountString);
		rateLabel = new JLabel(rateString);
		numPeriodsLabel = new JLabel(numPeriodsString);
		paymentLabel = new JLabel(paymentString);

		//Create the text fields and set them up.
		amountField = new JFormattedTextField(
				//第一个参数指定了格式化文本框所需的
				//格式器。第二个参数指定了所显示的格式器，该格式器
				//将在文本框无焦点时使用，第三个参数指定了编辑格式器
				//他在文本框获得焦点时使用
				new DefaultFormatterFactory(
						new NumberFormatter(amountDisplayFormat),
						new NumberFormatter(amountDisplayFormat),
						new NumberFormatter(amountEditFormat)));
		amountField.setValue(new Double(amount));
		amountField.setColumns(10);
		amountField.addPropertyChangeListener("value", this);

		NumberFormatter percentEditFormatter =
				new NumberFormatter(percentEditFormat) {
			public String valueToString(Object o)
					throws ParseException {
				Number number = (Number)o;
				if (number != null) {
					double d = number.doubleValue() * 100.0;
					number = new Double(d);
				}
				return super.valueToString(number);
			}
			public Object stringToValue(String s)
					throws ParseException {
				Number number = (Number)super.stringToValue(s);
				if (number != null) {
					double d = number.doubleValue() / 100.0;
					number = new Double(d);
				}
				return number;
			}
		};
		rateField = new JFormattedTextField(
				new DefaultFormatterFactory(
						new NumberFormatter(percentDisplayFormat),
						new NumberFormatter(percentDisplayFormat),
						percentEditFormatter));
		rateField.setValue(new Double(rate));
		rateField.setColumns(10);
		rateField.addPropertyChangeListener("value", this);

		numPeriodsField = new JFormattedTextField();
		numPeriodsField.setValue(new Integer(numPeriods));
		numPeriodsField.setColumns(10);
		numPeriodsField.addPropertyChangeListener("value", this);

		paymentField = new JFormattedTextField(paymentFormat);
		paymentField.setValue(new Double(payment));
		paymentField.setColumns(10);
		paymentField.setEditable(false);
		paymentField.setForeground(Color.red);

		//Tell accessibility tools about label/textfield pairs.
		amountLabel.setLabelFor(amountField);
		rateLabel.setLabelFor(rateField);
		numPeriodsLabel.setLabelFor(numPeriodsField);
		paymentLabel.setLabelFor(paymentField);

		//Lay out the labels in a panel.
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(amountLabel);
		labelPane.add(rateLabel);
		labelPane.add(numPeriodsLabel);
		labelPane.add(paymentLabel);

		//Layout the text fields in a panel.
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(amountField);
		fieldPane.add(rateField);
		fieldPane.add(numPeriodsField);
		fieldPane.add(paymentField);

		//Put the panels in this panel, labels on left,
		//text fields on right.
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(labelPane, BorderLayout.CENTER);
		add(fieldPane, BorderLayout.LINE_END);
	}
	//create and set up number format. these objects also parse
	//number input by user
	private void setUpFormat() {
		amountDisplayFormat = NumberFormat.getCurrencyInstance();
		amountDisplayFormat.setMinimumFractionDigits(0);
		amountEditFormat = NumberFormat.getNumberInstance();

		percentDisplayFormat = NumberFormat.getPercentInstance();
		percentDisplayFormat.setMinimumFractionDigits(2);
		percentEditFormat = NumberFormat.getNumberInstance();
		percentEditFormat.setMinimumFractionDigits(2);

		paymentFormat = NumberFormat.getCurrencyInstance();
	}
	/** Called when a field's "value" property changes. */
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		if (source == amountField) {
			amount = ((Number)amountField.getValue()).doubleValue();
		} else if (source == rateField) {
			rate = ((Number)rateField.getValue()).doubleValue();
		} else if (source == numPeriodsField) {
			numPeriods = ((Number)numPeriodsField.getValue()).intValue();
		}

		double payment = computePayment(amount, rate, numPeriods);
		paymentField.setValue(new Double(payment));
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("FormatterFactoryDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add contents to the window.
		frame.add(new FormatterFactoryDemo());

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}

	//Compute the monthly payment based on the loan amount,
	//APR, and length of loan.
	double computePayment(double loanAmt, double rate, int numPeriods) {
		double I, partial1, denominator, answer;

		numPeriods *= 12;        //get number of months
		if (rate > 0.001) {
			I = rate / 12.0;         //get monthly rate from annual
			partial1 = Math.pow((1 + I), (0.0 - numPeriods));
			denominator = (1 - partial1) / I;
		} else { //rate ~= 0
			denominator = numPeriods;
		}

		answer = (-1 * loanAmt) / denominator;
		return answer;
	}

}
