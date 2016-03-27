<div class="question-header">
	问卷名称 - {{questionaire.title}}
</div>

<div class="question-toolbar">
	<input type="button" value="暂停"/>
	<input type="button" value="重做"/>
	<input type="button" value="结束"/>
	<input type="button" value="答案"/>
</div>
	
<div class="question-desc">
	{{question.code}} - {{question.description}}
</div>
	
<div class="question-answer-list">
	
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
					<input name="{{answerWrap.answer.code}}" style="width:100%;" class="slider" type="range" min="{{answerWrap.sliderOption.start}}" max="{{answerWrap.sliderOption.end}}" step="{{answerWrap.sliderOption.step}}" value="{{answerWrap.sliderOption.start}}" onchange="onsliderchange(this)"/>
						
				{{else if answerWrap.answer.type == "calendar"}}	<!-- 日期 -->
				
					<input type="date"/>
				
				{{/if}}
			
			</div>
			
		{{/each}}
	
	{{else if question.type == "complex"}}	<!-- 复杂问题  -->
	
		{{if question.showType == "table"}}	<!-- 表格显示 -->
		
			{{if subQuesWrapList[0]["answerWrapList"].length == 1}} <!-- 单一答案 -->
			
				<table style="width:100%">
					
						<!-- 生成表头  -->
						<tr>
						
							<td style="width:{{100/(subQuesWrapList[0]["answerWrapList"][0].radioOptions.length+1)}}%"></td>
							
							{{if subQuesWrapList[0]["answerWrapList"][0].answer.type == "radiogroup"}} <!-- 单选框  -->
							
								{{each subQuesWrapList[0]["answerWrapList"][0].radioOptions as radioOption i}}
								
									<td style="width:{{100/(subQuesWrapList[0]["answerWrapList"][0].radioOptions.length+1)}}%">{{radioOption.text}}</td>
								
								{{/each}}
							
							{{else}}
							
							{{/if}}
						
						</tr>
						
						<!-- 生成表体 -->
						{{each subQuesWrapList as subQuesWrap i}}
						
							<tr class="answer" data-type="{{subQuesWrap.answerWrapList[0].answer.type}}" data-code="{{subQuesWrap.answerWrapList[0].answer.code}}">
								
								<td>{{subQuesWrap.question.code}} {{subQuesWrap.question.description}}</td>
								
								{{each subQuesWrap.answerWrapList[0].radioOptions as radioOption i}}
								
									<td><input type="radio" name={{subQuesWrap.answerWrapList[0].answer.code}} value="{{radioOption.value}}" {{i == 0 ? "checked":""}}/><span style="display:none;">{{radioOption.text}}</span></td>
								
								{{/each}}
								
							</tr>
						
						{{/each}} 
					
				</table>
			
			{{else}}  <!-- 多个答案 -->
				
				<table style="width:100%"> 
				
					<!-- 表头 -->
					<tr>
						<td rowspan="2" style="width:{{100/(subQuesWrapList[0]["answerWrapList"].length+1)}}%"></td>
						{{each subQuesWrapList[0]["answerWrapList"] as answerWrap i}}
							<td colspan="{{answerWrap.radioOptions.length}}" style="text-align:center;width:{{100/(subQuesWrapList[0]["answerWrapList"].length+1)}}%">{{answerWrap.answer.label}}</td>
						{{/each}}
					</tr>
					<tr>
						{{each subQuesWrapList[0]["answerWrapList"] as answerWrap i}}
							{{each answerWrap.radioOptions as radioOption i}}
								<td style="text-align:center;width:{{(100/(subQuesWrapList[0]["answerWrapList"].length+1))/(radioOption.length)}}%">{{radioOption.text}}</td>
							{{/each}}
						{{/each}}
					</tr>
					
					<!-- 表体 -->
					{{each subQuesWrapList as subQuesWrap i}}
					
						<tr>
						
							<td>{{subQuesWrap.question.code}} {{subQuesWrap.question.description}}</td>
							
							{{each subQuesWrap.answerWrapList as answerWrap j}}
									
										<td class="answer" data-type="{{answerWrap.answer.type}}" data-code="{{answerWrap.answer.code}}" colspan="{{answerWrap.radioOptions.length}}">
											
											<span class="answer-label" style="display:none;">{{answerWrap.answer.label}}</span>
											
											{{each answerWrap.radioOptions as radioOption k}}
												
												<span style="text-align:center;display:inline-block;width:{{100/answerWrap.radioOptions.length}}%">
													<input type="radio" name="{{answerWrap.answer.code}}" value="{{radioOption.value}}" {{k == 0 ? "checked":""}}/>
													<span style="display:none;">{{radioOption.text}}</span>
												</span>
											{{/each}}
										
										</td>

							{{/each}}
						
						</tr>
					
					{{/each}}
					
				</table>
			
			{{/if}}
		
		{{else if question.showType == "list"}}	<!-- 列表显示 -->
		
		{{/if}}
	
	{{/if}}
	
</div>

<div class="question-footer">
	<input type="button" value="上一题"/>
	<input type="button" value="下一题" onclick="{{question.exitLogic}}"/>
</div>
