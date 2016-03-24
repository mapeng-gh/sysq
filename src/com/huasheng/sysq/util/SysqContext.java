package com.huasheng.sysq.util;

import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.model.Version;

public class SysqContext {

	private static Interviewer interviewer;//访问者
	private static Version currentVersion;//当前版本
	
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
