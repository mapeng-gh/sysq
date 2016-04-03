<div class="question">

	<div class="header">
		问卷名称 - {{questionaire.title}}
	</div>
	
	<div class="toolbar">
		<input type="button" value="暂停" onclick="pauseInterview()"/>
		<input type="button" value="重做" onclick="redoQuestionaire()"/>
		<input type="button" value="结束" onclick="quitInterview()"/>
		<input type="button" value="答案"/>
	</div>
		
	<div class="description">
		{{question.code}} - {{question.description}}
	</div>
		
	<div class="answers">
		
		{{each answerWrapList as answerWrap i}}
		
			<div class="answer" data-code="{{answerWrap.answer.code}}" data-type="{{answerWrap.answer.type}}" data-label="{{answerWrap.answer.label}}">
		
					<span class="label">{{answerWrap.answer.label}}</span>
					
					<div class="content">
						
						{{if answerWrap.answer.type == "radiogroup"}}	<!-- 单选按钮 -->
						
							{{if answerWrap.answer.showType == "horizontal"}}  <!-- 横向   -->
							
								{{each answerWrap.radioOptions as radioOption i}}
									<span class="radio-option">
										<input type="radio" name="{{answerWrap.answer.code}}" {{i == 0 ? checked="checked":""}} value="{{radioOption.value}}"/>
										<span>{{radioOption.text}}</span>
									</span>
								{{/each}}	
											
							{{else}} 	<!-- 纵向  -->
							
								{{each answerWrap.radioOptions as radioOption i}}
									<div class="radio-option">
										<input type="radio" name="{{answerWrap.answer.code}}" {{i == 0 ? checked="checked":""}} value="{{radioOption.value}}"/>
										<span>{{radioOption.text}}</span>
									</div>
								{{/each}}
							
							{{/if}}
						
						{{else if answerWrap.answer.type == "slider"}}	<!-- 滑动块 -->
						
							<div class="slider-value">
								{{answerWrap.sliderOption.start}}
							</div>
							<span>{{answerWrap.sliderOption.start}}</span>
							<input type="range"  min="{{answerWrap.sliderOption.start}}" max="{{answerWrap.sliderOption.end}}" step="{{answerWrap.sliderOption.step}}" value="{{answerWrap.sliderOption.start}}" onchange="onsliderchange(this)"/>
							<span>{{answerWrap.sliderOption.end}}</span>
								
						{{else if answerWrap.answer.type == "calendar"}}	<!-- 日期 -->
						
							<input type="date"/>
						
						{{else if answerWrap.answer.type == "dropdownlist"}}	 <!-- 下拉框   -->
						
							<select name="{{answerWrap.answer.code}}">
							
							{{each answerWrap.dropDownListOptions as dropDownListOption i}}
							
								<option value="{{dropDownListOption.value}}">{{dropDownListOption.text}}</option>
							
							{{/each}}
							
							</select>
							
						{{else if answerWrap.answer.type == "text"}}		<!-- 文本域   -->
						
							<textarea></textarea>
							
						{{/if}}
					
					</div>
				
				</div>
				
		{{/each}}
		
	</div>
	
	<div class="footer">
		<input type="button" value="上一题" onclick="jumpToPreviousQuestion()"/>
		<input type="button" value="下一题" onclick="{{question.exitLogic}}"/>
	</div>
	
</div>
