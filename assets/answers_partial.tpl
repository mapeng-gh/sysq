<div class="results">

	<div class="header">
		问卷名称 - {{questionaire.title}}
	</div>
	
	<div class="toolbar">
		<input type="button" value="继续" onclick="resumeQuestionaire()"/>
	</div>
	
	<div class="body">
	
		<div class="title">答案列表</div>
		
		<div class="content">
		
			{{each questionList as question i}}
			
				<div class="question">
				
					<div class="description">{{question.description}}</div>
					
					<div class="answers">
					
						{{each answerOfQuestionMap[question.code] as answerValue i}}
						
							<div class="answer">
							
								<span class="label">{{answerValue.label}}</span>
								<span class="text">{{answerValue.text}}</span>
								
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