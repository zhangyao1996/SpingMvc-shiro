package com.zhangyao.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.lionsoul.ip2region.*;

/**
* @author zhangyao
* @version Mar 18, 2019 4:34:10 PM
*/
public class AddressUtils {
	
	  public static String getAddress(String ip) {
	        //db
	        String dbPath = AddressUtils.class.getResource("/other/ip2region.db").getPath();

	        File file = new File(dbPath);

	        if (!file.exists()) {
	            //throw new GlobalException("缺少 ip2region.db库");
	        	
	        	System.out.println("不存在该库");
	        }

	        int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree

	        try {
	            DbConfig config = new DbConfig();
	            DbSearcher searcher = new DbSearcher(config, file.getPath());
	            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	            //define the method
	            Method method = null;
	            switch (algorithm) {
	                case DbSearcher.BTREE_ALGORITHM:
	                    method = searcher.getClass().getMethod("btreeSearch", String.class);
	                    break;
	                case DbSearcher.BINARY_ALGORITHM:
	                    method = searcher.getClass().getMethod("binarySearch", String.class);
	                    break;
	                case DbSearcher.MEMORY_ALGORITYM:
	                    method = searcher.getClass().getMethod("memorySearch", String.class);
	                    break;
	            }

	            DataBlock dataBlock = null;
	            if (!Util.isIpAddress(ip)) {
	                System.out.println("Error: Invalid ip address");
	            }
	            dataBlock = (DataBlock) method.invoke(searcher, ip);
	            reader.close();
	            searcher.close();
	            return dataBlock.getRegion();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "";
	    }
}

