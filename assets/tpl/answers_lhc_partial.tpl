<div class="results-lhc">

	<div class="header size-title">
		问卷名称 - {{questionaireTitle}}
	</div>
	
	<div class="toolbar">
		<input type="button" value="继续" class="btn-toolbar size-btn" onclick="resumeQuestionaire()"/>
	</div>
	
	<div class="content">
		
		<table>
		
			<tr>
				<td>
					<p>Number of Episode</p>
					<p>发作次数</p>
				</td>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>4</td>
				<td>5</td>
				<td>6</td>
				<td>7</td>
				<td>8</td>
				<td>9</td>
			</tr>
			<tr>
				<td>
					<p>age</p>
					<p>发生年龄</p>
				</td>
				{{if answerList["LHC.1.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.1"].questionCode}}\')">
						{{answerList["LHC.1.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.1"].questionCode}}\')">
						{{answerList["LHC.2.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.1"].questionCode}}\')">
						{{answerList["LHC.3.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.1"].questionCode}}\')">
						{{answerList["LHC.4.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.1"].questionCode}}\')">
						{{answerList["LHC.5.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.1"].questionCode}}\')">
						{{answerList["LHC.6.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.1"].questionCode}}\')">
						{{answerList["LHC.7.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.1"].questionCode}}\')">
						{{answerList["LHC.8.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.1"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.1"].questionCode}}\')">
						{{answerList["LHC.9.1"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>is the worst</p>
					<p>是否最严重</p>
				</td>
				{{if answerList["LHC.1.1.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.1.a"].questionCode}}\')">
						{{answerList["LHC.1.1.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.1.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.1.a"].questionCode}}\')">
						{{answerList["LHC.2.1.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.1.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.1.a"].questionCode}}\')">
						{{answerList["LHC.3.1.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.1.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.1.a"].questionCode}}\')">
						{{answerList["LHC.4.1.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.1.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.1.a"].questionCode}}\')">
						{{answerList["LHC.5.1.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.1.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.1.a"].questionCode}}\')">
						{{answerList["LHC.6.1.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.1.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.1.a"].questionCode}}\')">
						{{answerList["LHC.7.1.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<p>Events or Out of Blue</p>
					<p>由于事件还是具体原因</p>
				</td>
				{{if answerList["LHC.1.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.2"].questionCode}}\')">
						{{answerList["LHC.1.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.2"].questionCode}}\')">
						{{answerList["LHC.2.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.2"].questionCode}}\')">
						{{answerList["LHC.3.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.2"].questionCode}}\')">
						{{answerList["LHC.4.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.2"].questionCode}}\')">
						{{answerList["LHC.5.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.2"].questionCode}}\')">
						{{answerList["LHC.6.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.2"].questionCode}}\')">
						{{answerList["LHC.7.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.2"].questionCode}}\')">
						{{answerList["LHC.8.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.2"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.2"].questionCode}}\')">
						{{answerList["LHC.9.2"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>Duration: weeks, months or years</p>
					<p>持续时间：周</p>
				</td>
				{{if answerList["LHC.1.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.3.w"].questionCode}}\')">
						{{answerList["LHC.1.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.3.w"].questionCode}}\')">
						{{answerList["LHC.2.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.3.w"].questionCode}}\')">
						{{answerList["LHC.3.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.3.w"].questionCode}}\')">
						{{answerList["LHC.4.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.3.w"].questionCode}}\')">
						{{answerList["LHC.5.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.3.w"].questionCode}}\')">
						{{answerList["LHC.6.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.3.w"].questionCode}}\')">
						{{answerList["LHC.7.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.3.w"].questionCode}}\')">
						{{answerList["LHC.8.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.3.w"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.3.w"].questionCode}}\')">
						{{answerList["LHC.9.3.w"]["value"]}}W
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>Treatment</p>
					<p>接受治疗与否</p>
				</td>
				{{if answerList["LHC.1.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.4"].questionCode}}\')">
						{{answerList["LHC.1.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.4"].questionCode}}\')">
						{{answerList["LHC.2.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.4"].questionCode}}\')">
						{{answerList["LHC.3.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.4"].questionCode}}\')">
						{{answerList["LHC.4.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.4"].questionCode}}\')">
						{{answerList["LHC.5.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.4"].questionCode}}\')">
						{{answerList["LHC.6.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.4"].questionCode}}\')">
						{{answerList["LHC.7.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.4"].questionCode}}\')">
						{{answerList["LHC.8.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.4"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.4"].questionCode}}\')">
						{{answerList["LHC.9.4"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>How well did treatment work?</p>
					<p>治疗效果如何</p>
				</td>
				{{if answerList["LHC.1.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.4.b"].questionCode}}\')">
						{{answerList["LHC.1.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.4.b"].questionCode}}\')">
						{{answerList["LHC.2.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.4.b"].questionCode}}\')">
						{{answerList["LHC.3.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.4.b"].questionCode}}\')">
						{{answerList["LHC.4.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.4.b"].questionCode}}\')">
						{{answerList["LHC.5.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.4.b"].questionCode}}\')">
						{{answerList["LHC.6.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.4.b"].questionCode}}\')">
						{{answerList["LHC.7.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.4.b"].questionCode}}\')">
						{{answerList["LHC.8.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.4.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.4.b"].questionCode}}\')">
						{{answerList["LHC.9.4.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE1 Marital separation/divorce</p>
					<p>LTE1 夫妻分居/离婚</p>
				</td>
				{{if answerList["LHC.1.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.a"].questionCode}}\')">
						{{answerList["LHC.1.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.a"].questionCode}}\')">
						{{answerList["LHC.2.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.a"].questionCode}}\')">
						{{answerList["LHC.3.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.a"].questionCode}}\')">
						{{answerList["LHC.4.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.a"].questionCode}}\')">
						{{answerList["LHC.5.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.a"].questionCode}}\')">
						{{answerList["LHC.6.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.a"].questionCode}}\')">
						{{answerList["LHC.7.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.a"].questionCode}}\')">
						{{answerList["LHC.8.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.a"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.a"].questionCode}}\')">
						{{answerList["LHC.9.5.a"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE2 Major injury or traffic accident</p>
					<p>LTE2 严重创伤或车祸</p>
				</td>
				{{if answerList["LHC.1.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.b"].questionCode}}\')">
						{{answerList["LHC.1.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.b"].questionCode}}\')">
						{{answerList["LHC.2.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.b"].questionCode}}\')">
						{{answerList["LHC.3.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.b"].questionCode}}\')">
						{{answerList["LHC.4.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.b"].questionCode}}\')">
						{{answerList["LHC.5.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.b"].questionCode}}\')">
						{{answerList["LHC.6.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.b"].questionCode}}\')">
						{{answerList["LHC.7.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.b"].questionCode}}\')">
						{{answerList["LHC.8.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.b"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.b"].questionCode}}\')">
						{{answerList["LHC.9.5.b"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE3 Loss of job/retirement</p>
					<p>LTE3 失业/下岗/退休</p>
				</td>
				{{if answerList["LHC.1.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.c"].questionCode}}\')">
						{{answerList["LHC.1.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.c"].questionCode}}\')">
						{{answerList["LHC.2.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.c"].questionCode}}\')">
						{{answerList["LHC.3.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.c"].questionCode}}\')">
						{{answerList["LHC.4.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.c"].questionCode}}\')">
						{{answerList["LHC.5.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.c"].questionCode}}\')">
						{{answerList["LHC.6.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.c"].questionCode}}\')">
						{{answerList["LHC.7.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.c"].questionCode}}\')">
						{{answerList["LHC.8.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.c"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.c"].questionCode}}\')">
						{{answerList["LHC.9.5.c"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE4 Death /major illness of spouse</p>
					<p>LTE4 配偶死亡或患重病</p>
				</td>
				{{if answerList["LHC.1.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.d"].questionCode}}\')">
						{{answerList["LHC.1.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.d"].questionCode}}\')">
						{{answerList["LHC.2.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.d"].questionCode}}\')">
						{{answerList["LHC.3.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.d"].questionCode}}\')">
						{{answerList["LHC.4.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.d"].questionCode}}\')">
						{{answerList["LHC.5.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.d"].questionCode}}\')">
						{{answerList["LHC.6.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.d"].questionCode}}\')">
						{{answerList["LHC.7.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.d"].questionCode}}\')">
						{{answerList["LHC.8.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.d"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.d"].questionCode}}\')">
						{{answerList["LHC.9.5.d"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE5 Business bankrupt</p>
					<p>LTE5 营企业或家庭经济破产</p>
				</td>
				{{if answerList["LHC.1.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.e"].questionCode}}\')">
						{{answerList["LHC.1.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.e"].questionCode}}\')">
						{{answerList["LHC.2.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.e"].questionCode}}\')">
						{{answerList["LHC.3.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.e"].questionCode}}\')">
						{{answerList["LHC.4.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.e"].questionCode}}\')">
						{{answerList["LHC.5.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.e"].questionCode}}\')">
						{{answerList["LHC.6.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.e"].questionCode}}\')">
						{{answerList["LHC.7.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.e"].questionCode}}\')">
						{{answerList["LHC.8.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.e"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.e"].questionCode}}\')">
						{{answerList["LHC.9.5.e"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE6 Death/major illness of other close family member</p>
					<p>LTE6 家庭其他成员死亡或患重病</p>
				</td>
				{{if answerList["LHC.1.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.f"].questionCode}}\')">
						{{answerList["LHC.1.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.f"].questionCode}}\')">
						{{answerList["LHC.2.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.f"].questionCode}}\')">
						{{answerList["LHC.3.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.f"].questionCode}}\')">
						{{answerList["LHC.4.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.f"].questionCode}}\')">
						{{answerList["LHC.5.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.f"].questionCode}}\')">
						{{answerList["LHC.6.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.f"].questionCode}}\')">
						{{answerList["LHC.7.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.f"].questionCode}}\')">
						{{answerList["LHC.8.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.f"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.f"].questionCode}}\')">
						{{answerList["LHC.9.5.f"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE7 Violence</p>
					<p>LTE7 遭到暴力打击/被强暴</p>
				</td>
				{{if answerList["LHC.1.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.g"].questionCode}}\')">
						{{answerList["LHC.1.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.g"].questionCode}}\')">
						{{answerList["LHC.2.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.g"].questionCode}}\')">
						{{answerList["LHC.3.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.g"].questionCode}}\')">
						{{answerList["LHC.4.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.g"].questionCode}}\')">
						{{answerList["LHC.5.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.g"].questionCode}}\')">
						{{answerList["LHC.6.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.g"].questionCode}}\')">
						{{answerList["LHC.7.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.g"].questionCode}}\')">
						{{answerList["LHC.8.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.g"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.g"].questionCode}}\')">
						{{answerList["LHC.9.5.g"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE8 Major natural disaster (e.g. flood & drought)</p>
					<p>LTE8 严重自然灾害（如旱、涝等）</p>
				</td>
				{{if answerList["LHC.1.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.h"].questionCode}}\')">
						{{answerList["LHC.1.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.h"].questionCode}}\')">
						{{answerList["LHC.2.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.h"].questionCode}}\')">
						{{answerList["LHC.3.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.h"].questionCode}}\')">
						{{answerList["LHC.4.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.h"].questionCode}}\')">
						{{answerList["LHC.5.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.h"].questionCode}}\')">
						{{answerList["LHC.6.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.h"].questionCode}}\')">
						{{answerList["LHC.7.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.h"].questionCode}}\')">
						{{answerList["LHC.8.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.h"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.h"].questionCode}}\')">
						{{answerList["LHC.9.5.h"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE9 Major conflict within family</p>
					<p>LTE9 严重的家庭内部矛盾及冲突</p>
				</td>
				{{if answerList["LHC.1.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.i"].questionCode}}\')">
						{{answerList["LHC.1.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
					{{if answerList["LHC.2.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.i"].questionCode}}\')">
						{{answerList["LHC.2.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.i"].questionCode}}\')">
						{{answerList["LHC.3.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.i"].questionCode}}\')">
						{{answerList["LHC.4.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.i"].questionCode}}\')">
						{{answerList["LHC.5.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.i"].questionCode}}\')">
						{{answerList["LHC.6.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.i"].questionCode}}\')">
						{{answerList["LHC.7.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.i"].questionCode}}\')">
						{{answerList["LHC.8.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.i"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.i"].questionCode}}\')">
						{{answerList["LHC.9.5.i"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
			<tr>
				<td>
					<p>LTE10 Loss of income / living on debt</p>
					<p>LTE10 丧失经济来源/负债度日</p>
				</td>
				{{if answerList["LHC.1.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.1.5.j"].questionCode}}\')">
						{{answerList["LHC.1.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.2.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.2.5.j"].questionCode}}\')">
						{{answerList["LHC.2.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.3.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.3.5.j"].questionCode}}\')">
						{{answerList["LHC.3.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.4.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.4.5.j"].questionCode}}\')">
						{{answerList["LHC.4.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.5.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.5.5.j"].questionCode}}\')">
						{{answerList["LHC.5.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.6.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.6.5.j"].questionCode}}\')">
						{{answerList["LHC.6.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.7.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.7.5.j"].questionCode}}\')">
						{{answerList["LHC.7.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.8.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.8.5.j"].questionCode}}\')">
						{{answerList["LHC.8.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
				{{if answerList["LHC.9.5.j"]}}
					<td onclick="editQuestion(\'{{answerList["LHC.9.5.j"].questionCode}}\')">
						{{answerList["LHC.9.5.j"]["value"]}}
					</td>
				{{else}}
					<td></td>
				{{/if}}
			</tr>
		</table>
		
	</div>
		
</div>