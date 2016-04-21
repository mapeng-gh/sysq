<div class="results-lhc">

	<div class="header size-title">
		问卷名称 - {{questionaireTitle}}
	</div>
	
	<div class="toolbar">
		<input type="button" value="继续" class="btn-toolbar" onclick="resumeQuestionaire()"/>
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
				<td>{{answerList["LHC.1.1"]?answerList["LHC.1.1"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.1"]?answerList["LHC.2.1"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.1.2"]?answerList["LHC.3.1.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.1.2"]?answerList["LHC.4.1.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.1.2"]?answerList["LHC.5.1.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.1.2"]?answerList["LHC.6.1.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.1.2"]?answerList["LHC.7.1.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.1.2"]?answerList["LHC.8.1.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.1"]?answerList["LHC.9.1"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>is the worst</p>
					<p>是否最严重</p>
				</td>
				<td>{{answerList["LHC.1.1.b"]?answerList["LHC.1.1.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.1.b"]?answerList["LHC.2.1.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.1.b"]?answerList["LHC.3.1.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.1.b"]?answerList["LHC.4.1.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.1.b"]?answerList["LHC.5.1.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.1.b"]?answerList["LHC.6.1.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.1.b"]?answerList["LHC.7.1.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.1.a"]?answerList["LHC.8.1.a"]["text"]:""}}</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<p>Events or Out of Blue</p>
					<p>由于事件还是具体原因</p>
				</td>
				<td>{{answerList["LHC.1.2"]?answerList["LHC.1.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.2"]?answerList["LHC.2.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.2"]?answerList["LHC.3.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.2"]?answerList["LHC.4.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.2"]?answerList["LHC.5.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.2"]?answerList["LHC.6.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.2"]?answerList["LHC.7.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.2"]?answerList["LHC.8.2"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.2"]?answerList["LHC.9.2"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>Duration: weeks, months or years</p>
					<p>持续时间：周，月，年</p>
				</td>
				<td>
					{{if answerList["LHC.1.3.y"]}}
						{{answerList["LHC.1.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.1.3.m"]}}
						{{answerList["LHC.1.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.1.3.w"]}}
						{{answerList["LHC.1.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.2.3.y"]}}
						{{answerList["LHC.2.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.2.3.m"]}}
						{{answerList["LHC.2.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.2.3.w"]}}
						{{answerList["LHC.2.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.3.3.y"]}}
						{{answerList["LHC.3.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.3.3.m"]}}
						{{answerList["LHC.3.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.3.3.w"]}}
						{{answerList["LHC.3.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.4.3.y"]}}
						{{answerList["LHC.4.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.4.3.m"]}}
						{{answerList["LHC.4.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.4.3.w"]}}
						{{answerList["LHC.4.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.5.3.y"]}}
						{{answerList["LHC.5.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.5.3.m"]}}
						{{answerList["LHC.5.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.5.3.w"]}}
						{{answerList["LHC.5.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.6.3.y"]}}
						{{answerList["LHC.6.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.6.3.m"]}}
						{{answerList["LHC.6.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.6.3.w"]}}
						{{answerList["LHC.6.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.7.3.y"]}}
						{{answerList["LHC.7.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.7.3.m"]}}
						{{answerList["LHC.7.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.7.3.w"]}}
						{{answerList["LHC.7.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.8.3.y"]}}
						{{answerList["LHC.8.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.8.3.m"]}}
						{{answerList["LHC.8.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.8.3.w"]}}
						{{answerList["LHC.8.3.w"]["text"]}}W
					{{/if}}
				</td>
					<td>
					{{if answerList["LHC.9.3.y"]}}
						{{answerList["LHC.9.3.y"]["text"]}}Y
					{{/if}}
					{{if answerList["LHC.9.3.m"]}}
						{{answerList["LHC.9.3.m"]["text"]}}M
					{{/if}}
					{{if answerList["LHC.9.3.w"]}}
						{{answerList["LHC.9.3.w"]["text"]}}W
					{{/if}}
				</td>
			</tr>
			<tr>
				<td>
					<p>Treatment</p>
					<p>接受治疗与否</p>
				</td>
				<td>{{answerList["LHC.1.4"]?answerList["LHC.1.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.4"]?answerList["LHC.2.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.4"]?answerList["LHC.3.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.4"]?answerList["LHC.4.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.4"]?answerList["LHC.5.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.4"]?answerList["LHC.6.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.4"]?answerList["LHC.7.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.4"]?answerList["LHC.8.4"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.4"]?answerList["LHC.9.4"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>How well did treatment work?</p>
					<p>治疗效果如何</p>
				</td>
				<td>{{answerList["LHC.1.4.b"]?answerList["LHC.1.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.4.b"]?answerList["LHC.2.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.4.b"]?answerList["LHC.3.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.4.b"]?answerList["LHC.4.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.4.b"]?answerList["LHC.5.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.4.b"]?answerList["LHC.6.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.4.b"]?answerList["LHC.7.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.4.b"]?answerList["LHC.8.4.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.4.b"]?answerList["LHC.9.4.b"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE1 Marital separation/divorce</p>
					<p>LTE1 夫妻分居/离婚</p>
				</td>
				<td>{{answerList["LHC.1.5.a"]?answerList["LHC.1.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.a"]?answerList["LHC.2.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.a"]?answerList["LHC.3.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.a"]?answerList["LHC.4.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.a"]?answerList["LHC.5.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.a"]?answerList["LHC.6.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.a"]?answerList["LHC.7.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.a"]?answerList["LHC.8.5.a"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.a"]?answerList["LHC.9.5.a"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE2 Major injury or traffic accident</p>
					<p>LTE2 严重创伤或车祸</p>
				</td>
				<td>{{answerList["LHC.1.5.b"]?answerList["LHC.1.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.b"]?answerList["LHC.2.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.b"]?answerList["LHC.3.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.b"]?answerList["LHC.4.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.b"]?answerList["LHC.5.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.b"]?answerList["LHC.6.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.b"]?answerList["LHC.7.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.b"]?answerList["LHC.8.5.b"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.b"]?answerList["LHC.9.5.b"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE3 Loss of job/retirement</p>
					<p>LTE3 失业/下岗/退休</p>
				</td>
				<td>{{answerList["LHC.1.5.c"]?answerList["LHC.1.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.c"]?answerList["LHC.2.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.c"]?answerList["LHC.3.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.c"]?answerList["LHC.4.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.c"]?answerList["LHC.5.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.c"]?answerList["LHC.6.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.c"]?answerList["LHC.7.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.c"]?answerList["LHC.8.5.c"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.c"]?answerList["LHC.9.5.c"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE4 Death /major illness of spouse</p>
					<p>LTE4 配偶死亡或患重病</p>
				</td>
				<td>{{answerList["LHC.1.5.d"]?answerList["LHC.1.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.d"]?answerList["LHC.2.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.d"]?answerList["LHC.3.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.d"]?answerList["LHC.4.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.d"]?answerList["LHC.5.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.d"]?answerList["LHC.6.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.d"]?answerList["LHC.7.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.d"]?answerList["LHC.8.5.d"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.d"]?answerList["LHC.9.5.d"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE5 Business bankrupt</p>
					<p>LTE5 营企业或家庭经济破产</p>
				</td>
				<td>{{answerList["LHC.1.5.e"]?answerList["LHC.1.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.e"]?answerList["LHC.2.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.e"]?answerList["LHC.3.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.e"]?answerList["LHC.4.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.e"]?answerList["LHC.5.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.e"]?answerList["LHC.6.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.e"]?answerList["LHC.7.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.e"]?answerList["LHC.8.5.e"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.e"]?answerList["LHC.9.5.e"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE6 Death/major illness of other close family member</p>
					<p>LTE6 家庭其他成员死亡或患重病</p>
				</td>
				<td>{{answerList["LHC.1.5.f"]?answerList["LHC.1.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.f"]?answerList["LHC.2.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.f"]?answerList["LHC.3.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.f"]?answerList["LHC.4.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.f"]?answerList["LHC.5.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.f"]?answerList["LHC.6.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.f"]?answerList["LHC.7.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.f"]?answerList["LHC.8.5.f"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.f"]?answerList["LHC.9.5.f"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE7 Violence</p>
					<p>LTE7 遭到暴力打击/被强暴</p>
				</td>
				<td>{{answerList["LHC.1.5.g"]?answerList["LHC.1.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.g"]?answerList["LHC.2.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.g"]?answerList["LHC.3.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.g"]?answerList["LHC.4.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.g"]?answerList["LHC.5.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.g"]?answerList["LHC.6.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.g"]?answerList["LHC.7.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.g"]?answerList["LHC.8.5.g"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.g"]?answerList["LHC.9.5.g"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE8 Major natural disaster (e.g. flood & drought)</p>
					<p>LTE8 严重自然灾害（如旱、涝等）</p>
				</td>
				<td>{{answerList["LHC.1.5.h"]?answerList["LHC.1.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.h"]?answerList["LHC.2.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.h"]?answerList["LHC.3.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.h"]?answerList["LHC.4.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.h"]?answerList["LHC.5.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.h"]?answerList["LHC.6.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.h"]?answerList["LHC.7.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.h"]?answerList["LHC.8.5.h"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.h"]?answerList["LHC.9.5.h"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE9 Major conflict within family</p>
					<p>LTE9 严重的家庭内部矛盾及冲突</p>
				</td>
				<td>{{answerList["LHC.1.5.i"]?answerList["LHC.1.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.i"]?answerList["LHC.2.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.i"]?answerList["LHC.3.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.i"]?answerList["LHC.4.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.i"]?answerList["LHC.5.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.i"]?answerList["LHC.6.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.i"]?answerList["LHC.7.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.i"]?answerList["LHC.8.5.i"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.i"]?answerList["LHC.9.5.i"]["text"]:""}}</td>
			</tr>
			<tr>
				<td>
					<p>LTE10 Loss of income / living on debt</p>
					<p>LTE10 丧失经济来源/负债度日</p>
				</td>
				<td>{{answerList["LHC.1.5.j"]?answerList["LHC.1.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.2.5.j"]?answerList["LHC.2.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.3.5.j"]?answerList["LHC.3.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.4.5.j"]?answerList["LHC.4.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.5.5.j"]?answerList["LHC.5.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.6.5.j"]?answerList["LHC.6.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.7.5.j"]?answerList["LHC.7.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.8.5.j"]?answerList["LHC.8.5.j"]["text"]:""}}</td>
				<td>{{answerList["LHC.9.5.j"]?answerList["LHC.9.5.j"]["text"]:""}}</td>
			</tr>
		</table>
		
	</div>
		
</div>