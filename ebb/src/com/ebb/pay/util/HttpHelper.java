package com.ebb.pay.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;


public class HttpHelper {

	public static String doPostWithBody(String url, String param) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(param, "UTF-8"));
		post.addHeader("Content-Type", "application/json");
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
		post.setConfig(requestConfig);
		CloseableHttpResponse response = client.execute(post);
		String responseStr = EntityUtils.toString(response.getEntity(), "utf-8");
		response.close();
		client.close();
		return responseStr;
	}

	public static String doPostBaidu(String url, File file) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("img", file);
		post.setEntity(builder.build());
		String responseStr = "";
		try {
			CloseableHttpResponse response = client.execute(post);
			responseStr = inputStream2String(response.getEntity().getContent());
		} catch (Exception e) {
		}
		return responseStr;
	}

	public static String doPostWithImg(String url, MultipartFile[] img) throws Exception {
		return doPostWithFile(url, "img", img, null);
	}

	public static String doPostWithFile(String url, MultipartFile[] file) throws Exception {

		return doPostWithFile(url, "file", file, null);
	}

	public static String doPostWithFile(String url, String filepartkey, Map<String, String> params,
			String... filename) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		ContentType contentType = ContentType.create("text/plain", "utf-8");
		for (String fn : filename) {
			builder.addBinaryBody(filepartkey, new File(fn));
		}
		if (params != null) {
			for (String key : params.keySet()) {
				StringBody strBody = new StringBody(params.get(key), contentType);
				builder.addPart(key, strBody);
			}
		}
		post.setEntity(builder.build());
		String responseStr = "";
		try {
			CloseableHttpResponse response = client.execute(post);
			responseStr = inputStream2String(response.getEntity().getContent());
		} catch (Exception e) {
		}
		return responseStr;
	}

	/**
	 * post with file
	 * 
	 * @param uri
	 * @param filepartkey
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doPostWithFile(String uri, String filepartkey, MultipartFile[] file,
			Map<String, String> params) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(uri);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		for (MultipartFile f : file) {

			File f1 = new File("d:/soft/" + f.getOriginalFilename());
			inputStream2File(f.getInputStream(), f1);
			builder.addBinaryBody(filepartkey, f1);
		}
		ContentType contentType = ContentType.create("text/plain", "utf-8");
		if (params != null) {
			for (String key : params.keySet()) {
				StringBody strBody = new StringBody(params.get(key), contentType);
				builder.addPart(key, strBody);
			}
		}
		post.setEntity(builder.build());
		String responseStr = "";
		try {
			CloseableHttpResponse response = client.execute(post);
			responseStr = inputStream2String(response.getEntity().getContent());
		} catch (Exception e) {
		}
		for (MultipartFile f : file) {
			File f1 = new File("d:/soft/" + f.getOriginalFilename());
			if (f1.exists())
				f1.delete();
		}
		return responseStr;
	}

	/**
	 * post
	 * 
	 * @param uri
	 * @param params
	 * @return
	 */
	public static String doPost(String uri, Map<String, String> params) {
		String responseStr = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(uri);
		List<NameValuePair> paramlist = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			NameValuePair nvp = new BasicNameValuePair(key, params.get(key));
			paramlist.add(nvp);
		}
		try {
			StringEntity entity = new UrlEncodedFormEntity(paramlist, "utf-8");
			post.setEntity(entity);
			CloseableHttpResponse response = client.execute(post);
			responseStr = inputStream2String(response.getEntity().getContent());
		} catch (Exception e) {
		}
		return responseStr;
	}

	public static String doGet(String uri) {
		String responseStr = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(uri);
		CloseableHttpResponse response;
		try {
			response = client.execute(get);
			responseStr = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
		}
		return responseStr;
	}

	public static String inputStream2String(InputStream in) throws Exception {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public static void inputStream2File(InputStream inputStream, File file) throws Exception {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		inputStream.close();
	}

}
