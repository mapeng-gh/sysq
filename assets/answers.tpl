<div class="answers-header">
	问卷名称 - {{questionaire.title}}
</div>

<div class="answers-toolbar">
	<input type="button" value="重做" onclick="redoQuestionaire()"/>
	<input type="button" value="结束" onclick="quitInterview()"/>
	<input type="button" value="保存" onclick="saveQuestionaire()"/>
</div>

<div class="answers-results">

	<div class="title">答案列表</div>
	
	<div class="content">
	
		<table>
		
		{{each questionList as question i}}
		
			{{if question.type == "simple"}} <!-- 简单问题 -->
			
				<tr>
					<td class="seq">{{(i+1)}}</td>
					<td class="question-desc">{{question.code}} {{question.description}}</td>
					<td class="answer-value">{{answerMap[question.code]}}</td>
				</tr>
			
			{{else if question.type == "complex"}}  <!-- 复杂问题   -->
			
				<tr>
					<td class="seq">{{(i+1)}}</td>
					<td class="question-desc">{{question.code}} {{question.description}}</td>
					<td class="answer-value"></td>
				</tr>
				
				{{each subQuesListMap[question.code] as subQues i}}
				
					<tr>
						<td class="seq"></td>
						<td class="question-desc">{{subQues.code}} {{subQues.description}}</td>
						<td class="answer-value">{{answerMap[subQues.code]}}</td>
					</tr>
				
				{{/each}}
			
			{{/if}}
			
		{{/each}}
		
		</table>
	
	</div>
	
</div>