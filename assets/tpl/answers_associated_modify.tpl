<div class="results">

	<div class="header size-title">
		问卷名称 - {{questionaire.title}}
	</div>
	
	<div class="toolbar">
		<input type="button" value="取消" class="btn-toolbar size-btn" onclick="jumpToAnswerList4App()"/>
		<input type="button" value="保存" class="btn-toolbar size-btn" onclick="saveModifyQuestionaire()"/>
	</div>
	
	<div class="body">
	
		<!--<div class="title">答案列表</div>-->
		
		<div class="content">
		
			{{each questionList as question i}}
			
				<div class="ques">
				
					<div class="description size-description">{{question.description}}</div>
					
					<div class="answers">
					
						{{each answerOfQuestionMap[question.code] as answerValue i}}
						
							<div class="answer">
							
								<div class="label size-answer-label">{{answerValue.label}}</div>
								<div class="text size-answer-text">{{answerValue.text}}</div>
								
							</div>
							
							<div style="clear:both;"></div>
						
						{{/each}}
					
					</div>
					
					<div style="clear:both;"></div>
					
				</div>
			
			{{/each}}
		
		</div>
		
	</div>
	
</div>