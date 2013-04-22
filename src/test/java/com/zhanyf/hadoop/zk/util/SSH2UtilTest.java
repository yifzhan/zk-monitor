package com.zhanyf.hadoop.zk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSH2UtilTest {
	private static final String HOST_NAME = "192.168.16.202";
	private static final int HOST_PORT = 22;

	private static final String CMD_DF_LH = "df -lh";

	@Test
	public void testSSH2() {

		Connection connection = new Connection(HOST_NAME, HOST_PORT);
		try {
			connection.connect();
			boolean isAuthenticated = connection.authenticateWithPassword("root", "111111");
			if (isAuthenticated == false)
				throw new IOException("not connected, for authentication is not correct");
			Session session = connection.openSession();
			session.execCommand(CMD_DF_LH);
			InputStream stdin = new StreamGobbler(session.getStdout());
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
