<div class="question">

	<div class="header size-title">
		问卷名称 - {{questionaire.title}}
	</div>
	
	<div class="toolbar">
		<input type="button" value="暂停" class="btn-toolbar size-btn" onclick="pauseInterview()"/>
		<input type="button" value="重做" class="btn-toolbar size-btn" onclick="redoQuestionaire()"/>
		<input type="button" value="结束" class="btn-toolbar size-btn" onclick="quitInterview()"/>
		<input type="button" value="答案" class="btn-toolbar size-btn" onclick="jumpToPartialAnswerList()"/>
	</div>
		
	<div class="contents">	
	
		<div class="description size-description">
			{{question.description}}
		</div>
			
		<div class="answers">
			
			{{each answerWrapList as answerWrap i}}
			
				<div class="answer {{answerWrap.answer.isShow == 0 ? "nodisplay":""}}" data-code="{{answerWrap.answer.code}}" data-type="{{answerWrap.answer.type}}" data-label="{{answerWrap.answer.label}}" data-seq-num="{{answerWrap.answer.seqNum}}" data-question-code="{{question.code}}">
			
						<span class="label size-answer-label">{{answerWrap.answer.label}}</span>
						
						<div class="content">
							
							{{if answerWrap.answer.type == "radiogroup"}}	<!-- 单选按钮 -->
							
								{{if answerWrap.answer.showType == "horizontal"}}  <!-- 横向   -->
								
									{{each answerWrap.radioOptions as radioOption i}}
										<span class="radio-option">
											<input type="radio" id="{{answerWrap.answer.code+i}}" name="{{answerWrap.answer.code}}"  value="{{radioOption.value}}" {{if answerWrap.answer.eventType}} on{{answerWrap.answer.eventType}}="{{answerWrap.answer.eventExecute}}"{{/if}}/>
											<label for="{{answerWrap.answer.code+i}}"><span class="size-answer-text">{{radioOption.text}}</span></label>
										</span>
									{{/each}}	
												
								{{else}} 	<!-- 纵向  -->
								
									{{each answerWrap.radioOptions as radioOption i}}
										<div class="radio-option">
											<input type="radio" id="{{answerWrap.answer.code+i}}" name="{{answerWrap.answer.code}}"  value="{{radioOption.value}}" {{if answerWrap.answer.eventType}} on{{answerWrap.answer.eventType}}="{{answerWrap.answer.eventExecute}}"{{/if}}/>
											<label for="{{answerWrap.answer.code+i}}"><span class="size-answer-text">{{radioOption.text}}</span></label>
										</div>
									{{/each}}
								
								{{/if}}
								
							{{else if answerWrap.answer.type == "checkbox"}}		<!-- 多选框 -->
							
								{{if answerWrap.answer.showType == "horizontal"}}  <!-- 横向   -->
								
									{{each answerWrap.checkboxOptions as checkboxOption i}}
										<span class="checkbox-option">
											<input id="{{answerWrap.answer.code+i}}" type="checkbox"  value="{{checkboxOption.value}}" />
											<label for="{{answerWrap.answer.code+i}}"><span class="size-answer-text">{{checkboxOption.text}}</span></label>
										</span>
									{{/each}}	
												
								{{else}} 	<!-- 纵向  -->
								
									{{each answerWrap.checkboxOptions as checkboxOption i}}
										<div class="checkbox-option">
											<input id="{{answerWrap.answer.code+i}}" type="checkbox"  value="{{checkboxOption.value}}" />
											<label for="{{answerWrap.answer.code+i}}"><span class="size-answer-text">{{checkboxOption.text}}</span></label>
										</div>
									{{/each}}
								
								{{/if}}
							
							{{else if answerWrap.answer.type == "slider"}}	<!-- 滑动块 -->
							
								<div class="slider-value size-answer-text">
									{{answerWrap.sliderOption.start}}
								</div>
								<span class="size-answer-text">{{answerWrap.sliderOption.start}}</span>
								<input type="range"  min="{{answerWrap.sliderOption.start}}" max="{{answerWrap.sliderOption.end}}" step="{{answerWrap.sliderOption.step}}" value="{{answerWrap.sliderOption.start}}" onchange="onsliderchange(this)" {{if answerWrap.answer.eventType}} on{{answerWrap.answer.eventType}}="{{answerWrap.answer.eventExecute}}"{{/if}}/>
								<span class="size-answer-text">{{answerWrap.sliderOption.end}}</span>
									
							{{else if answerWrap.answer.type == "calendar"}}	<!-- 日期 -->
							
								<input type="date" class="size-answer-text" {{if answerWrap.answer.eventType}} on{{answerWrap.answer.eventType}}="{{answerWrap.answer.eventExecute}}"{{/if}}/>
							
							{{else if answerWrap.answer.type == "dropdownlist"}}	 <!-- 下拉框   -->
							
								<select class="size-answer-text" name="{{answerWrap.answer.code}}" {{if answerWrap.answer.eventType}} on{{answerWrap.answer.eventType}}="{{answerWrap.answer.eventExecute}}"{{/if}}>
								
								{{each answerWrap.dropDownListOptions as dropDownListOption i}}
								
									<option class="size-answer-text" value="{{dropDownListOption.value}}">{{dropDownListOption.text}}</option>
								
								{{/each}}
								
								</select>
								
							{{else if answerWrap.answer.type == "text"}}		<!-- 文本域   -->
							
								<textarea class="size-answer-text" {{if answerWrap.answer.eventType}} on{{answerWrap.answer.eventType}}="{{answerWrap.answer.eventExecute}}"{{/if}}></textarea>
								
							{{else if answerWrap.answer.type == "spinbox"}}		<!-- 数字框 -->
							
								<input type="number"  data-start="{{answerWrap.spinBoxOption.start}}" data-end="{{answerWrap.spinBoxOption.end}}" data-step="{{answerWrap.spinBoxOption.step}}"/>
								
							{{/if}}
						
						</div>
					
					</div>
					
			{{/each}}
			
		</div>
		
	</div>
	
	<div class="footer">
		<input type="button" value="上一题" class="btn-bottom-toolbar size-btn" onclick="jumpToPreviousQuestion()"/>
		<input type="button" value="下一题" class="btn-bottom-toolbar size-btn" onclick="if(checkData()===false){return;}{{question.exitLogic}}"/>
	</div>
	
</div>
