package com.huasheng.sysq.model;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AnswerWrap {

	private Answer answer;
	
	private List<Map<String,String>> radioOptions;
	
	private Map<String,String> sliderOption;
	
	private List<Map<String,String>> dropDownListOptions;
	
	private List<Map<String,String>> checkboxOptions;
	
	public AnswerWrap(Answer answer){
		
		this.answer = answer;
		
		if(answer.getType().equals(Answer.TYPE_RADIOGROUP)){
			Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
			Gson gson = new Gson();
			this.radioOptions = gson.fromJson(answer.getExtra(), type);
		}else if(answer.getType().equals(Answer.TYPE_SLIDER)){
			Type type = new TypeToken<Map<String,String>>(){}.getType();
			Gson gson = new Gson();
			this.sliderOption = gson.fromJson(answer.getExtra(), type);	
		}else if(answer.getType().equals(Answer.TYPE_DROPDOWNLIST)){
			Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
			Gson gson = new Gson();
			this.dropDownListOptions = gson.fromJson(answer.getExtra(), type);
		}else if(answer.getType().equals(Answer.TYPE_CHECKBOX)){
			Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
			Gson gson = new Gson();
			this.checkboxOptions = gson.fromJson(answer.getExtra(), type);
		}
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public List<Map<String, String>> getRadioOptions() {
		return radioOptions;
	}

	public void setRadioOptions(List<Map<String, String>> radioOptions) {
		this.radioOptions = radioOptions;
	}

	public Map<String, String> getSliderOption() {
		return sliderOption;
	}

	public void setSliderOption(Map<String, String> sliderOption) {
		this.sliderOption = sliderOption;
	}

	public List<Map<String, String>> getDropDownListOptions() {
		return dropDownListOptions;
	}

	public void setDropDownListOptions(List<Map<String, String>> dropDownListOptions) {
		this.dropDownListOptions = dropDownListOptions;
	}

	public List<Map<String, String>> getCheckboxOptions() {
		return checkboxOptions;
	}

	public void setCheckboxOptions(List<Map<String, String>> checkboxOptions) {
		this.checkboxOptions = checkboxOptions;
	}
}
