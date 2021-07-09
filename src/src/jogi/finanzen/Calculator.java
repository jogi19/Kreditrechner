/*
 * (C) 2004 Siemens VDO
 * All Rights Reserved
 * FILE: 		Calculator.java
 * VERSION 0.1
 * 
 * MODIFIED by 	Jochen Weiss
 * MODIFIED 	17.08.2010
 * AUTHOR 		Jochen Weiss
 * CREATED		17.08.2010
*/
package jogi.finanzen;

import java.lang.Math;



public class Calculator
{
    /**
     * 
     * @param duration in years
     * @param interestRate in percent e.G. 5 % per year
     * @param capital at start time. If it is a credit, use a negative number
     * @return capital after duration
     */
    public double getCompoundedInterest(int duration, double interestRate, double capital)
    {
        for(int i=1;i<=duration;i++)
        {
            capital = capital*(1+(interestRate/100));
        }
        return capital;
    }
    
    /**
     * 
     * @param duration in years
     * @param interestRate in percent e.G. 5 % per year
     * @param capital at start time. If it is a credit, use a negative number
     * @param monthly rate
     * @return capital after duration
     */
    public double getCompoundedInterest(double duration, double interestRate, double capital,double rate)
    {
        for(int i=1;i<=duration;i++)
        {
            for(int j = 0;j<12;j++)
            {    
                capital = capital*(1+(interestRate/1200))+rate;
            }    
        }
        return capital;
    }
    
    /**
     * 
     * @param duration in years
     * @param interestRate in percent e.G. 5 % per year
     * @param credit at start time
     * @return returns count of month until the credit is payed off, 
     * returns -1 if the rate is to low to ever pay it off
     *  
     */
    public DurationResponse getDuration(double interestRate, double capital,double rate)
    {
        capital = capital *(-1);
        //check if rate is high enough
        if ((rate*12<(interestRate/100)*capital*(-1)))
        {
            System.out.println("Dauert ewig");
            return new DurationResponse(-12,capital);
        }
        int month = 0;
        
        while(capital <=0)
        {    
            for(int j = 0;j<12;j++)
            {    
                month++;
                capital = capital*(1+(interestRate/1200))+rate;
                //System.out.println("month: "+(j+1)+ " capital: "+capital);
                if(capital >=0)
                {
                    return new DurationResponse(month,capital);
                }
            }    
        }    
        return new DurationResponse(month,capital);
    }
    /**
     * 
     * @param duration in years
     * @param interestRate in percent e.G. 5 % per year
     * @param credit at start time
     * @param remainderOfADebt
     * @return returns count of month until the credit is lower than remainder of a debt, 
     * returns -1 if the rate is to low to ever pay it off
     *  
     */
    public DurationResponse getDuration(double interestRate, double capital,double rate,double remainderOfADebt)
    {
        remainderOfADebt = remainderOfADebt*(-1);
        capital = capital *(-1);
        //check if rate is high enough
        if (((rate*12-0.1)<=(interestRate/100)*capital*(-1))) // (rate*12-0.1: -0.1 um einen Hang durch Rundungsfehler zu vermeiden
        {
            System.out.println("Dauert ewig");
            return new DurationResponse(-12,capital);
        }
        int month = 0;
        
        while(capital <=remainderOfADebt)
        {    
            for(int j = 0;j<12;j++)
            {    
                month++;
                capital = capital*(1+(interestRate/1200))+rate;
                if(capital >=remainderOfADebt)
                {
                    return new DurationResponse(month,capital);
                }
            }    
        }    
        return new DurationResponse(month,capital);
    }
    /**
     * 
     * @param duration in years
     * @param interestRate
     * @param startCapital
     * @return yearly rate if you pay off at the end of the year
     */
    public double getYearlyRate(double duration, double interestRate, double startCapital)
    {
       //TODO muss auf Restkapital geändert werden
        /*
       double sc = startCapital;
       double ir = interestRate;
       double   d  = duration;
       double i = ir/100;
       double q = i+1;
       double yearly = sc* ((i * Math.pow(q, d))/(Math.pow(q, d)-1));
       return yearly;
       */
       
       //System.out.println("Muss noch auf restcapital geändert werden");
       return 0;
    }
    
    /**
     * 
     * @param duration in years
     * @param interestRate
     * @param startCredit
     * @return
     */
    public double getMounthlyRate(double duration, double interestRate, double startCredit)
    {
       
       double sc = startCredit;
       double ir = interestRate;
       double   d  = duration;
       //System.out.println("sc:"+ sc+" ir: "+ir+" d: "+d);
       double i = ir/100;
       double q = i+1;
       double yearly = sc* ((i * Math.pow(q, d))/(Math.pow(q, d)-1));
       double monthly = yearly/(12+((i/2)*10));
       //System.out.println("monthly: "+monthly);
       return monthly;
    }
    
    /**
     * 
     * @param duration in years
     * @param interestRate
     * @param startCredit
     * @param remainderOfADebt
     * @return monthly rate
     */
    public double getMounthlyRate(double duration, double interestRate, double startCredit, double remainderOfADebt)
    {
        //TODO fix problem wenn restbetrag groesser als anfangsbetrag ist.
        startCredit         = startCredit*(-1);
        remainderOfADebt    = remainderOfADebt*(-1);
        double tmpMonthlyRate = 0.0;
        double tmpCaptital = startCredit;
        while(tmpCaptital <= remainderOfADebt)
        {
            tmpCaptital = this.getCompoundedInterest(duration, interestRate, startCredit, tmpMonthlyRate);
            tmpMonthlyRate += 0.01;
        }
        /*
        if(startCredit>=remainderOfADebt)
        {    
            while(tmpCaptital <= remainderOfADebt)
            {
                tmpCaptital = this.getCompoundedInterest(duration, interestRate, startCredit, tmpMonthlyRate);
                tmpMonthlyRate += 0.01;
            }
        } 
        */   
        System.out.println("startCredit: "+startCredit + " remainderOfADebt: "+remainderOfADebt);
        startCredit         = startCredit*(-1);
        remainderOfADebt    = remainderOfADebt*(-1);
        tmpCaptital = startCredit;
        if(startCredit<remainderOfADebt)
        {
            tmpMonthlyRate = tmpMonthlyRate-0.01;
            while(tmpCaptital <= remainderOfADebt)
                tmpCaptital = this.getCompoundedInterest(duration, interestRate, startCredit, tmpMonthlyRate);
            tmpMonthlyRate -= 0.01;    
            System.out.println("startCredit<remainderOfADebt");
        }
       
        return tmpMonthlyRate;
    }
    
    
    /*
    public double getCapital(double duration, double interestRate, double monthlyRate)
    {
        double d = duration;
        double ir = interestRate/100;
        double mr = monthlyRate;
        double q = 1 + ir;
        double capital = mr*(((Math.pow(q, d)-1)/(ir*(Math.pow(q, d))))*(12+(ir/2)*11));
        return capital;
    }
    */
    public double getCapital(double duration, double interestRate, double monthlyRate,double remainderOfADebt)
    {
        double d = duration;
        double mr = monthlyRate;
        double capital=0;
        double tmp_capital = 0;
        capital =this.getCompoundedInterest(d, interestRate, tmp_capital*(-1), mr);
        while(remainderOfADebt*(-1)<capital)
        {    
            capital =this.getCompoundedInterest(d, interestRate, tmp_capital*(-1), mr);
            tmp_capital++;
        }
        return tmp_capital;
    }
    
    
    public double getTilgungsRate(double capital, double interestRate,double monthlyRate)
    {
        //TODO
        //double kapitalAfterOneYear = this.getCapital(1, interestRate, monthlyRate, remainderOfADebt)
        double kapitalAfterOneYear = this.getCompoundedInterest(1, interestRate, capital*(-1), monthlyRate);
        double tilgungJahrEins = capital - kapitalAfterOneYear*(-1);
        double tilgungsrate = tilgungJahrEins/capital*100;
        return tilgungsrate;
    }
}
