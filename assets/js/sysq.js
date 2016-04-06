/**
 * 页面渲染
 * @param tpl
 * @param data
 */
function renderContent(tpl,data){
	var render = template.compile(tpl);
	appservice.debug("compile finished");
	appservice.debug(data);
	data = JSON.parse(data);
	appservice.debug("parse finished");
	var html = render(data);
	appservice.debug("render finished");
	appservice.debug(html);
	$("#content").html(html);
}

/**
 * 页面初始化
 * @param entryLogic
 */
function entry(entryLogic){
	eval(entryLogic);
}


/**
 * 滑动块拖动触发函数
 * @param dom
 */
function onsliderchange(dom){
	$(dom).parent().find('div.slider-value').html(dom.value);
}

var answers = {};

/**
 * 获取本题答案
 */
function getLocalAnswerValue(){
	
	var localAnswers = {};
	
	$("div.answer").each(function(){
		var $answer = $(this);
		
		//跳过隐藏答案
		if($answer.hasClass("nodisplay")){
			return;
		}
		
		//获取答案通用属性
		var code = $answer.data("code");
		var label = $answer.data("label");
		var type = $answer.data("type");
		
		//获取值与文本
		var value,text;
		if(type == "radiogroup"){	//单选 
			
			var $selectedRadio = $answer.find("input[type='radio']:checked");
			value = $selectedRadio.val();
			text = $selectedRadio.next().text();
			
		}else if(type == "slider"){//滑动块
			
			var $range = $answer.find("input[type='range']");
			value = $range.val();
			text = value;
			
		}else if(type == "dropdownlist"){//下拉框
			
			var $select = $answer.find("select");
			value = $select.val();
			text = $select.find("option:checked").text();
			
		}else if(type == "text"){//文本域
			
			var $textarea = $answer.find("textarea");
			value = $textarea.val();
			text = value;
			
		}else if(type == "calendar"){//日历
			
			var $date = $answer.find("input[type='date']");
			value = $date.val();
			text = value;
		}
		
		localAnswers[code] = {"code":code,"label":label,"value":value,"text":text};
	});
	
	return localAnswers;
}

/**
 * 获取答案值
 * @param answerCode
 */
function getAnswerValue(answerCode){
	
	var answerValue;
	
	var localAnswers = getLocalAnswerValue();
	if(localAnswers[answerCode]){//本题检测
		answerValue = localAnswers[answerCode];
	}else{//已做过题目检测
		answerValue = answers[answerCode];
	}
	
	return answerValue["value"];
}


/**
 * 暂存答案
 */
function saveToAnswers(){
	
	var localAnswers = getLocalAnswerValue();
	$.extend(answers,localAnswers);
	
	appservice.debug("answers = " + JSON.stringify(answers));
}

/**
 * 开始访谈
 */
function startInterview(){
	appservice.jumpToFirstQuestion();
}


/**
 * 跳转下一题
 */
function jumpToNextQuestion(){
	saveToAnswers();
	appservice.jumpToNextQuestion();
}

/**
 * 跳转答案列表
 */
function jumpToAnswerList(){
	saveToAnswers();
	appservice.jumpToAnswerList(JSON.stringify(answers));
}

/**
 * 中途跳转答案列表
 */
function jumpToPartialAnswerList(){
	appservice.jumpToPartialAnswerList(JSON.stringify(answers));
}

/**
 * 保存问卷
 */
function saveQuestionaire(){
	appservice.saveQuestionaire(JSON.stringify(answers));
	answers = {};//清空答案
}

/**
 * 重做问卷
 */
function redoQuestionaire(){
	answers = {};//清空答案
	appservice.jumpToFirstQuestion();
}

/**
 * 退出访谈
 */
function quitInterview(){
	appservice.quitInterview();
}

/**
 * 跳转上一题
 */
function jumpToPreviousQuestion(){
	appservice.jumpToPreviousQuestion();
}

/**
 * 暂停访谈
 */
function pauseInterview(){
	appservice.pauseInterview(JSON.stringify(answers));
}

/**
 * 弹框提示
 * @param msg
 */
function showMsg(msg){
	appservice.showMsg(msg);
}

/**
 * 跳转指定问题
 * @param questionCode
 */
function jumpToSpecQuestion(questionCode){
	saveToAnswers();
	appservice.jumpToSpecQuestion(questionCode);
}

/**
 * 显示
 * @param answerCode
 */
function showAnswer(answerCode){
	var $answer = $("div.answer[data-code='"+answerCode+"']");
	if($answer.hasClass("nodisplay")){
		$answer.removeClass("nodisplay");
	}
}

/**
 * 隐藏
 * @param answerCode
 */
function hideAnswer(answerCode){
	var $answer = $("div.answer[data-code='"+answerCode+"']");
	if(!$answer.hasClass("nodisplay")){
		$answer.addClass("nodisplay");
	}
}

/**
 * 继续访谈（中途跳转答案列表）
 */
function resumeQuestionaire(){
	appservice.resumeQuestionaire();
}

/**
 * 跳转结束问题
 * @param endQuestionCode
 */
function jumpToEnd(endQuestionCode){
	appservice.jumpToEnd(endQuestionCode);
}

/**
 * 结束访谈（保存答案）
 */
function quitInterviewAndSave(){
	appservice.quitInterviewAndSave(JSON.stringify(answers));
}