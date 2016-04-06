/**
 * ҳ����Ⱦ
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
 * ҳ���ʼ��
 * @param entryLogic
 */
function entry(entryLogic){
	eval(entryLogic);
}


/**
 * �������϶���������
 * @param dom
 */
function onsliderchange(dom){
	$(dom).parent().find('div.slider-value').html(dom.value);
}

var answers = {};

/**
 * ��ȡ�����
 */
function getLocalAnswerValue(){
	
	var localAnswers = {};
	
	$("div.answer").each(function(){
		var $answer = $(this);
		
		//�������ش�
		if($answer.hasClass("nodisplay")){
			return;
		}
		
		//��ȡ��ͨ������
		var code = $answer.data("code");
		var label = $answer.data("label");
		var type = $answer.data("type");
		
		//��ȡֵ���ı�
		var value,text;
		if(type == "radiogroup"){	//��ѡ 
			
			var $selectedRadio = $answer.find("input[type='radio']:checked");
			value = $selectedRadio.val();
			text = $selectedRadio.next().text();
			
		}else if(type == "slider"){//������
			
			var $range = $answer.find("input[type='range']");
			value = $range.val();
			text = value;
			
		}else if(type == "dropdownlist"){//������
			
			var $select = $answer.find("select");
			value = $select.val();
			text = $select.find("option:checked").text();
			
		}else if(type == "text"){//�ı���
			
			var $textarea = $answer.find("textarea");
			value = $textarea.val();
			text = value;
			
		}else if(type == "calendar"){//����
			
			var $date = $answer.find("input[type='date']");
			value = $date.val();
			text = value;
		}
		
		localAnswers[code] = {"code":code,"label":label,"value":value,"text":text};
	});
	
	return localAnswers;
}

/**
 * ��ȡ��ֵ
 * @param answerCode
 */
function getAnswerValue(answerCode){
	
	var answerValue;
	
	var localAnswers = getLocalAnswerValue();
	if(localAnswers[answerCode]){//������
		answerValue = localAnswers[answerCode];
	}else{//��������Ŀ���
		answerValue = answers[answerCode];
	}
	
	return answerValue["value"];
}


/**
 * �ݴ��
 */
function saveToAnswers(){
	
	var localAnswers = getLocalAnswerValue();
	$.extend(answers,localAnswers);
	
	appservice.debug("answers = " + JSON.stringify(answers));
}

/**
 * ��ʼ��̸
 */
function startInterview(){
	appservice.jumpToFirstQuestion();
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
 * ��;��ת���б�
 */
function jumpToPartialAnswerList(){
	appservice.jumpToPartialAnswerList(JSON.stringify(answers));
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
	appservice.jumpToFirstQuestion();
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

/**
 * ��ͣ��̸
 */
function pauseInterview(){
	appservice.pauseInterview(JSON.stringify(answers));
}

/**
 * ������ʾ
 * @param msg
 */
function showMsg(msg){
	appservice.showMsg(msg);
}

/**
 * ��תָ������
 * @param questionCode
 */
function jumpToSpecQuestion(questionCode){
	saveToAnswers();
	appservice.jumpToSpecQuestion(questionCode);
}

/**
 * ��ʾ
 * @param answerCode
 */
function showAnswer(answerCode){
	var $answer = $("div.answer[data-code='"+answerCode+"']");
	if($answer.hasClass("nodisplay")){
		$answer.removeClass("nodisplay");
	}
}

/**
 * ����
 * @param answerCode
 */
function hideAnswer(answerCode){
	var $answer = $("div.answer[data-code='"+answerCode+"']");
	if(!$answer.hasClass("nodisplay")){
		$answer.addClass("nodisplay");
	}
}

/**
 * ������̸����;��ת���б�
 */
function resumeQuestionaire(){
	appservice.resumeQuestionaire();
}

/**
 * ��ת��������
 * @param endQuestionCode
 */
function jumpToEnd(endQuestionCode){
	appservice.jumpToEnd(endQuestionCode);
}

/**
 * ������̸������𰸣�
 */
function quitInterviewAndSave(){
	appservice.quitInterviewAndSave(JSON.stringify(answers));
}