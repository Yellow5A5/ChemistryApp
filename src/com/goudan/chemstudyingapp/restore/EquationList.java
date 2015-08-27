package com.goudan.chemstudyingapp.restore;

import java.util.ArrayList;
import java.util.List;

import com.example.chemstudyingapp.R;

public class EquationList {
	public static List<Equation> equationList = new ArrayList<Equation>();
	public static String[] material = {"NaOH溶液，酚酞溶液,稀盐酸"};
	public static String[] equipment = {"烧杯，胶头滴管"};
	public static String[] condition = {""};
	public static String[] result = {""};
	public static String[] phenomenon = {""};
	public static String[] url =  {""};
	public static int[] resultImage = {R.drawable.test1};
	
//	public static void init()
//	{
//		for(int i=0;i<material.length;i++)
//		{
//			Equation equation=new Equation();
//			equation.setMaterial(material[i]);
////			equation.setEquipment(equipment[i]);
//			equation.setCondition(condition[i]);
//			equation.setResult(result[i]);
//			equation.setPhenomenon(phenomenon[i]);
////			equation.setResultImage(resultImage[i]);
//			equationList.add(equation);
//		}
//		
//	}
}
