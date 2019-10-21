package com.ebb.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.aliyuncs.exceptions.ClientException;
import com.ebb.common.Code;
import com.ebb.model.User;
import com.ebb.model.dto.ReturnJson;
import com.ebb.utils.AliyunSMS;
import com.ebb.utils.MD5Utils;
import com.ebb.utils.RandomNameUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

/**
 * @author wangxu
 */
public class AppUserController extends Controller {

	private static Logger log = Logger.getLogger(AppUserController.class);

	/**
	 *  @author wangxu
	 *   
	 * 	@describe APP端登陆，附带token
	 * 
	 * 	@Clear 不进行拦截
	 */
	@Clear
	public void login() {
		String mobile = get("mobile");
		//String code = get("code");
		String code = "123456";
		
		Record record = new Record();
		ReturnJson returnJson = new ReturnJson();
		
		record.set("mobile", mobile);
		Integer randomNum = RandomNameUtils.getSixRandom();
		
		
		try{
			if(mobile.isEmpty()){
				returnJson.setCode(Code.FAIL);
				returnJson.setMessage("手机号不能为空");
			}
			String token = "";
			Record find = Db.findFirst("select * from user_info where mobile='"+ mobile +"'");
			
			if (find != null) { // 可登陆
				// 生成token
				token = (System.currentTimeMillis() + new Random()
						.nextInt(999999999)) + "";
				token = MD5Utils.ToMd5(token);
				log.info("本次登录用户id为:" + find.getInt("id") + ",token为：" + token);
				// 将token存入redis
				Cache bbsCache = Redis.use("bbs");
				bbsCache.set(token, mobile);
				returnJson.setBody(find);
				returnJson.setToken(token);
				returnJson.setCode(Code.SUCCESS);
				returnJson.setMessage("登陆成功！");

			} else {
				record.set("nick_id",randomNum);
				record.set("nick_name", RandomNameUtils.getStringRandom(11));
				Db.save("user_info", record);
				token = (System.currentTimeMillis() + new Random()
				.nextInt(999999999)) + "";
				token = MD5Utils.ToMd5(token);
				// 将token存入redis
				Cache bbsCache = Redis.use("bbs");
				bbsCache.set(token, mobile);
				returnJson.setBody(record);
				returnJson.setToken(token);
				returnJson.setCode(Code.SUCCESS);
				returnJson.setMessage("登陆成功！");
			}
			User user = new User();
			user.setMobile(mobile);
			user.setUserId(record.getInt("id"));
			set("user", user);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			renderJson(returnJson);
		}
	}
	
	

	/**
	 * @author wangxu
	 * 
	 * @describe 会员注册，直接生产token
	 * 
	 * @Clear 不进行拦截
	 */
	@Clear
	public void regist() {
		String mobile = get("mobile");
		String password = get("password");
		String code ="123";
		ReturnJson rj = new ReturnJson();
		if(mobile.isEmpty() || password.isEmpty() || code.isEmpty()){
			rj.setCode(Code.FAIL);
			rj.setMessage("字段不能为空！");
			renderJson(rj);
			return;
		}
		
		//判断账号是否已经别注册
		List<Record> list = Db.find("select * from user_info where mobile = "+mobile+"");
		if(list.size() > 0){
			rj.setCode(Code.FAIL);
			rj.setMessage("您的手机号已注册！");
			renderJson(rj);
			return;
		}
		//生成随机id
		Integer randomNum = RandomNameUtils.getSixRandom();
		
		
		boolean b = true;
		
		while(b){
			List<Record> bList = Db.find("select * from user_info where nick_id = "+randomNum+"");
			if(bList.size() > 0){ //有重复
				randomNum = RandomNameUtils.getSixRandom();
			}else{
				b = false;
			}
		}
		
		// 直接存如数据库
		Record rd = new Record();
		rd.set("mobile", mobile);
		rd.set("password", password);
		rd.set("create_time", new Date());
		rd.set("update_time", new Date());
		rd.set("nick_name", RandomNameUtils.getStringRandom(11));
		rd.set("nick_id", randomNum);
		
		try {
			Db.save("user_info", rd);

			// 生成token
			String token = (System.currentTimeMillis() + new Random()
					.nextInt(999999999)) + "";
			token = MD5Utils.ToMd5(token);
			log.info("本次登录用户id为:" + rd.getInt("id") + ",token为：" + token);
			// 将token存入radis
			Cache bbsCache = Redis.use("bbs");
			rj.setBody(rd);
			bbsCache.set(token, mobile);
			rj.setToken(token);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("登陆成功！");

		} catch (Exception e) {
			log.error(e);
			rj.setCode(Code.FAIL);
			rj.setMessage(e + "");
		}

		renderJson(rj);
	}

	/**
	 * @author wangxu
	 * 
	 * @describe 忘记密码,直接登陆，存入token
	 * 
	 * @Clear 不进行拦截
	 */
	@Clear
	public void forget() {
		String mobile = get("mobile");
		String password = get("password");
		String code = get("code");

		ReturnJson rj = new ReturnJson();

		// 直接存入数据库
		Record rd = new Record();
		rd.set("mobile", mobile);
		rd.set("password", password);

		try {
			Db.update("user_info", "mobile", rd);
			// 生成token
			String token = (System.currentTimeMillis() + new Random()
					.nextInt(999999999)) + "";
			token = MD5Utils.ToMd5(token);
			log.info("本次登录用户id为:" + rd.getInt("id") + ",token为：" + token);
			// 将token存入radis
			Cache bbsCache = Redis.use("bbs");
			bbsCache.set(token, mobile);
			rj.setBody(rd);
			rj.setToken(token);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("修改成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			rj.setCode(Code.FAIL);
			rj.setMessage(e + "");
		}
		renderJson(rj);
	}
	
	/**
	 * @author wangxu
	 * 
	 * @describe 注册时获得验证码
	 * 
	 * @Clear 不进行拦截
	 */
	@Clear
	public void getRegistCode() {
		String mobile = get("mobile");
		Integer code =(int)((Math.random()*9+1)*100000);
		
		ReturnJson rj = new ReturnJson();
		
		try {
			AliyunSMS.sendSms(mobile, code.toString(),Code.SmsRegist);
			//将短信放入缓存
			Cache bbsCache = Redis.use("bbs");
			bbsCache.setex(code, 180, mobile);
			rj.setCode(Code.SUCCESS);
			rj.setBody(code);
			rj.setMessage("验证码获得成功");
			
		} catch (ClientException e) {
			log.info(e+"");
			e.printStackTrace();
			rj.setCode(Code.FAIL);
			rj.setMessage("验证码获得失败");
		}
		renderJson(rj);
	}
	
	/**
	 * @author wangxu
	 * 
	 * @describe 注册时获得验证码
	 * 
	 * @Clear 不进行拦截
	 */
	@Clear
	public void getForgetCode() {
		String mobile = get("mobile");
		Integer code =(int)((Math.random()*9+1)*100000);
		ReturnJson rj = new ReturnJson();
		try {
			AliyunSMS.sendSms(mobile, code.toString(),Code.SmsForget);
			//将短信放入缓存
			Cache bbsCache = Redis.use("bbs");
			bbsCache.setex(code, 180, mobile);
			rj.setCode(Code.SUCCESS);
			rj.setBody(code);
			rj.setMessage("验证码获得成功");
		} catch (ClientException e) {
			log.info(e+"");
			e.printStackTrace();
			rj.setCode(Code.FAIL);
			rj.setMessage("验证码获得失败");
		}
		renderJson(rj);
	}
	
	

	/**
	 * @author xwyf
	 * 
	 * @describe 保存调查信息
	 */
	@Clear
	public void saveSurvey(){
		
		Integer userId = getInt("userId");
		//1为男，2为女
		Integer sex = getInt("sex");
		Integer dicAgeId = getInt("dicAgeId");
		String height = get("height");
		String weight = get("weight");
		//0为没有经验，1为有经验
		Integer isExp = getInt("isExp");
		Integer dicDurationId = getInt("dicDurationId");
		Integer dicMemberId = getInt("dicMemberId");
		String residentPlace = get("residentPlace");
		String clubName = get("clubName");
		
		//调查表所用record
		Record record = new Record();
		//用户表所用record
		Record userRecord = new Record();
		ReturnJson returnJson = new ReturnJson();
		
//		if(sex == null || dicAgeId == null || height.isEmpty() || weight.isEmpty() || isExp == null || dicDurationId == null || dicMemberId == null || residentPlace.isEmpty() || clubName.isEmpty()){
//			returnJson.setCode(Code.FAIL);
//			returnJson.setMessage("请输入完整信息!");
//			renderJson(returnJson);
//			return;
//		}
		
		try{
			record.set("user_id", userId);
			record.set("sex", sex);
			record.set("dic_age_id",dicAgeId);
			record.set("height",Double.parseDouble(height));
			record.set("weight",Double.parseDouble(weight));
			record.set("is_exp", isExp);
			record.set("dic_duration_id", dicDurationId);
			record.set("dic_member_id", dicMemberId);
			record.set("resident_place", residentPlace);
			record.set("club_name", clubName);
			
			Db.save("survey_table", record);

			userRecord.set("id",userId);
			userRecord.set("sex", sex);
			userRecord.set("update_time",new Date());
			userRecord.set("survey_id",record.getInt("id"));
			
			Db.update("user_info", "id", userRecord);

			returnJson.setBody(record);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("个人资料保存成功");
	
		}catch(Exception e){
			log.info(e+"");
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("个人资料保存异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * @author xwyf
	 * 
	 * @describe 获取年龄字典信息
	 */
	@Clear
	public void getMasDic(){
		String type = get("type");
		ReturnJson returnJson = new ReturnJson();
		
		try{
			List<Record> find  = Db.find("select * from user_dic where type = '"+type+"' and is_able =1");
			
			if(find.isEmpty()){
				returnJson.setCode(Code.FAIL);
				returnJson.setMessage("获取值为空");
				return;
			}
			returnJson.setBody(find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		}catch(Exception e){
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**@author wx
	 * @describe 根据关键字获得健身场所
	 */
	@Clear
	public void findDicByKeyWord(){
		String type = get("type");
		String keyWord = get("keyWord");
		ReturnJson returnJson = new ReturnJson();
		
		try{
			List<Record> find  = Db.find("select a.describe from user_dic a where a.type = '"+type+"' and a.is_able =1 and a.netic like '%"+keyWord+"%'");
			
			if(find.isEmpty()){
				returnJson.setCode(Code.FAIL);
				returnJson.setMessage("获取值为空");
				return;
			}
			returnJson.setBody(find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		}catch(Exception e){
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * @author xwyf
	 * 
	 * @describe 获取年限字典信息
	 */
	@Clear
	public void getDicDuration(){
		String type = get("type");
		ReturnJson returnJson = new ReturnJson();
		
		try{
			List<Record> find = Db.find("select id,min_duration_num,max_duration_num from dictionary where type = '" +type+ "'");
			if(find.isEmpty()){
				returnJson.setCode(Code.FAIL);
				returnJson.setMessage("获取值为空");
				return;
			}
			returnJson.setBody(find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		}catch(Exception e){
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * @author xwyf
	 * 
	 * @describe 获取地址信息中的省
	 */
	@Clear
	public void getProvince(){
		Record record = new Record();
		ReturnJson returnJson = new ReturnJson();
		try{
			List<Record> find = Db.find("select * from china where pid = 0");
			if(find.isEmpty()){
				returnJson.setCode(Code.FAIL);
				returnJson.setMessage("暂无信息");
				return;
			}
			
			record.set("province", find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setBody(record);
			returnJson.setMessage("获取成功");
		}catch(Exception e){
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取失败");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * @author xwyf
	 * 
	 * @describe 获取地址信息中的市
	 */
	@Clear
	public void getCity(){
		Record record = new Record();
		ReturnJson returnJson = new ReturnJson();
		
		Integer id = getInt("id");
		
		try{
			List<Record> find = Db.find("select * from china where pid = '" +id+ "'");
			
			record.set("city", find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setBody(record);
			returnJson.setMessage("获取成功");
		}catch(Exception e){
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取失败");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * @author xwyf
	 * 
	 * @describe 获取地址信息中的省
	 */
	@Clear
	public void getCitys(){
		Record record = new Record();
		ReturnJson returnJson = new ReturnJson();
		try{
			List<Record> find = Db.find("select * from china where pid = 0 and id != 0");
			for(Record r : find){
				
				Record rd = new Record();
				List<Record> citys = Db.find("select * from china where pid = '" +r.getInt("id")+ "'");
				r.set("sub", citys);
			}
			
			record.set("province", find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setBody(record);
			returnJson.setMessage("获取成功");
		}catch(Exception e){
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取失败");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * 判断是否已经填过报表
	 */
	@Clear
	public void getIsSurvey(){
		
		Integer id = getInt("id");
		ReturnJson returnJson = new ReturnJson();
		List<Record> rds = Db.find("select * from user_info where survey_id is not null and id = "+id+"");
		if(rds.size() > 0){ //说明已填写
			returnJson.setMessage("已经填写");
			returnJson.setCode(Code.SUCCESS);
		}else{
			returnJson.setMessage("未填写");
			returnJson.setCode(Code.FAIL);
		}
		renderJson(returnJson);
	}
	/**
	 * 根据用户id获取用户所有的基础信息
	 */
	@Clear
	public void getUserInfo(){
		Integer id = getInt("id");
		ReturnJson returnJson = new ReturnJson();
		try {
			List<Record> find = Db.find("select * from user_info where id = "+id);
			
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
	
	public void getUserInfoByIdList(){
		String ids = get("ids");
		ReturnJson rj = new ReturnJson();
		try {
			List<Record> list = Db.find("select id,nick_id,mobile,nick_name,sex from user_info where id in ("+ids+")");
			rj.setBody(list);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败！");
		}finally{
			renderJson(rj);
		}
	}
	
	/**
	 * 获得教练详情
	 */
	public void getCoachById(){
		Integer id = getInt("id");
		ReturnJson rj = new ReturnJson();
		try {
			List<Record> rds = Db.find("select d.*,(select COUNT(*) from rela_praise "
					+ "where user_praise_id !='' and dynamic_id = d.id) as c "
					+ "from user_info u "
					+ "left join dynamic_info d on u.id = d.user_id "
					+ "where u.id="+id+" and d.is_able = 1");
			rj.setBody(rds);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败！");
		}finally{
			renderJson(rj);
		}
	}
	
	/**
	 * 我的收藏
	 */
	public void myCollect(){
		Integer id = getInt("id");
		ReturnJson rj = new ReturnJson();
		try {
			List<Record> rds = Db.find("select d.* from rela_praise r left join dynamic_info d on r.dynamic_id = d.id where user_collect_id = "+id+"");
			rj.setBody(rds);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败");
		}finally{
			renderJson(rj);
		}
	}
	
	@Test
	public void test1(){
		Cache bbsCache = Redis.use("bbs");
//		String a = bbsCache.
//		System.out.println(a);
	}
}
