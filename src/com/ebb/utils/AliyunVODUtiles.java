package com.ebb.utils;

import java.util.List;

import org.junit.Test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jfinal.kit.PropKit;

public class AliyunVODUtiles {
	
	private static String AccessKeyId = PropKit.use("aliyun.properties")
			.get("accessKeyId");
	private static String AccessKeySecret = PropKit.use("aliyun.properties").get(
			"accessKeySecret");
	
	/*获取播放地址函数*/
	public static String getPlayInfo(String vId) throws Exception {
		String regionId = "cn-shanghai";
	    IClientProfile profile = DefaultProfile.getProfile(regionId, AccessKeyId, AccessKeySecret);
	    GetPlayInfoRequest request = new GetPlayInfoRequest();
	    DefaultAcsClient client = new DefaultAcsClient(profile);
	    GetPlayInfoResponse response = new GetPlayInfoResponse();
	    request.setVideoId(vId);
	    response = client.getAcsResponse(request);
	    List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
	    GetPlayInfoResponse.PlayInfo playInfo = playInfoList.get(0);
	    return playInfo.getPlayURL();
	}
	
	/*获取播放凭证函数*/
	public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
	    GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
	    request.setVideoId("bb031aaab376410c8a6c5e4092318b90");
	    return client.getAcsResponse(request);
	}
	
////	/*以下为调用示例*/
//	public static void main(String[] argv) {
//	    DefaultAcsClient client = InitVodClient(AccessKeyId, AccessKeySecret);
//	    GetPlayInfoResponse response = new GetPlayInfoResponse();
//	    try {
//	        response = getPlayInfo(client);
//	        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
//	        //播放地址
//	        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//	            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
//	        }
//	        //Base信息
//	        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
//	    } catch (Exception e) {
//	        System.out.print("ErrorMessage = " + e.getLocalizedMessage());
//	    }
//	    System.out.print("RequestId = " + response.getRequestId() + "\n");
//	}
	
	/*以下为调用示例*/
	@Test
	public void test2(){
		 DefaultAcsClient client = InitVodClient("LTAIdJ7LIQPylNxj", "XqozcTYm5wKxY4itcM58EMggKLxkK6");
		    GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
		    try {
		        response = getVideoPlayAuth(client);
		        //播放凭证
		        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
		        //VideoMeta信息
		        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
		    } catch (Exception e) {
		        System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		    }
		    System.out.print("RequestId = " + response.getRequestId() + "\n");
	}
	
	public static DefaultAcsClient InitVodClient(String accessKeyId, String accessKeySecret)
	{
	    // 点播服务接入区域
	    String regionId = "cn-shanghai";
	    IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
	    // DefaultProfile.AddEndpoint(regionId, regionId, "vod", "vod." + regionId + ".aliyuncs.com");
	    return new DefaultAcsClient(profile);
	}
	
}
