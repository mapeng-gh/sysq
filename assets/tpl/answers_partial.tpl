<div class="results">

	<div class="header size-title">
		问卷名称 - {{questionaire.title}}
	</div>
	
	<div class="toolbar">
		<input type="button" value="继续" class="btn-toolbar" onclick="resumeQuestionaire()"/>
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
							
								<span class="label size-answer-label">{{answerValue.label}}</span>
								<span class="text size-answer-text">{{answerValue.text}}</span>
								
							</div>
							
							<div style="clear:both;"></div>
						
						{{/each}}
					
					</div>
					
					<div style="clear:both;"></div>
					
					<div class="operations">
					
						<input type="button" value="编辑"/>
					
					</div>
				
				</div>
			
			{{/each}}
		
		</div>
		
	</div>
	
</div>