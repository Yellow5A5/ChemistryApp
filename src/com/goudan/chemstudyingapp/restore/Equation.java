package com.goudan.chemstudyingapp.restore;

public class Equation {
	private String material = "";
	private String equipment = "";
	private String condition = "";
	private String result = "";
	private String phenomenon="";
	private String url="";
	private int resultImage = 0;
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getResultImage() {
		return resultImage;
	}
	public void setResultImage(int resultImage) {
		this.resultImage = resultImage;
	}
	public String getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(String phenomenon) {
		this.phenomenon = phenomenon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
