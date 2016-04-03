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
	$(dom).parent().find('div.slider-value').html(dom.value);
}

var answers = {};

/**
 * �ݴ��
 */
function saveToAnswers(){
	$("div.answer").each(function(){
		var $answer = $(this);
		
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
		
		answers[code] = {"code":code,"label":label,"value":value,"text":text};
		
		appservice.showMsg("answers = " + JSON.stringify(answers));
	});
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

