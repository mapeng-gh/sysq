package com.huasheng.sysq.util;

import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.model.Version;

public class SysqContext {

	private static Interviewer interviewer;//������
	private static Version currentVersion;//��ǰ�汾
	
	public static Interviewer getInterviewer() {
		return interviewer;
	}
	public static void setInterviewer(Interviewer interviewer) {
		SysqContext.interviewer = interviewer;
	}
	public static Version getCurrentVersion() {
		return currentVersion;
	}
	public static void setCurrentVersion(Version currentVersion) {
		SysqContext.currentVersion = currentVersion;
	}
	
}
