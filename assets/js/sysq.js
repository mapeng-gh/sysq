/**
 * ҳ����Ⱦ
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
 * �������϶���������
 * @param dom
 */
function onsliderchange(dom){
	$(dom).prev().children('.slider-value').html(dom.value);
}

var answers = {};//{"answerCode":{"answerCode":"","answerValue":"","answerText":"","answerLabel":""}}

/**
 * �ݴ��
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
		}
		
		answers[code] = {"answerCode":code,"answerValue":value,"answerText":text,"answerLabel":label};
		appservice.showMsg("answers = " + JSON.stringify(answers));
	});
}

/**
 * ��ת��һ������
 */
function jumpToFirstQuestion(){
	appservice.getFirstQuestion();
}


/**
 * ��ת��һ��
 */
function jumpToNextQuestion(){
	saveToAnswers();
	appservice.jumpToNextQuestion();
}

/**
 * ��ת���б�
 */
function jumpToAnswerList(){
	saveToAnswers();
	appservice.jumpToAnswerList(JSON.stringify(answers));
}

/**
 * �����ʾ�
 */
function saveQuestionaire(){
	appservice.saveQuestionaire(JSON.stringify(answers));
	answers = {};//��մ�
}

/**
 * �����ʾ�
 */
function redoQuestionaire(){
	answers = {};//��մ�
	appservice.getFirstQuestion();
}

/**
 * �˳���̸
 */
function quitInterview(){
	appservice.quitInterview();
}

/**
 * ��ת��һ��
 */
function jumpToPreviousQuestion(){
	appservice.jumpToPreviousQuestion();
}

