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

var answers = [];

/**
 * ��ȡ�����
 */
function getLocalAnswerValue(){
	
	var localAnswers = [];
	
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
		var questionCode = $answer.data("question-code");
		
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
		
		localAnswers.push({"code":code,"label":label,"value":value,"text":text,"questionCode":questionCode});
	});
	
	return localAnswers;
}

/**
 * ��ȡ��ֵ
 * @param answerCode
 */
function getAnswerValue(answerCode){
	
	//�ӱ����ȡ
	var localAnswers = getLocalAnswerValue();
	for(var i=0;i<localAnswers.length;i++){
		if(localAnswers[i].code == answerCode){
			return localAnswers[i]["value"]; 
		}
	}
	
	//��������Ŀ��ȡ
	for(i=0;i<answers.length;i++){
		if(answers[i].code == answerCode){
			return answers[i]["value"];
		}
	}
}


/**
 * �ݴ��
 */
function saveToAnswers(){
	
	//��ȡ�����
	var localAnswers = getLocalAnswerValue();
	
	//����Ѿ����ڴ𰸣������
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
	answers = [];//��մ�
}

/**
 * �����ʾ�
 */
function redoQuestionaire(){
	answers = [];//��մ�
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
 * �༭���⣨���б���ת��
 * @param questionCode
 */
function editQuestion(questionCode){
	
	//��ո������Լ�֮����������д�
	var index = -1;
	for(var i=0;i<answers.length;i++){
		if(answers[i]["questionCode"] == questionCode){
			index = i;
			break;
		}
	}
	answers = answers.slice(0,i);
	appservice.debug(JSON.stringify(answers));
	
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

/**
 * ��������
 * @param dateStr  1988-07-03
 */
function calculateAge(dateStr){
	var year = dateStr.split("-")[0];
	var curDate = new Date();
	var curYear = curDate.getFullYear();
	return curYear - parseInt(year);
}