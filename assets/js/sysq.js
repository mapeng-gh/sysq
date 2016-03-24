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

function onsliderchange(dom){
	$(dom).prev().children('.slider-value').html(dom.value);
}

var answers = [];//{"answerCode":"answerValue"}

function saveToAnswers(){
	$("div.answer").each(function(){
		var $this = $(this);
		var code = $this.data("code");
		var type = $this.data("type");
		var value;
		if(type == "radiogroup"){
			value = $this.find("input[type='radio']:checked").val();
		}else if(type == "slider"){
			value = $this.find("input[type='range']").val();
		}
		answers.push({"answerCode":code,"answerValue":value});
		appservice.showMsg("answers = " + JSON.stringify(answers));
	});
}

function jumpToNextQuestion(){
	saveToAnswers();
	appservice.jumpToNextQuestion();
}

function jumpToAnswerList(){
	saveToAnswers();
	appservice.jumpToAnswerList(JSON.stringify(answers));
}
