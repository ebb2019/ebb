package com.ebb.utils;

import java.io.File;
import java.util.Date;

import org.junit.Test;

import cn.hutool.core.date.DateUtil;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ebb.common.Code;
import com.jfinal.kit.PropKit;

public class AliyunUtils extends ConfigUtils {

	/**
	 * 存储图片到OSS
	 * 
	 * @param imgPath
	 *            图片保存路径
	 * @return
	 */
	public static String setImgToOss(StringBuffer imgPath) {

		String sex = imgPath.substring(imgPath.length() - 4, imgPath.length());
		Date date = DateUtil.date();
		Long str = date.getTime();
		// 封装名称
		String imgName = str.toString() + sex;
		String url = save(imgPath, imgName, Code.IMGDUCKET);
		return url;
	}

	/**
	 * 存储视频到OSS
	 * 
	 * @param videoPath
	 * @return
	 */
	public static String setVideoToOss(StringBuffer videoPath) {

		String sex = videoPath.substring(videoPath.length() - 4,
				videoPath.length());
		Date date = DateUtil.date();
		Long str = date.getTime();
		// 封装名称
		String imgName = str.toString() + sex;
		String url = save(videoPath, imgName, Code.VIDEODUCKET);
		return url;
	}

	/**
	 * 向OSS存储文件
	 * 
	 * @param imgPath
	 *            文件路径
	 * @param imgName
	 *            文件名称
	 * @return
	 */
	private static String save(StringBuffer imgPath, String imgName,
			String ducket) {
		String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = PropKit.use("aliyun.properties")
				.get("accessKeyId");
		String accessKeySecret = PropKit.use("aliyun.properties").get(
				"accessKeySecret");
		String bucketName = PropKit.use("aliyun.properties").get(ducket);

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,
				accessKeySecret);

		// 创建存储空间。
		ossClient.createBucket(bucketName);
		// 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
		ossClient.putObject(bucketName, imgName, new File(imgPath.toString()));
		// 关闭OSSClient。
		ossClient.shutdown();

		// 获得存储路径
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24
				* 365 * 10);
		String url = ossClient.generatePresignedUrl(bucketName, imgName,
				expiration).toString();

		return url;
	}
	
	@Test
	public void Test1(){
		StringBuffer buff = new StringBuffer("D:\\timg.jpg");
		setImgToOss(buff);
	}
}
