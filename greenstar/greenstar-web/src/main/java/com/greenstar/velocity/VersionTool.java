package com.greenstar.velocity;

import java.util.UUID;

import org.apache.velocity.tools.config.DefaultKey;

/***
 * 
 * @ClassName: VersionTool
 * @Description: 版本工具
 * @author yuanhualiang
 * @since JDK 1.7
 */
@DefaultKey("version")
public class VersionTool {
	/**
	 * 版本字符
	 */
	private static String VERSION_STR = "V1.2.6.14";

	/**
	 * @return
	 */
	public static String getVersion() {
		return VERSION_STR;
	}

	public static void main(String[] args) {
		System.out.print(UUID.randomUUID().toString().replace("-", ""));

	}
}
