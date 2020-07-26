package com.market.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * �α׸� ���������� ó�����ִ� ���¼ҽ� ������ ���̺귯��
 * log4j(�ȵ���̵忡���� ���)
 * */
public class RemainLog {
	// ��·��� trace < debug < info < error < fatal
	Logger logger;
	FileInputStream fis;
	String path = "F:/lastProject/CucumberMarket/src/com/market/log/log4j.properties";
	Properties props;

	public RemainLog() {
		logger = Logger.getLogger(this.getClass().getName());

		try {
			fis = new FileInputStream(path);
			System.out.println("���� ã�� ����");
			props = new Properties();
			props.load(fis);
			// �ΰſ� �������� �ν� ��Ű��
			PropertyConfigurator.configure(props);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMessage() {
			logger.trace("���");
			logger.debug("���");
			logger.info("���³� ���� ���");
			logger.error("����");
			logger.fatal("ġ���� ����");

	}

}