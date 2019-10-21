package com.ebb.controller;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ebb.common.Code;
import com.ebb.common.Inits;
import com.ebb.model.dto.ReturnJson;
import com.ebb.model.dto.ToPage;
import com.ebb.utils.AliyunUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

/**
 * 动态类
 * @author Administrator
 *
 */
public class AppDynamicController extends Controller{
	
	private static Logger log = Logger.getLogger(AppDynamicController.class);
	/**
	 * @author xwyf
	 * 
	 * @describe 获取动态中的信息
	 */
	@Clear
	public void getPraiseList(){
		Integer currentPage = getInt("currentPage");
		Integer pageSize = getInt("pageSize");
		
		ReturnJson returnJson = new ReturnJson();
		
		Page<Record> dynamicList = new Page<Record>();
		try {
			ToPage initPage = Inits.initPage(currentPage, pageSize);
			dynamicList = Db.paginate(initPage.getCurrentPage(), initPage.getPagesize(), "select * ","from dynamic_info where is_able = 1");
			
			returnJson.setBody(dynamicList);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * 点赞
	 */
	@Clear
	public void clickPraise(){
		Integer id = getInt("id");
		Integer clickUserId = getInt("clickUserId"); //点赞人id
		Record tempRd = new Record();
		ReturnJson rj = new ReturnJson();
		try {
			//获取动态内容
			Record rd = Db.findFirst("select * from dynamic_info where id = "+id+"");
			//保存实例
			tempRd.set("dynamic_id", id);
			tempRd.set("user_pub_id", rd.getInt("user_id"));
			tempRd.set("user_praise_id", clickUserId);
			tempRd.set("create_time", new Date());
			tempRd.set("type", 1);
			Db.save("rela_praise", tempRd);
			
			//点在数加+1
			Integer tempNum = rd.getInt("praise_num");
			tempNum ++;
			//执行更新
			rd.set("praise_num", tempNum);
			Db.update("dynamic_info", "id", rd);
			rj.setBody(rd);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("获取成功");
		} catch (Exception e) {
			// TODO: handle exception
			
			rj.setCode(Code.FAIL);
			rj.setMessage("获取失败");
			log.info(e);
		}finally{
			renderJson(rj);
		}
	}
	@Clear
	public void collectPraise(){
		Integer id = getInt("id");
		Integer collectPraise = getInt("collectPraise"); //收藏人id
		Record tempRd = new Record();
		ReturnJson rj = new ReturnJson();
		try {
			//获取动态内容
			Record rd = Db.findFirst("select * from dynamic_info where id = "+id+"");
			//保存实例
			tempRd.set("dynamic_id", id);
			tempRd.set("user_pub_id", rd.getInt("user_id"));
			tempRd.set("user_collect_id", collectPraise);
			tempRd.set("type", 2);
			tempRd.set("create_time", new Date());
			Db.save("rela_praise", tempRd);
			
			//点在数加+1
			Integer tempNum = rd.getInt("collect_num");
			tempNum ++;
			//执行更新
			rd.set("collect_num", tempNum);
			Db.update("dynamic_info", "id", rd);
			
			rj.setBody(rd);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("获取成功");
		} catch (Exception e) {
			rj.setCode(Code.FAIL);
			rj.setMessage("获取失败");
			log.info(e);
		}finally{
			renderJson(rj);
		}
	}
	/**
	 * 保存照片
	 * 
	 * (未测试)
	 */
	@Clear
	public void savePhoto(){
		Integer userId = getInt("userId");
		UploadFile img = getFile("img");
		ReturnJson returnJson = new ReturnJson();
		try {
			StringBuffer imgPath = new StringBuffer(img.getUploadPath()); 
			//返回一个oss中存储的url
			String url = AliyunUtils.setImgToOss(imgPath);
			
			int update = Db.update("update dynamic_info set img_path = "+ url +"where user_id = " + userId);
			
			returnJson.setCode(Code.SUCCESS); 
			returnJson.setMessage("保存成功");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("保存异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * 保存热门中的评论
	 */
	@Clear
	public void saveComment(){
		Integer userId = getInt("userId");
		Integer dynamicId = getInt("dynamicId");
		String content = get("content");
		Record record= new Record();
		ReturnJson returnJson = new ReturnJson();
		
		record.set("user_id", userId);
		record.set("dynamic_id",dynamicId);
		record.set("content", content);
		record.set("create_time",new Date());
		
		try {
			boolean save = Db.save("rela_comment", record);
			if(save){
				Record findFirst = Db.findFirst("select comment_num from dynamic_info where id = "+dynamicId);
				Integer commentNum = findFirst.getInt("comment_num");
				commentNum++;
				int update = Db.update("update dynamic_info set comment_num = "+commentNum +" where id = "+dynamicId);
				
				returnJson.setCode(Code.SUCCESS);
				returnJson.setMessage("保存成功");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("保存异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * 查询当前动态下所有的评论
	 */
	@Clear
	public void getCurDynamicComment(){
		Integer dynamicId = getInt("dynamicId");
		
		ReturnJson returnJson = new ReturnJson();
		
		try {
			List<Record> find = Db.find("select * from rela_comment r left join user_info u on r.user_id = u.id where r.dynamic_id ="+dynamicId);
			
			if(find.isEmpty()){
				returnJson.setCode(Code.SUCCESS);
				returnJson.setMessage("当前暂无评论");
				return;
			}
			returnJson.setBody(find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
	}
}
