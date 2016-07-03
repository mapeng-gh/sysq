package com.huasheng.sysq.service;

import java.util.List;

import com.huasheng.sysq.db.VersionDB;
import com.huasheng.sysq.model.Version;

public class SystemUpdateService {

	/**
	 * 获取当前问卷版本
	 * @return
	 */
	public static Version getCurrentInterviewVersion(){
		List<Version> versionList = VersionDB.select();
		if(versionList == null || versionList.size() <= 0){
			return null;
		}
		for(Version version : versionList){
			if(version.getIsCurrent() == 1){
				return version;
			}
		}
		return null;
	}
	
	/**
	 * 切换问卷版本
	 * @param oldVersionId
	 * @param newVersionId
	 */
	public static void switchInterviewVersion(int oldVersionId,int newVersionId){
		Version oldVersion = VersionDB.select(oldVersionId);
		oldVersion.setIsCurrent(0);
		VersionDB.update(oldVersion);
		Version newVersion = VersionDB.select(newVersionId);
		newVersion.setIsCurrent(1);
		VersionDB.update(newVersion);
	}
}
