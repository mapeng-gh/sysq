<div class="header">
	问卷名称 - {{questionaire.title}}
</div>

<div class="toolbar">
	<input type="button" value="暂停"/>
	<input type="button" value="重做"/>
	<input type="button" value="结束"/>
	<input type="button" value="答案"/>
</div>
	
<div class="question">
	{{question.code}} - {{question.description}}
</div>
	
<div class="answer-list">
	
	{{if question.type == "simple"}}	<!-- 简单答案 -->
	
		{{each answerWrapList as answerWrap i}}
		
			<div class="answer" data-type="{{answerWrap.answer.type}}" data-code="{{answerWrap.answer.code}}">
			
				<span class="answer-label">{{answerWrap.answer.label}}</span> 
		
				{{if answerWrap.answer.type == "radiogroup"}}	<!-- 单选按钮 -->
				
					{{each answerWrap.radioOptions as radioOption j}}
						<input type="radio" name="{{answerWrap.answer.code}}" {{j == 0 ? checked="checked":""}} value="{{radioOption.value}}"/><span class="radio-text">{{radioOption.text}}</span>
					{{/each}}
				
				{{else if answerWrap.answer.type == "slider"}}	<!-- 滑动块 -->
				
					<div class="slider-tip" style="text-align:center;">
						<span style="float:left;">{{answerWrap.sliderOption.start}}</span>
						<span class="slider-value">{{answerWrap.sliderOption.start}}</span>
						<span style="float:right;">{{answerWrap.sliderOption.end}}</span>
					</div>
					<input name="{{answerWrap.answer.code}}" class="slider" type="range" min="{{answerWrap.sliderOption.start}}" max="{{answerWrap.sliderOption.end}}" step="{{answerWrap.sliderOption.step}}" value="{{answerWrap.sliderOption.start}}" onchange="onsliderchange(this)"/>
						
				{{else if answerWrap.answer.type == "calendar"}}	<!-- 日期 -->
				
					<input type="date"/>
				
				{{/if}}
			
			</div>
			
		{{/each}}
	
	{{else if question.type == "complex"}}	<!-- 复杂答案（表格）  -->
	
	{{/if}}
	
</div>

<div class="footer">
	<input type="button" class="previous" value="上一题"/>
	<input type="button" class="next" value="下一题" onclick="{{question.exitLogic}}"/>
</div>
