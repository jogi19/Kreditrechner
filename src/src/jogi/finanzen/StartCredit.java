/*
 * (C) by me
 * All Rights Reserved
 * FILE: 		StartCredit.java
 * VERSION 0.1
 * 
 * MODIFIED by 	Jochen Weiss
 * MODIFIED 	16.08.2010
 * AUTHOR 		Jochen Weiss
 * CREATED		16.08.2010
*/
package jogi.finanzen;


public class StartCredit
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
       new StartCredit();
    }
     private StartCredit()
     {
         FinanceGui fgui = new FinanceGui();  
         fgui.setVisible(true);
     }
}
