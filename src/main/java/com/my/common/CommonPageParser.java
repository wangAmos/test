package com.my.common;

import com.my.config.Configuration;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class CommonPageParser {
	
	private static VelocityEngine ve;
	
	static {
		String templateBasePath = Configuration.templatePath;
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "file");
		properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
		properties.setProperty("file.resource.loader.path", templateBasePath);
		properties.setProperty("file.resource.loader.cache", "true");
		properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
		properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
		properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
		properties.setProperty("directive.set.null.allowed", "true");
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init(properties);
		ve = velocityEngine;
	}
	
	public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile) {
	

		try {
			File file = new File(fileDirPath + targetFile);
			if (!(file.exists())) {
				new File(file.getParent()).mkdirs();
			}
			Template template = ve.getTemplate(templateName, Configuration.systemEncoding);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, Configuration.systemEncoding));
			template.merge(context, writer);
			writer.flush();
			writer.close();
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}