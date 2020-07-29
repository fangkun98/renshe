package com.ciyun.renshe.common;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WebRequest {

	public static String getWebString(String url,Map<String,String> map){
		String data = null;
		CloseableHttpClient instance = HttpClientBuilder.create().disableRedirectHandling().build(); 
		HttpGet httpGet = new HttpGet(url + getUrlParam(map));
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				30000).setConnectTimeout(30000).build();
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = instance.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				data = EntityUtils.toString(entity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public static String postWebString(String url,Map<String,String> map){
		String data = null;
		CloseableHttpClient instance = HttpClientBuilder.create().disableRedirectHandling().build(); 
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			List nvps = new ArrayList();
			for(Map.Entry<String, String> entry : map.entrySet()){
	            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			response = instance.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				data = EntityUtils.toString(responseEntity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	
	public static String getWebStringXML(String url,String xml){
		String data = null;
		CloseableHttpClient instance = HttpClientBuilder.create().disableRedirectHandling().build(); 
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				10000).setConnectTimeout(10000).build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/xml; charset=utf-8");
		CloseableHttpResponse response = null;
		try {
			StringEntity myEntity = new StringEntity(xml);
			httpPost.setEntity(myEntity);
			response = instance.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				data = EntityUtils.toString(entity, "utf-8");
				InputStream in = entity.getContent();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	/*public static Map getWebJson(String url,Map<String,String> map){
		return JsonMapperManager.readJsonMapContent(getWebString(url, map));
	}*/
	
	private static String getUrlParam(Map<String,String> map){
		if(map == null || map.size() == 0){
			return "";
		}
		String param = "?";
		for(Map.Entry<String, String> entry : map.entrySet()){
			param = param + entry.getKey() + "=" + entry.getValue() + "&";
		}
		return param.substring(0, param.length() - 1);
	}
	
	public static String postJson(String url,String json){
		String data = null;
		CloseableHttpClient instance = HttpClientBuilder.create().disableRedirectHandling().build(); 
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new StringEntity(json, "UTF-8"));
			response = instance.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				data = EntityUtils.toString(responseEntity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static String postWebJson(String url,String data){
		String result = null;
		CloseableHttpClient instance = HttpClientBuilder.create().disableRedirectHandling().build(); 
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new StringEntity(data,"UTF-8"));
			response = instance.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				result = EntityUtils.toString(responseEntity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static String postWebString(String url,String str){
		String data = null;
		CloseableHttpClient instance = HttpClientBuilder.create().disableRedirectHandling().build(); 
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
		
		CloseableHttpResponse response = null;
		try {
			
			StringEntity entity = new StringEntity(str,Charset.forName("UTF-8"));//解决中文乱码问题    			
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");			
			httpPost.setEntity(entity);			
			response = instance.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				data = EntityUtils.toString(responseEntity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static void main(String[] args) {

		String str = "6+m5vqDR3eEi7QHBx5ypyZBBVAEb5MBhxrfRj3pY++ckGW3oCvPZtcjwY60kj+9A8G9zCHpGAOwtHf8OoxeJqqV7SprbLYx0nzBCXYKoyd2/1dlBRGq3UQMgn70bL7gzlbCPvtkMd8FbJLVh0VpcmFWVJErOZN/S9mAjijW6b6AcEVJgIAGFa8GViXK1m9QSk2v8KjbUUT3LCk2ffgupvnOnb8y6YSBgQSO0dbQFRTPaHLuJtc1a3DEaNu7EJPk7ITQhRlIsY+edcDpVP0rntw==";
		String s = WebRequest.postWebString("http://221.215.38.123:7001/qdaio-gett/case/saveApply", str);
		System.out.println(s);

		//1、报账基本信息 http://10.135.6.213:8000/easp/SieBoeEmp 
//		Map<String,String> SieBoeEmpMap = new HashMap<String, String>();
//		SieBoeEmpMap.put("employeeNumber","01024201");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/SieBoeEmp", JSON.toJSONString(SieBoeEmpMap));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
		
		
//		2、报账草稿基本信息 http://10.135.6.213:8000/easp/BoeInfo
//		Map<String,String> BoeInfo = new HashMap<String, String>();
//		BoeInfo.put("boeNum","F011603000426");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/BoeInfo", JSON.toJSONString(BoeInfo));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
		
		
//		3、经营体选择接口 http://10.135.6.213:8000/easp/BudgetChoose
//		Map<String,String> BudgetChoose = new HashMap<String, String>();
//		BudgetChoose.put("employeeNumber","01024201");
//		BudgetChoose.put("boeTypeCode","gd_errands");
//		BudgetChoose.put("pageNum","1");
//		BudgetChoose.put("pageSize","3");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/BudgetChoose", JSON.toJSONString(BudgetChoose));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
		
		
		//4、结算单位选择接口http://10.135.6.213:8000/easp/SettlementUnit（成禹）
//		Map<String,String> SettlementUnit = new HashMap<String, String>();
//		SettlementUnit.put("employeeNumber","01024201");
//		SettlementUnit.put("pageNum","1");
//		SettlementUnit.put("pageSize","3");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/SettlementUnit", JSON.toJSONString(SettlementUnit));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
		
		
		/***
		 * 数据为空
		 */
//		6、研发项目接口 http://10.135.6.213:8000/easp/Independent
//		Map<String,String> Independent = new HashMap<String, String>();
//		Independent.put("employeeNumber","01024201");
//		Independent.put("balanceUnitCode","9120");//结算单位编码未知
//		Independent.put("operationTypeCode","6666020201");
//		Independent.put("pageNum","1");
//		Independent.put("pageSize","3");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/Independent", JSON.toJSONString(Independent));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
		
		
		/***
		 * 数据为空
		 */
		//7、核销借款查询接口 http://10.135.6.213:8000/easp/PrepayApply（郝思朦）
//		Map<String,String> PrepayApply = new HashMap<String, String>();
//		PrepayApply.put("employeeNumber","01024201");
//		PrepayApply.put("balanceUnitCode","9120");   //结算单位编码 未知
//		PrepayApply.put("currencyCode","CNY");
//		PrepayApply.put("pageNum","1");
//		PrepayApply.put("pageSize","3");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/PrepayApply", JSON.toJSONString(PrepayApply));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
		
		/**
		 * 国家-地区编码
		 */
		//8、个人标准接口 http://10.135.6.213:8000/easp/Personal
//		Map<String,String> Personal = new HashMap<String, String>();
//		Personal.put("employeeNumber","01024201");
//		Personal.put("balanceUnitCode","9120");   
//		Personal.put("Personal","");
//		Personal.put("stayStatusCode","1");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/Personal", JSON.toJSONString(Personal));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
		
//		Map<String,String> Personal = new HashMap<String, String>();
//		Personal.put("pageNum","1");
//		Personal.put("pageSize","1000");   
////		Personal.put("countryName","中国");
//		String data = WebRequest.postJson("http://10.135.6.213:8000/easp/CountryChoice", JSON.toJSONString(Personal));
//		JSONObject object = JSON.parseObject(data);
//		System.out.println(object.getString("data"));
//		System.out.println(data);
	}
}
