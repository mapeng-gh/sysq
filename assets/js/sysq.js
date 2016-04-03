/**
 * 页面渲染
 * @param tpl
 * @param data
 */
function renderContent(tpl,data){
	var render = template.compile(tpl);
	appservice.showMsg("compile finished");
	data = JSON.parse(data);
	appservice.showMsg("parse finished");
	var html = render(data);
	appservice.showMsg("render finished");
	appservice.showMsg(html);
	$("#content").html(html);
}

/**
 * 滑动块拖动触发函数
 * @param dom
 */
function onsliderchange(dom){
	$(dom).prev().children('.slider-value').html(dom.value);
}

var answers = {};//{"answerCode":{"answerCode":"","answerValue":"","answerText":"","answerLabel":""}}

/**
 * 暂存答案
 */
function saveToAnswers(){
	$(".answer").each(function(){
		var $this = $(this);
		var code = $this.data("code");
		var label = ""; 
		if($this.find("span.answer-label").length > 0){
			label = $this.find("span.answer-label").html();
		}
		
		var value;
		var text;
		var type = $this.data("type");
		if(type == "radiogroup"){
			value = $this.find("input[type='radio']:checked").val();
			text = $this.find("input[type='radio']:checked").next().html();
		}else if(type == "slider"){
			value = $this.find("input[type='range']").val();
			text = value;
		}else if(type == "dropdownlist"){
			value = $this.find("select").val();
			text = $this.find("select option:checked").text();
		}else if(type == "text"){
			value = $this.find("input[type='text']").val();
			text = value;
		}else if(type == "checkbox"){
			
		}
		
		answers[code] = {"answerCode":code,"answerValue":value,"answerText":text,"answerLabel":label};
		appservice.showMsg("answers = " + JSON.stringify(answers));
	});
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

