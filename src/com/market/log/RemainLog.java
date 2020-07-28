package com.market.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class RemainLog {
	// 출력레벨 trace < debug < info < error < fatal
	Logger logger;
	FileInputStream fis;
	String filePath = this.getClass().getResource("").getPath();

	String path = filePath+"log4j.properties";
	Properties props;

	public RemainLog() {
		logger = Logger.getLogger(this.getClass().getName());

		try {
			fis = new FileInputStream(path);
			props = new Properties();
			props.load(fis);
			// 로거에 설정파일 인식 시키기
			PropertyConfigurator.configure(props);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMessage() {
			logger.trace("출력");
			logger.debug("출력");
			logger.info("상태나 정보 출력");
			logger.error("에러");
			logger.fatal("치명적 에러");

	}

}