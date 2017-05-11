package com.greenstar.velocity;

import java.io.File;
import java.net.URL;

import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/***
 * 
 * @ClassName: EimClasspathResourceLoader
 * @Description: 热加载模版
 * @author yuanhualiang
 * @since JDK 1.7
 */
public class EimClasspathResourceLoader extends ClasspathResourceLoader {

	/**
	 * FIXME 根据文件名字获取class目录下面的文件
	 * 
	 * @param name
	 *            模版资源名称
	 * @return 模版文件
	 */
	private File getResource(String name) {
		File file = null;
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = this.getClass().getClassLoader();
		}
		if (name.startsWith("/")) {
			name = name.substring(1);
		}
		URL resource = classLoader.getResource(name);
		if (null != resource) {
			file = new File(resource.getFile());
		}
		return file;
	}

	/***
	 * 
	 * Title: isSourceModified Description: TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @param resource
	 * @return
	 * @see org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader#isSourceModified(org.apache.velocity.runtime.resource.Resource)
	 */
	@Override
	public boolean isSourceModified(Resource resource) {
		boolean modified = true;
		String name = resource.getName();
		File file = getResource(name);
		if (null != file && file.canRead()) {
			modified = (file.lastModified() != resource.getLastModified());
		}
		return modified;
	}

	/***
	 * 
	 * Title: getLastModified Description: TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @param resource
	 * @return
	 * @see org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader#getLastModified(org.apache.velocity.runtime.resource.Resource)
	 */
	@Override
	public long getLastModified(Resource resource) {
		String name = resource.getName();
		File file = getResource(name);
		return null == file || !file.canRead() ? 0l : file.lastModified();
	}
}
