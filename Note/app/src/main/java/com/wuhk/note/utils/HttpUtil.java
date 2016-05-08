package com.wuhk.note.utils;

import android.content.Context;

import com.xuan.bigapple.lib.http.BPHttpUtils;
import com.xuan.bigapple.lib.http.BPResponse;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class HttpUtil {

	/**
	 * 提交参数(无通用参数)
	 *
	 * @param context
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static BPResponse post(Context context, String url,
			Map<String, String> paramsMap) {
		displayLog(url, paramsMap);
		BPResponse response = null;

		try {
			response = BPHttpUtils.post(url, paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response = new BPResponse();
			response.setStatusCode(-1);
			response.setResultStr(e.getMessage());
		}

		return response;
	}

	/**
	 * 提交参数(无通用参数)
	 *
	 * @param context
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static BPResponse get(Context context, String url,
								  Map<String, String> paramsMap) {
		displayLog(url, paramsMap);
		BPResponse response = null;

		try {
			response = BPHttpUtils.get(url, paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response = new BPResponse();
			response.setStatusCode(-1);
			response.setResultStr(e.getMessage());
		}

		return response;
	}
	/**
	 * 打印请求日志
	 *
	 * @param url
	 * @param paramsMap
	 */
	private static void displayLog(String url, Map<String, String> paramsMap) {
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append("?");
		for (Map.Entry<String, String> e : paramsMap.entrySet()) {
			sb.append(e.getKey()).append("=")
					.append(URLEncoder.encode(e.getValue().trim())).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		LogUtil.e("请求地址：" + sb.toString());
	}

}
