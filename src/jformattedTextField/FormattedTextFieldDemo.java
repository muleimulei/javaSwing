package jformattedTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FormattedTextFieldDemo extends JPanel implements PropertyChangeListener{
	//values for the fields
	private double amount=100000;
	private double rate = 7.5;
	private int numPeriods=30;
	//labels to identify the fields
	private JLabel amountLabel;
	private JLabel rateLabel;
	private JLabel numPeriodsLabel;
	private JLabel paymentLabel;
	//string for the label
	private static String amountString="Loan Amount";
	private static String rateString="APR (%)";
	private static String numPeriodString ="Years :";
	private static String paymentString="Monthly Payment";
	
	//formats to format and parse number
	private NumberFormat amountFormat;
	private NumberFormat percentFormat;
	private NumberFormat paymentFormat;
	
	//fields for data entry
	private JFormattedTextField amountField;
	private JFormattedTextField rateField;
	private JFormattedTextField numPeriodsField;
	private JFormattedTextField paymentField;
	public FormattedTextFieldDemo(){
		super(new BorderLayout());
		setUpFormats();
		double payment = computePayment(amount,rate,numPeriods);
		//create the label
		amountLabel = new JLabel(amountString);
		rateLabel = new JLabel(rateString);
		numPeriodsLabel = new JLabel(numPeriodString);
		paymentLabel = new JLabel(paymentString);
		
		//create the text fields and set them up
		amountField = new JFormattedTextField(amountFormat);
		amountField.setValue(new Double(amount));
		amountField.setColumns(10);
		amountField.addPropertyChangeListener("value", this);
		
		rateField = new JFormattedTextField(percentFormat);
		rateField.setValue(new Double(rate));
		rateField.setColumns(10);
		rateField.addPropertyChangeListener("value", this);
		
		numPeriodsField = new JFormattedTextField();
		numPeriodsField.setValue(new Double(payment));
		numPeriodsField.setColumns(10);
		numPeriodsField.addPropertyChangeListener("value", this);
		
		paymentField = new JFormattedTextField(paymentFormat);
		paymentField.setValue(new Double(payment));
		paymentField.setColumns(10);
		paymentField.setEditable(false);
		paymentField.setForeground(Color.RED);
		
		//tell accessibility tools about label /textfield pairs
		amountLabel.setLabelFor(amountField);
		rateLabel.setLabelFor(rateField);
		numPeriodsLabel.setLabelFor(numPeriodsField);
		paymentLabel.setLabelFor(paymentField);
		
		//lay out the labels in a panel
		JPanel labelpane = new JPanel(new GridLayout(0, 1));
		labelpane.add(amountLabel);
		labelpane.add(rateLabel);
		labelpane.add(numPeriodsLabel);
		labelpane.add(paymentLabel);
		
		//layout the text field in a panel
		JPanel fieldpane = new JPanel(new GridLayout(0, 1));
		fieldpane.add(amountField);
		fieldpane.add(rateField);
		fieldpane.add(numPeriodsField);
		fieldpane.add(paymentField);
		
		//put the panels in this panel,label on left, text fields on right
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(labelpane,BorderLayout.CENTER);
		add(fieldpane,BorderLayout.LINE_END);
		
		
	}
	  //Compute the monthly payment based on the loan amount,
    //APR, and length of loan.
    double computePayment(double loanAmt, double rate, int numPeriods) {
        double I, partial1, denominator, answer;
 
        numPeriods *= 12;        //get number of months
        if (rate > 0.01) {
            I = rate / 100.0 / 12.0;         //get monthly rate from annual
            partial1 = Math.pow((1 + I), (0.0 - numPeriods));
            denominator = (1 - partial1) / I;
        } else { //rate ~= 0
            denominator = numPeriods;
        }
 
        answer = (-1 * loanAmt) / denominator;
        return answer;
    }
	//create and set up number formats.
	//these objects also parse numbers input by user
	private void setUpFormats() {
		amountFormat = NumberFormat.getNumberInstance();
		percentFormat = NumberFormat.getNumberInstance();
		percentFormat.setMinimumFractionDigits(3);
		paymentFormat = NumberFormat.getCurrencyInstance();
	}
	@Override
	//called when a field's value property change
	public void propertyChange(PropertyChangeEvent evt) {
		Object source = evt.getSource();
		if(source==amountField){
			amount = ((Number)amountField.getValue()).doubleValue();
		}else if(source==rateField){
			rate = ((Number)rateField.getValue()).doubleValue();
		}else if(source==numPeriodsField){
			numPeriods = ((Number)numPeriodsField.getValue()).intValue();
		}
		double payment = computePayment(amount, rate, numPeriods);
		paymentField.setValue(new Double(payment));
	}
	
	public static void createAndShowGUI(){
		//create and set up the window
		JFrame frame = new JFrame("FormattedTextFieldDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add content to the window
		frame.setContentPane(new FormattedTextFieldDemo());
		
		//display the window
		frame.pack();
		frame.setVisible(true);
	}
   public static void main(String[] args) {
	   SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			//turn off metal's use of bold fonts
			UIManager.put("swing.boldMetal", Boolean.FALSE);
			createAndShowGUI();
		}
	});
   }
}
