/*
 * (C) 2004 Siemens VDO
 * All Rights Reserved
 * FILE: 		DurationResponse.java
 * VERSION 0.1
 * 
 * MODIFIED by 	Jochen Weiss
 * MODIFIED 	06.09.2010
 * AUTHOR 		Jochen Weiss
 * CREATED		06.09.2010
*/
package jogi.finanzen;

public class DurationResponse
{
    int duration;
    double restCapital;
    
    /**
     * 
     * @param duration duration in month
     * @param restCapital 
     * @param restCapital left over capital
     */
    public DurationResponse(int duration, double restCapital)
    {
        this.duration       = duration;
        this.restCapital    = restCapital;
    }

    /**
     * 
     * @return duration duration in month
     */
    public int getDuration()
    {
        return duration;
    }
    
    /**
     * 
     * @return  restCapital left over capital
     */
    public double getRestCapital()
    {
        return restCapital;
    }
}
