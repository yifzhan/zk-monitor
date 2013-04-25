package com.zhanyf.hadoop.zk.util;

public class ExceptionTest {
	public static void main(String[] args) {
		try {
			new ExceptionTest().test();
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void test() throws DaoException {
		throw new DaoException();
	}
}
