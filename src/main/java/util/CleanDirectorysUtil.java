package util;

import java.io.File;

import org.apache.log4j.Logger;


/*
 * 清除截图文件夹
 * 保留三天
 */
public class CleanDirectorysUtil {
	
	private static Logger logger=Logger.getLogger(CleanDirectorysUtil.class);
	//配置保存文件夹数量
	private static int saveNum=3;
	public void clean(String dirpath) {
		File dir=new File(dirpath);
		File[] dirs=null;
		if(dir.isDirectory()) {
			dirs=dir.listFiles();
		}
		if(dirs.length>saveNum) {
			dirs[0].delete();
		}
	}
	
	}
	
