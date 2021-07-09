/*
 * (C) 2010 by me
 * All Rights Reserved
 * FILE: 		FinanceGui.java
 * VERSION 0.1
 * 
 * MODIFIED by 	Jochen Weiss
 * MODIFIED 	31.09.2010
 * AUTHOR 		Jochen Weiss
 * CREATED		17.08.2010
*/
package jogi.finanzen;


import java.awt.Color;
import java.awt.Font;


import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup ;

import java.io.File;
import java.io.FileWriter;
import java.lang.Double;



public class FinanceGui extends JFrame implements ActionListener, ChangeListener, Runnable
{
    /**
     * 
     */
    
    JButton jbCalculate     = null;
    JButton jbSaveAs        = null;            
    JButton jbSave          = null;
    JButton jbExit          = null;
    JButton jbRemoveRow     = null;
    JButton jbAutorun       = null;
    JMenu   jmMenu          = null;
    
    ButtonGroup bgAutorun;
    JRadioButton jrbZinssatz;
    JRadioButton jrbKredit;
    JRadioButton jrbMonatlRate;
    JRadioButton jrbLaufzeit;
    JRadioButton jrbRestschuld;
    
    JComboBox jcbChoice        = null;
    JTextField jtfInterestRate;
    JTextField jtfCapital;
    JTextField jtfMonthlyRate;
    JTextField jtfNoRepayment;
    JTextField jtfDurationYears;
    JTextField jtfDurationMounth;
    JTextField jtfRemainderOfADebt;
    JTextField jtfStartValue;
    JTextField jtfSteps;
    JTextField jtfEndValue;
    
    JLabel jlCreditCalculator;
    JLabel jlInterestRate;
    JLabel jlCapital;
    JLabel jlMonthlyRate;
    JLabel jlNoRepayment;
    JLabel jlDurationYears;
    JLabel jlDurationMounth;
    JLabel jlRemainderOfADebt;
    JLabel jlStartValue;
    JLabel jlSteps;
    JLabel jlEndValue;
    
    JSlider jsInterestRate;
    JSlider jsCapital;
    JSlider jsMonthlyRate;
    JSlider jsDurationYears;
    JSlider jsRemainderOfADebt;
    
    int selectedChoiceIndex = 0;
    
    double lastUsedKredit = 0;
    double lastUsedZinssatz = 0;
    double lastUsedAnfangstilgung = 0;
    double lastUsedmonatRate = 0;
    double lastUsedGezahlt = 0;
    double lastUsedAbtrag = 0;
    double lastUsedZinsen = 0;
    double lastUsedrestschuld = 0;
    double lastUsedlaufzeitJahre = 0;
    
    double savedUsedKredit = 0;
    double savedUsedZinssatz = 0;
    double savedUsedmonatRate = 0;
    double savedUsedAnfangstilgung = 0;
    double savedUsedGezahlt = 0;
    double savedUsedAbtrag = 0;
    double savedUsedZinsen = 0;
    double savedUsedrestschuld = 0;
    double savedUsedlaufzeitJahre = 0;
    
    
    JLabel jlTest   = null;
    
    Calculator calc;
    private static final long serialVersionUID = 1L;

    ImageIcon buttonIcon;
    JScrollPane scrollPane;
    JTable table;
    JProgressBar autorunProgressBar;
    DefaultTableModel model;
    
    private boolean isAutoRun = false;
    private double storeRestschulden;
    
    FinanceGui()
    {
        this.setLayout(null); 
        this.setSize(720, 760); 
        this.setResizable(true);
        
        Font ft = new Font("Serif", Font.BOLD,30);
        jlCreditCalculator = new JLabel("Kreditrechner");
        jlCreditCalculator.setBounds(10, 5, 200, 35);
        jlCreditCalculator.setFont(ft);
        this.add(jlCreditCalculator);
        jcbChoice = new JComboBox(); 
        jcbChoice.addItem( "Laufzeit" ); 
        jcbChoice.addItem( "Rate" ); 
        jcbChoice.addItem( "Restschuld" );
        jcbChoice.addItem( "Kredit" );
        jcbChoice.addItem( "-------" );
        
        jcbChoice.addActionListener(this);
        
        jcbChoice.setBounds(10, 40, 150, 20);
        this.add(jcbChoice);
        
        jbCalculate = new JButton("calculate");
        jbCalculate.setBounds(170, 40, 100, 20);
        jbCalculate.setToolTipText("Berechnen der eingegebenen Werte");
        this.add(jbCalculate);
        
        
        jbSave = new JButton("speichern");
        jbSave.setBounds(280, 40, 100, 20);
        jbSave.setToolTipText("speichern der Ergebnisse in Tabelle");
        this.add(jbSave);
        
        
        
        jbAutorun = new JButton("Autorun"); 
        jbAutorun.setBounds(480, 40, 100, 20);
        jbAutorun.setToolTipText("Berechnen aufsteigender Werte. Ergebnisse werden in Tabelle gespeichert");
        this.add(jbAutorun);
        
        jlStartValue = new JLabel("Startwert");
        jlStartValue.setBounds(480, 70, 100, 20);
        this.add(jlStartValue);
        
        jtfStartValue = new JTextField("0");
        jtfStartValue.setBounds(580, 70, 100, 20);
        this.add(jtfStartValue);
        
        jlSteps = new JLabel("Schritte");
        jlSteps.setBounds(480, 90, 100, 20);
        this.add(jlSteps);
        
        jtfSteps = new JTextField("0.1");
        jtfSteps.setBounds(580, 90, 100, 20);
        this.add(jtfSteps);
        
        jlEndValue = new JLabel("Endwert");
        jlEndValue.setBounds(480, 110, 100, 20);
        this.add(jlEndValue);
        
        jtfEndValue = new JTextField("5");
        jtfEndValue.setBounds(580, 110, 100, 20);
        this.add(jtfEndValue);
        
        jbSaveAs        = new JButton("save as...");            
        jbSaveAs.setBounds(625, 555, 83, 20);
        jbSaveAs.setToolTipText("Speichern des Inhalts der Tabelle in Textdatei");
        this.add(jbSaveAs);
        
        jbRemoveRow = new JButton("löschen");
        jbRemoveRow.setBounds(625,580,83,20);
        jbRemoveRow.setToolTipText("Löschen der ausgewählten Reihen in Tabelle. Alle auswählen mit 'strg+A'");
        this.add(jbRemoveRow); 
        
        jtfInterestRate = new JTextField("5");
        jtfInterestRate.setBounds(10,65,145,20);
        jlInterestRate = new JLabel("Zinssatz in %");
        jlInterestRate.setBounds(160,65,100,20);
        
        jsInterestRate = new JSlider(0,1000,500);
        jsInterestRate.setBounds(270, 65, 150, 20);
        jsInterestRate.addChangeListener(this);
        
        jtfCapital= new JTextField("100000");
        jtfCapital.setBounds(10, 90, 150, 20);
        jlCapital = new JLabel("Kredit");
        jlCapital.setBounds(160, 90, 100, 20);
        
        jsCapital = new JSlider(0,400000,100000);
        jsCapital.setBounds(270, 90, 150, 20);
        jsCapital.addChangeListener(this);
        
        jtfMonthlyRate= new JTextField("750");
        jtfMonthlyRate.setBounds(10, 115, 150, 20);
        jlMonthlyRate = new JLabel("montl. Rate");
        jlMonthlyRate.setBounds(160, 115, 100, 20);
        
        jsMonthlyRate = new JSlider(0,2500,750);
        jsMonthlyRate.setBounds(270, 115, 150, 20);
        jsMonthlyRate.addChangeListener(this);
        
        jtfNoRepayment= new JTextField("0");
        jtfNoRepayment.setBounds(10, 140, 150, 20);
        jtfNoRepayment.setForeground(Color.GRAY);
        jlNoRepayment = new JLabel("tilgungsfrei");
        jlNoRepayment.setBounds(160, 140, 100, 20);
        jlNoRepayment.setForeground(Color.GRAY);
        
        jtfDurationYears= new JTextField("20");
        jtfDurationYears.setBounds(10, 165, 150, 20);
        jlDurationYears = new JLabel("Laufzeit (Jahre)");
        jlDurationYears.setBounds(160, 165, 100, 20);
        
        jsDurationYears = new JSlider(0,50,20);
        jsDurationYears.setBounds(270, 165, 150, 20);
        jsDurationYears.addChangeListener(this);
        
        jtfDurationMounth= new JTextField("0");
        jtfDurationMounth.setBounds(10, 190, 150, 20);
        jlDurationMounth= new JLabel("Laufzeit (Monate)");
        jlDurationMounth.setBounds(160, 190, 100, 20);
        
        jtfRemainderOfADebt = new JTextField("92000");
        jtfRemainderOfADebt.setBounds(10, 215, 150, 20);
        jlRemainderOfADebt = new JLabel("Restschuld");
        jlRemainderOfADebt.setBounds(160, 215, 100, 20);
        jsRemainderOfADebt = new JSlider(0,250000,92000);
        jsRemainderOfADebt.setBounds(270, 215, 150, 20);
        jsRemainderOfADebt.addChangeListener(this);
        
        
        this.add(jtfInterestRate);
        this.add(jtfCapital);
        this.add(jtfMonthlyRate);
        this.add(jtfNoRepayment);
        this.add(jtfDurationYears);
        this.add(jtfDurationMounth);
        this.add(jtfRemainderOfADebt);
        
        this.add(jlInterestRate);
        this.add(jlCapital);
        this.add(jlMonthlyRate);
        this.add(jlNoRepayment);
        this.add(jlDurationYears);
        this.add(jlDurationMounth);
        this.add(jlRemainderOfADebt);
        
        this.add(jsInterestRate);
        this.add(jsCapital);
        this.add(jsMonthlyRate);
        this.add(jsDurationYears);
        this.add(jsRemainderOfADebt);
        
        buttonIcon = createImageIcon("../../images/exitsign.gif","exit");
        jbExit = new JButton(buttonIcon);
        jbExit.setBounds(625,610,83,100);
        this.add(jbExit);
        
        jlTest = new JLabel("<html>ACHTUNG<p/>Kein Programm ist fehlerfrei<br>" +
        		"Die Ergebnisse können eventl. ungenau sein. !!!</html>");
        jlTest.setBounds(10,220,610,300);
        
        this.add(jlTest);
        
        jbCalculate.addActionListener(this);
        jbSave.addActionListener(this);
        jbAutorun.addActionListener(this);
        jbExit.addActionListener(this);
        jbRemoveRow.addActionListener(this);
        jbSaveAs.addActionListener(this);
        
        calc = new Calculator();
        
        jtfInterestRate.setForeground(Color.BLUE);
        jtfCapital.setForeground(Color.BLUE);
        jtfMonthlyRate.setForeground(Color.BLUE);
        jtfNoRepayment.setForeground(Color.BLACK);
        jtfDurationYears.setForeground(Color.BLACK);
        jtfDurationMounth.setForeground(Color.BLACK);
        jtfRemainderOfADebt.setForeground(Color.BLUE);
        
        String[] columnNames={"Kredit",
            "Zinssatz",
            "Rate",
            "Anfangstilgung",
            "gezahlt",
            "Abtrag",
            "Zinsen",
            "Restschulden",
            "Laufzeit"};
            
        model = new DefaultTableModel();
        model.addColumn(columnNames[0]);
        model.addColumn(columnNames[1]);
        model.addColumn(columnNames[2]);
        model.addColumn(columnNames[3]);
        model.addColumn(columnNames[4]);
        model.addColumn(columnNames[5]);
        model.addColumn(columnNames[6]);
        model.addColumn(columnNames[7]);
        model.addColumn(columnNames[8]);
        
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 510, 610, 200);
        add(scrollPane);
        
        autorunProgressBar = new JProgressBar();
        autorunProgressBar.setBounds(10, 715, 695, 20);
        add(autorunProgressBar);
        autorunProgressBar.setStringPainted(true);
        
        bgAutorun       = new ButtonGroup();
        jrbZinssatz     = new JRadioButton("Zinssatz"); 
        jrbZinssatz.setBounds(460, 135, 100, 20);
        jrbZinssatz.setSelected(true);
        jrbKredit       = new JRadioButton("Kredit");
        jrbKredit.setBounds(460, 155, 100, 20);
        jrbMonatlRate   = new JRadioButton("monatl. Rate");
        jrbMonatlRate.setBounds(460, 175, 100, 20);
        jrbLaufzeit     = new JRadioButton("Laufzeit");
        jrbLaufzeit.setBounds(460, 195, 100, 20);
        jrbRestschuld   = new JRadioButton("Restschuld");
        jrbRestschuld.setBounds(460, 215, 100, 20);
        
        bgAutorun.add(jrbZinssatz);
        bgAutorun.add(jrbKredit);
        bgAutorun.add(jrbMonatlRate);
        bgAutorun.add(jrbLaufzeit);
        bgAutorun.add(jrbRestschuld);
        
        this.add(jrbZinssatz);
        this.add(jrbKredit);
        this.add(jrbMonatlRate);
        this.add(jrbLaufzeit);
        this.add(jrbRestschuld);
    }

    @Override
    public void actionPerformed(ActionEvent ev)
    {
          if (ev.getSource() == jbCalculate) 
          {
              class CalculateButton implements Runnable
              {
                  CalculateButton()
                  {
                      new Thread(this).start();
                  }
                  public void run()
                  {
                      calculate();
                  }
              }
              new CalculateButton();
              
          }
          
          else if(ev.getSource() == jbExit)
          {
              System.exit(0);
          }
          
          
          else if(ev.getSource()== jbSave)
          {
              class SaveButton implements Runnable
              {
                  SaveButton()
                  {
                      new Thread(this).start();
                  }
                  
                  public void run()
                  {
                      save(); 
                  }
              }
              new SaveButton();
              
          }
          
          else if(ev.getSource() ==jcbChoice)
          {
             choice();
          }
          
          else if(ev.getSource() == jbRemoveRow)
          {
              class RemoveRow implements Runnable
              {
                  RemoveRow()
                  {
                      new Thread(this).start();
                  }
                  
                  public void run()
                  {
                      removeRow();
                  }
              }
              new RemoveRow();
              
          }
          else if(ev.getSource() == jbAutorun)
          {
              autoRun();
          }
          else if(ev.getSource() == jbSaveAs)
          {
              class ButtonSaveAs implements Runnable
              {
                  ButtonSaveAs()
                  {
                      new Thread(this).start();
                  }
                  
                  public void run()
                  {
                      saveAs(); 
                  }
              }
              new ButtonSaveAs();
          }
          else
          {
            System.out.println("unknown");
          }      
    }
    
    @Override
    public void stateChanged(ChangeEvent av)
    {
        if (av.getSource() == jsInterestRate) 
        {
            int v = jsInterestRate.getValue();
            jtfInterestRate.setText(""+v/100.0);
            
        }
        else if(av.getSource() == jsCapital)
        {
            int c = jsCapital.getValue();
            jtfCapital.setText(""+c);
        }
        else if(av.getSource() == jsMonthlyRate)
        {
            int m = jsMonthlyRate.getValue();
            jtfMonthlyRate.setText(""+m);
        }
        else if(av.getSource() == jsDurationYears)
        {
            int d = jsDurationYears.getValue();
            jtfDurationYears.setText(""+d);
        }
        else if(av.getSource() == jsRemainderOfADebt)
        {
            int d = jsRemainderOfADebt.getValue();
            jtfRemainderOfADebt.setText(""+d);
        }
        
        calculate();
                
    }private void calculate()
    {
        if(selectedChoiceIndex == 0)
        {
            calculateDuration();
        }
        else if(selectedChoiceIndex == 1)
        {
            calcluateMonthlyRate();
        }
        else if(selectedChoiceIndex == 2)
        {
            calculateRest();
        }
        else if(selectedChoiceIndex == 3)
        {
            calculateCredit();
        }
    }
    
    private void calculateDuration()
    {
        String p_rateString = jtfInterestRate.getText();
        double interestRate = Double.parseDouble(p_rateString);
        
        String p_captial = jtfCapital.getText();
        double capital = Double.parseDouble(p_captial);
        
        String p_rate = jtfMonthlyRate.getText();
        double rate = Double.parseDouble(p_rate);
        
        String p_remainderOfADebt = jtfRemainderOfADebt.getText();
        double remainderOfADebt = Double.parseDouble(p_remainderOfADebt);
        
        String p_tilgungsfrei = jtfNoRepayment.getText();
        double tilgungsfrei = Double.parseDouble(p_tilgungsfrei);
        
        
        
        DurationResponse durationResponse;
        if(isAutoRun)
        {
            durationResponse = calc.getDuration(interestRate, capital,rate,storeRestschulden);
        }
        else
        {
            durationResponse = calc.getDuration(interestRate, capital,rate,remainderOfADebt);
        }
        double p_duration = durationResponse.getDuration();
        double remainderOfADebt2 = (-1)*durationResponse.getRestCapital();
        
        this.jtfRemainderOfADebt.setText(""+remainderOfADebt2);
        this.jtfCapital.setText(""+capital);
        jtfDurationMounth.setText(""+p_duration);
        jtfDurationYears.setText(""+p_duration/12);
        jsDurationYears.setValue((int)(p_duration/12));
        
        this.setHTMLSummary(interestRate, capital, rate, p_duration/12, remainderOfADebt,tilgungsfrei);
        
    }
    
    private void calcluateMonthlyRate()
    {
        
        String p_rateString = jtfInterestRate.getText();
        double interestRate = Double.parseDouble(p_rateString);
        
        String p_captial = jtfCapital.getText();
        double capital = Double.parseDouble(p_captial);
        
        String p_durationYears = jtfDurationYears.getText();
        double duration = Double.parseDouble(p_durationYears);
        
        String p_remainderOfADebt = jtfRemainderOfADebt.getText();
        double remainderOfADebt = Double.parseDouble(p_remainderOfADebt);
        
        String p_tilgungsfrei = jtfNoRepayment.getText();
        double tilgungsfrei = Double.parseDouble(p_tilgungsfrei);
        
        
        jtfDurationMounth.setText(""+duration*12);
        double p_mr = calc.getMounthlyRate(duration, interestRate, capital,remainderOfADebt);
        
        
        jtfMonthlyRate.setText(""+p_mr);
        jsMonthlyRate.setValue((int)p_mr);
        double p_yr = calc.getYearlyRate(duration, interestRate, capital);
        jtfNoRepayment.setText(""+p_yr);
        
        this.setHTMLSummary(interestRate, capital, p_mr, duration, remainderOfADebt, tilgungsfrei);
    }
    
    private void calculateRest()
    {
        String p_durationYears = jtfDurationYears.getText();
        double duration = Double.parseDouble(p_durationYears);
        
        String p_rateString = jtfInterestRate.getText();
        double interestRate = Double.parseDouble(p_rateString);
        
        String p_captial = jtfCapital.getText();
        double capital = Double.parseDouble(p_captial);
        
        String p_rate = jtfMonthlyRate.getText();
        double rate = Double.parseDouble(p_rate);
        
        String p_tilgungsfrei = jtfNoRepayment.getText();
        double tilgungsfrei = Double.parseDouble(p_tilgungsfrei);
        
        double p_yr = calc.getYearlyRate(duration, interestRate, capital);
        jtfNoRepayment.setText(""+p_yr);
        
        jtfDurationMounth.setText(""+duration*12);
        
        double ci = calc.getCompoundedInterest(duration, interestRate, capital*(-1), rate);
        
        jtfRemainderOfADebt.setText(""+ci*(-1));
        this.setHTMLSummary(interestRate, capital, rate, duration, ci*(-1),tilgungsfrei);
    }
    
    private void calculateCredit()
    {
        String p_durationYears = jtfDurationYears.getText();
        double duration = Double.parseDouble(p_durationYears);
        
        String p_rateString = jtfInterestRate.getText();
        double interestRate = Double.parseDouble(p_rateString);
        
        String p_rate = jtfMonthlyRate.getText();
        double rate = Double.parseDouble(p_rate);
        
        String p_remainderOfADebt = jtfRemainderOfADebt.getText();
        double remainderOfADebt = Double.parseDouble(p_remainderOfADebt);
        
        String p_tilgungsfrei = jtfNoRepayment.getText();
        double tilgungsfrei = Double.parseDouble(p_tilgungsfrei);
        
        
        double capital = calc.getCapital(duration, interestRate, rate,remainderOfADebt);
        jtfCapital.setText(""+capital);
        this.jtfDurationMounth.setText(""+12*duration);
        calc.getYearlyRate(duration, interestRate, capital);
        this.setHTMLSummary(interestRate, capital, rate, duration, remainderOfADebt,tilgungsfrei);
    }
    
    private double roundScale2( double d )
    {
      return Math.rint( d * 100 ) / 100.;
    }

    
    private void choice()
    {
        selectedChoiceIndex = jcbChoice.getSelectedIndex();
        if(selectedChoiceIndex == 0)
        {
            jtfInterestRate.setForeground(Color.BLUE);
            jtfCapital.setForeground(Color.BLUE);
            jtfMonthlyRate.setForeground(Color.BLUE);
            jtfNoRepayment.setForeground(Color.BLACK);
            jtfDurationYears.setForeground(Color.BLACK);
            jtfDurationMounth.setForeground(Color.BLACK);
            jtfRemainderOfADebt.setForeground(Color.BLUE);
        }
        else if(selectedChoiceIndex == 1)
        {
            jtfInterestRate.setForeground(Color.BLUE);
            jtfCapital.setForeground(Color.BLUE);
            jtfMonthlyRate.setForeground(Color.BLACK);
            jtfNoRepayment.setForeground(Color.BLACK);
            jtfDurationYears.setForeground(Color.BLUE);
            jtfDurationMounth.setForeground(Color.BLACK);
            jtfRemainderOfADebt.setForeground(Color.BLUE);
        }
        else if(selectedChoiceIndex == 2)
        {
            jtfInterestRate.setForeground(Color.BLUE);
            jtfCapital.setForeground(Color.BLUE);
            jtfMonthlyRate.setForeground(Color.BLUE);
            jtfNoRepayment.setForeground(Color.BLACK);
            jtfDurationYears.setForeground(Color.BLUE);
            jtfDurationMounth.setForeground(Color.BLACK);
            jtfRemainderOfADebt.setForeground(Color.BLACK);
        }
        else if(selectedChoiceIndex == 3)
        {
            jtfInterestRate.setForeground(Color.BLUE);
            jtfCapital.setForeground(Color.BLACK);
            jtfMonthlyRate.setForeground(Color.BLUE);
            jtfNoRepayment.setForeground(Color.BLACK);
            jtfDurationYears.setForeground(Color.BLUE);
            jtfDurationMounth.setForeground(Color.BLACK);
            jtfRemainderOfADebt.setForeground(Color.BLUE);
        }
        else if(selectedChoiceIndex == 4)
        {
            jtfInterestRate.setForeground(Color.GRAY);
            jtfCapital.setForeground(Color.GRAY);
            jtfMonthlyRate.setForeground(Color.GRAY);
            jtfNoRepayment.setForeground(Color.GRAY);
            jtfDurationYears.setForeground(Color.GRAY);
            jtfDurationMounth.setForeground(Color.GRAY);
            jtfRemainderOfADebt.setForeground(Color.GRAY);
        }
        
    }
    
    private void save()
    {
        savedUsedKredit         = roundScale2(lastUsedKredit);
        savedUsedZinssatz       = roundScale2(lastUsedZinssatz);
        savedUsedAnfangstilgung = roundScale2(lastUsedAnfangstilgung);
        savedUsedmonatRate      = roundScale2(lastUsedmonatRate);
        savedUsedGezahlt        = roundScale2(lastUsedGezahlt);
        savedUsedAbtrag         = roundScale2(lastUsedAbtrag);
        savedUsedZinsen         = roundScale2(lastUsedZinsen);
        savedUsedrestschuld     = roundScale2(lastUsedrestschuld);
        savedUsedlaufzeitJahre  = roundScale2(lastUsedlaufzeitJahre);
        
        Object data[]={savedUsedKredit,
            savedUsedZinssatz,
            savedUsedmonatRate,
            savedUsedAnfangstilgung,
            savedUsedGezahlt,
            savedUsedAbtrag,
            savedUsedZinsen,
            savedUsedrestschuld,
            savedUsedlaufzeitJahre};
        
        model.addRow(data);
        this.calculate();
    }
    
    private void removeRow()
    {
        int[] selectedRows = table.getSelectedRows();
        int le = selectedRows.length;
        int min = 0;
        int max = le;
        autorunProgressBar.setMinimum(min);
        autorunProgressBar.setMaximum(max);
        for(int i=le;i>0;i--)
        {
            model.removeRow(selectedRows[i-1]);
            autorunProgressBar.setValue(le-i);
        }
        autorunProgressBar.setValue(le);
    }
    
    private void autoRun()
    {
        new Thread(this).start();
    }
    
    private void saveAs()
    {
        String saveAsString = "";
        int cc = table.getColumnCount();
        int rc = table.getRowCount();
        
        int min = 0;
        int max = rc;
        autorunProgressBar.setMinimum(min);
        autorunProgressBar.setMaximum(max);

        
        for(int j =0;j< cc;j++)
        {    
            //saveAsString += table.getColumnName(j)+"\t";
            saveAsString += table.getColumnName(j)+";";
        }
        saveAsString +="\n";
        for(int i = 0;i< rc;i++)
        {    
            autorunProgressBar.setValue(i);
            for(int j =0;j< cc;j++)
            {    
                //saveAsString += table.getValueAt(i, j)+"\t";
                saveAsString += table.getValueAt(i, j)+";";
            }
            saveAsString +="\n";
        }
        autorunProgressBar.setValue(rc);
        System.out.println(saveAsString);
        JFileChooser fileChooser = new JFileChooser();
        System.out.println("#1");
        fileChooser.showSaveDialog(this);
        System.out.println("#2");
        
        File saveAsFile = fileChooser.getSelectedFile(); 
        System.out.println("#3");
        
        try
        {
            FileWriter fw = new FileWriter(saveAsFile);
            fw.write(saveAsString);
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void setHTMLSummary(double zinssatz, double kredit, double monatRate, double laufzeitJahre, double restschuld, double tilgungsfrei)
    {
        restschuld = roundScale2(restschuld);
        double anfangstilgung = roundScale2(calc.getTilgungsRate(kredit, zinssatz, monatRate));
        double abtrag = kredit-restschuld;
        double gezahlt = roundScale2(12*laufzeitJahre*monatRate);
        double zinsen  = roundScale2(12*laufzeitJahre*monatRate-abtrag);
        abtrag = roundScale2(abtrag);
        jlTest.setText("<html>"
            +"<table width=600 border=2>"
            +"<tr>"
            +"<th>Zusammenfassung</th>"
            +"<th width=150>Ergebnis</th>"
            +"<th width=150>gespeichert </th>"
            +"<th width=150>Differenz </th>"
            +"</tr>"
            +"<tr>"
            +"<td>Kredit</td>"
            +"<td align=center bgcolor=00FF00>"+roundScale2(kredit)+" €</td>"
            +"<td align=center bgcolor=FFFFFF>"+roundScale2(savedUsedKredit)+" €</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(kredit-savedUsedKredit)+" €</td>"
            +"</tr>"
            +"<tr>"
            +"<td>Zinssatz:</td>"
            +"<td align=center bgcolor=00FF00>"+roundScale2(zinssatz)+" %</td>"
            +"<td align=center bgcolor=FFFFFF>"+roundScale2(savedUsedZinssatz)+" %</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(zinssatz-savedUsedZinssatz)+" %</td>"
            +"</tr>"
            +"<tr>"
            +"<td>monatl. Rate</td>"
            +"<td align=center bgcolor=00FF00>"+roundScale2(monatRate)+" €</td>"
            +"<td align=center bgcolor=FFFFFF>"+roundScale2(savedUsedmonatRate)+" €</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(monatRate-savedUsedmonatRate)+" €</td>"
            +"</tr>"
            +"<tr>"
            +"<td>Anfangstilgung:</td>"
            +"<td align=center bgcolor=00FF00>"+roundScale2(anfangstilgung)+" %</td>"
            +"<td align=center bgcolor=FFFFFF>"+roundScale2(savedUsedAnfangstilgung)+" %</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(anfangstilgung-savedUsedAnfangstilgung)+" %</td>"
            +"</tr>"
            +"<tr>"
            +"<td>gezahlt:</td>"
            +"<td align=center bgcolor=00FF00>"+roundScale2(gezahlt)+" €</td>"
            +"<td align=center bgcolor=FFFFFF>"+roundScale2(savedUsedGezahlt)+" €</td>"
            +"<td align=center bgcolor=AAAAFF>" +roundScale2(gezahlt-savedUsedGezahlt)+" €</td>"
            +"</tr>"
            +"<tr>"
            +"<td>Abtrag: </td>"
            +"<td align=center bgcolor=00FF00>"+abtrag+" €</td>"
            +"<td align=center bgcolor=FFFFFF>"+savedUsedAbtrag+" €</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(abtrag-savedUsedAbtrag)+" €</td>"
            +"</tr>"
            +"<tr>"
            +"<td>gezahlte Zinsen</td>"
            +"<td align=center bgcolor=00FF00>"+zinsen+" €</td>"
            +"<td align=center bgcolor=FFFFFF>"+savedUsedZinsen+" €</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(zinsen-savedUsedZinsen)+" €</td>"
            +"</tr>"
            +"<tr>"
            +"<td>Restschulden</td>"
            +"<td align=center bgcolor=00FF00>"+restschuld+" €</td>"
            +"<td align=center bgcolor=FFFFFF>"+savedUsedrestschuld+" €</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(restschuld-savedUsedrestschuld)+" €</td>"
            +"</tr>"
            +"<td>Laufzeit</td>"
            +"<td align=center bgcolor=00FF00>"+roundScale2(laufzeitJahre)+" Jahre</td>"
            +"<td align=center bgcolor=FFFFFF>"+roundScale2(savedUsedlaufzeitJahre)+" Jahre</td>"
            +"<td align=center bgcolor=AAAAFF>"+roundScale2(laufzeitJahre-savedUsedlaufzeitJahre)+" Jahre</td>"
            +"</tr>"
            +"</table>" 
            
            +"</html>");
            lastUsedKredit          = kredit;
            lastUsedZinssatz        = zinssatz;
            lastUsedmonatRate       = monatRate;
            lastUsedAnfangstilgung  = anfangstilgung;
            lastUsedGezahlt         = gezahlt;
            lastUsedAbtrag          = abtrag;
            lastUsedZinsen          = zinsen;
            lastUsedrestschuld      = restschuld;
            lastUsedlaufzeitJahre   =laufzeitJahre;
     }
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public void run()
    {
        isAutoRun = true;
        
        String p_remainderOfADebt = jtfRemainderOfADebt.getText();
        double remainderOfADebt = Double.parseDouble(p_remainderOfADebt);
        System.out.println("#1 storeRestschulden: "+storeRestschulden+ " remainderOfADebt: "+remainderOfADebt);
        storeRestschulden = remainderOfADebt;
        
        double startValue = Double.parseDouble(jtfStartValue.getText());
        double steps = Double.parseDouble(jtfSteps.getText());
        double endValue = Double.parseDouble(jtfEndValue.getText());
        
        int min = (int)(startValue*100);
        int max = (int)(endValue*100);
        autorunProgressBar.setMinimum(min);
        autorunProgressBar.setMaximum(max);
        
        double current = startValue;
        
        if(steps >0)
        {
            do
            {
                int va =(int)(current*100);
                autorunProgressBar.setValue(va);
                if(jrbZinssatz.isSelected())
                {    
                    jtfInterestRate.setText(""+current);
                }
                else if(jrbKredit.isSelected())
                {
                    jtfCapital.setText(""+current);
                }
                else if(jrbMonatlRate.isSelected())
                {
                    jtfMonthlyRate.setText(""+current);
                }
                else if(jrbLaufzeit.isSelected())
                {
                    jtfDurationYears.setText(""+current);
                }
                else if(jrbRestschuld.isSelected())
                {
                    this.jtfRemainderOfADebt.setText(""+current);
                }
                else
                {
                    break;
                }
                autorunProgressBar.repaint();
                this.calculate();
                this.save();
                current += steps;
            }
            while(roundScale2(current)<=roundScale2(endValue));
        }    
        isAutoRun = false;
    }
}
