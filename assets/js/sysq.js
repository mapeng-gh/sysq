var isReplay = false;

/**
 * 页面渲染
 * @param tpl
 * @param data
 */
function renderContent(tpl,data){
	
	appservice.debug("renderContent",tpl);
	var render = template.compile(tpl);
	appservice.debug("renderContent","template compile success");
	
	appservice.debug("renderContent",data);
	data = JSON.parse(data);
	appservice.debug("renderContent","data parse success");
	
	var html = render(data);
	appservice.debug("renderContent","template render success");
	appservice.debug("renderContent",html);
	
	$("#content").html(html);
}

/**
 * 滑动块拖动触发函数
 * @param dom
 */
function onsliderchange(dom){
	$(dom).parent().find('div.slider-value').html(dom.value);
}

var answers = [];

/**
 * 获取本题答案
 */
function getLocalAnswerValue(){
	
	var localAnswers = [];
	
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
		var seqNum = $answer.data("seq-num");
		var questionCode = $answer.data("question-code");
		
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
		
		localAnswers.push({"code":code,"label":label,"value":value,"text":text,"seqNum":seqNum,"questionCode":questionCode});
	});
	
	return localAnswers;
}

/**
 * 获取答案值
 * @param answerCode
 */
function getAnswerValue(answerCode){
	return getAnswer(answerCode)["value"];
}

/**
 * 获取答案文本
 * @param answerCode
 */
function getAnswerText(answerCode){
	return getAnswer(answerCode)["text"];
}

function getAnswer(answerCode){
	
	//从本题获取
	var localAnswers = getLocalAnswerValue();
	for(var i=0;i<localAnswers.length;i++){
		if(localAnswers[i].code == answerCode){
			return localAnswers[i]; 
		}
	}
	
	//从已做题目获取
	for(i=0;i<answers.length;i++){
		if(answers[i].code == answerCode){
			return answers[i];
		}
	}
	
	//从其他问卷获取
	var answerDB = JSON.parse(appservice.getInterviewAnswer(answerCode));
	return answerDB;
}


/**
 * 暂存答案
 */
function saveToAnswers(){
	
	//获取本题答案
	var localAnswers = getLocalAnswerValue();
	
	//如果已经存在答案，则更新
	for(var i=0;i<localAnswers.length;i++){
		var isExist = false;
		for(var j=0;j<answers.length;j++){
			if(localAnswers[i].code == answers[j].code){
				isExist = true;
				break;
			}
		}
		if(isExist == true){
			answers[j] = localAnswers[i];
		}else{
			answers.push(localAnswers[i]);
		}
	}
	
	appservice.debug("saveToAnswers","answers = " + JSON.stringify(answers));
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
	if(isReplay){
		return;
	}
	
	saveToAnswers();
	appservice.jumpToNextQuestion();
}

/**
 * 跳转答案列表
 */
function jumpToAnswerList(){
	if(isReplay){
		return;
	}
	
	saveToAnswers();
	appservice.jumpToAnswerList(JSON.stringify(answers),"all");
}

/**
 * 中途跳转答案列表
 */
function jumpToPartialAnswerList(){
	appservice.jumpToAnswerList(JSON.stringify(answers),"part");
}

/**
 * 保存问卷
 */
function saveQuestionaire(){
	appservice.saveAnswers(JSON.stringify(answers));
	answers = [];//清空答案
}

/**
 * 重做问卷
 */
function redoQuestionaire(){
	answers = [];//清空答案
	appservice.redoQuestionaire();
}

/**
 * 退出访谈
 */
function quitInterview(){
	
	var answersJS = answers.length == 0 ? "" : JSON.stringify(answers);
	appservice.quitInterview(answersJS);
	
}

/**
 * 跳转上一题
 */
function jumpToPreviousQuestion(){
	
	//如果是第一题，直接跳出
	if(answers.length == 0){
		appservice.showMsg("已经是第一题");
		return;
	}
	
	//删除上一题的所有答案（防止污染局部答案列表）
	var lastQuestionCode = answers[answers.length-1]["questionCode"];
	for(var i=answers.length-1;i>=0;i--){
		if(answers[i]["questionCode"] == lastQuestionCode){
			answers.pop();
		}else{
			break;
		}
	}
	
	appservice.jumpToPreviousQuestion();
}

/**
 * 暂停访谈
 */
function pauseInterview(){
	
	var answersJS = answers.length == 0 ? "" : JSON.stringify(answers);
	appservice.pauseInterview(answersJS);
	
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
	if(isReplay){
		return;
	}
	
	saveToAnswers();
	appservice.jumpToSpecQuestion(questionCode);
}

/**
 * 编辑问题（答案列表跳转）
 * @param questionCode
 */
function editQuestion(questionCode){
	
	//清空该问题以及之后问题的所有答案
	for(var i=0;i<answers.length;i++){
		if(answers[i]["questionCode"] == questionCode){
			break;
		}
	}
	answers = answers.slice(0,i);
	appservice.debug("editQuestion",JSON.stringify(answers));
	
	appservice.jumpToSpecQuestion(questionCode);
}

/**
 * 显示
 * @param answerCode
 */
function showAnswer(answerCode){
	if(isReplay){
		return;
	}
	
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
	if(isReplay){
		return;
	}
	
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
	appservice.jumpToEndQuestion(endQuestionCode);
}

/**
 * 计算年龄
 * @param dateStr  1988-07-03
 */
function calculateAge(dateStr){
	var year = dateStr.split("-")[0];
	var curDate = new Date();
	var curYear = curDate.getFullYear();
	return curYear - parseInt(year);
}

/**
 * 问题描述动态插入内容
 * @param name
 * @param text
 */
function insertQuestionFragment(){
	$("div.description span").each(function(){
		var answerCode = $(this).attr("name");
		var answerText = getAnswer(answerCode)["text"];
		$(this).html(answerText);
	});
}