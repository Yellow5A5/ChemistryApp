package com.goudan.chemstudyingapp.restore;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

public class RestoreThing {
	//定义集合存储每次新建的ImageView
	public static List<String> materialList=new ArrayList<String>();
	public static List<String> equipmentList=new ArrayList<String>();
	public static List<View> material=new ArrayList<View>();
	public static List<View> equipment=new ArrayList<View>();
	
	public static void addMateria(String material)
	{
		materialList.add(material);
	}
	public static void removeMateria(String material)
	{
		materialList.remove(material);
	}
	public static void addEquipment(String equipment)
	{
		equipmentList.add(equipment);
	}
	public static void removeEquipment(String equipment)
	{
		equipmentList.remove(equipment);
	}
	public static void addEquipmentImage(View iamge)
	{
		equipment.add(iamge);
	}
	public static void removeEquipmentImage(View iamge)
	{
		equipment.remove(iamge);
	}
	public static void addMaterialImage(View iamge)
	{
		material.add(iamge);
	}
	public static void removeMaterialImage(View iamge)
	{
		material.remove(iamge);
	}
}
