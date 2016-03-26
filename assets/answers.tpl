<div class="header">
	问卷名称 - {{questionaire.title}}
</div>

<div class="toolbar">
	<input type="button" value="重做"/>
	<input type="button" value="结束"/>
	<input type="button" value="保存"/>
</div>

<div class="results">

	<div class="results-title">答案列表</div>
	
	<div class="results-content">
	
		<table>
		
		{{each questionList as question i}}
		
			<tr>
				
				<td class="seq">{{(i+1)}}</td>
				<td class="question-desc">{{question.description}}</td>
				
				{{if question.type == "simple"}}
				
					<td class="answer-value">{{answerMap[question.code]}}</td>
				
				{{else if question.type == "complex"}}
				
					
				
				{{/if}}
				
				
			</tr>
		
		{{/each}}
		
		</table>
	
	</div>
	
</div>