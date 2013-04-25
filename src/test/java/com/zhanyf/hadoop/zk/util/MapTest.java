package com.zhanyf.hadoop.zk.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class MapTest {

	@Test
	public void testMapNull() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(null, null);
		map.put("xxx", null);
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println("---");
		}
	}
}
