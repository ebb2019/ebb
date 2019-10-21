package com.ebb.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ebb.common.Code;
import com.ebb.model.dto.ReturnJson;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

public class AppMineController extends Controller {
	private static Logger log = Logger.getLogger(AppUserController.class);
	
	/**
	 * 获取钱包余额
	 */
	@Clear
	public void getWallet(){
		//获取当前用户id
		Integer userId = getInt("id");
		
		ReturnJson returnJson = new ReturnJson();
		
		try {
			Record find = Db.findFirst("select uw.balance from user_info ui left join user_wallet uw on ui.wallet_id = uw.id where ui.id = "+userId);
			
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
	
	/**
	 * 钱包余额提现
	 */
	@Clear
	public void walletWithDrawal(){
		//获取当前用户id
		Integer userId = getInt("userId");
		//获取当前所选银行卡id
		Integer bankCardId = getInt("bankCardId");
		//获取要提现的金额
		BigDecimal amount = new BigDecimal(get("amount"));
		//获取支付密码
		String payPassword = get("payPassword");
		
		
		Record record = new Record();
		Record dealRecord = new Record();
		ReturnJson returnJson = new ReturnJson();
		
		record.set("user_id", userId);
		record.set("bankCard_Id", bankCardId);
		record.set("amount", amount);
		
		dealRecord.set("user_id", userId);
		dealRecord.set("amount", amount);
		dealRecord.set("create_time", new Date());
		dealRecord.set("update_time", new Date());
		// status  1:交易进行中   10:交易完成
		dealRecord.set("status", 10);
		// type    1:提现操作    2:充值操作
		dealRecord.set("type", 1);
		
		try {
			Record userRecord = Db.findFirst("select pay_password from user_info where id = "+userId);
			//判断支付密码是否正确
			if (userRecord.get("pay_password").equals(payPassword)) {
				Record findFirst = Db.findFirst("select uw.id,uw.balance from user_info ui left join user_wallet uw on ui.wallet_id = uw.id where ui.id = "
								+ userId);
				Integer walletId = findFirst.getInt("id");
				BigDecimal balance = new BigDecimal(findFirst.getStr("balance"));
				//余额大于等于提现金额
				if (balance.compareTo(amount) == 0 || balance.compareTo(amount) == 1) {
					BigDecimal subtract = balance.subtract(amount);

					Db.update("update user_wallet set balance = " + subtract
							+ " where id = " + walletId);
					
					//记录流水
					Db.save("deal_flow", dealRecord);

					returnJson.setBody(record);
					returnJson.setCode(Code.SUCCESS);
					returnJson.setMessage("提现成功");
				} else {
					returnJson.setBody(record);
					returnJson.setCode(Code.FAIL);
					returnJson.setMessage("余额不足");
				}
			}else{
				returnJson.setCode(Code.FAIL);
				returnJson.setMessage("支付密码错误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("提现异常");
		}finally{
			renderJson(returnJson);
		}
		
	}
	

	/**
	 * 获取当前用户银行卡信息
	 */
	@Clear
	public void getBankCard(){
		ReturnJson returnJson = new ReturnJson();
		//获取当前用户id
		Integer userId = getInt("id");
		
		try {
			List<Record> find = Db.find("select * from bank_certify where user_id = "+userId);
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
	
	
	/**
	 * 添加银行卡
	 */
	@Clear
	public void addBankCard(){
		//获取当前用户id
		Integer userId = getInt("id");
		//获取支付密码
		String payPassword = get("payPassword");
		//获取姓名
		String realName = get("realName");
		//身份证号
		String identity = get("identity");
		//银行卡号
		String cardNum = get("cardNum");
		//开户行
		String openBank = get("openBank");
		//开户行地址
		String bankAddress = get("bankAddress");
		//手机号
		String mobile = get("mobile");
		//验证码
		String code = get("code");
		
		ReturnJson returnJson = new ReturnJson();
		Record record = new Record();
		
		record.set("user_id", userId);
		record.set("real_name", realName);
		record.set("identity", identity);
		record.set("card_num", cardNum);
		record.set("open_bank", openBank);
		record.set("bank_address", bankAddress);
		record.set("mobile", mobile);
		//关于验证码的判断?
		Cache bbsCache = Redis.use("bbs");
		bbsCache.setex(code,360,mobile);
		
		try {
			//查询当前用户的所有银行卡
			List<Record> bankCardList = Db.find("select * from bank_certify where user_id = "+userId);
			//若未曾绑定银行卡，则保存用户支付密码
			if(bankCardList.isEmpty()){
				//保存用户支付密码
				Db.update("update user_info set pay_password = "+payPassword+" where id = "+userId);
			}
			
			//保存用户绑定银行卡的信息
			Db.save("bank_certify", record);
			
			//返回当前用户所有已绑定的银行卡
			List<Record> find = Db.find("select * from bank_certify where user_id = "+userId);
			
			returnJson.setBody(find);
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
	 * 查询当前用户的所有流水(按时间倒序)
	 */
	@Clear
	public void getUserAllFlow(){
		//获取用户id
		Integer userId = getInt("id");
		
		ReturnJson returnJson = new ReturnJson();
		
		try {
			List<Record> find = Db.find("select * from deal_flow where user_id = "+userId+" order by update_time desc");
			
			returnJson.setBody(find);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取失败");
		}finally{
			renderJson(returnJson);
		}
	}
	/**
	 * 我的-动态    获得个人详情
	 */
	public void getUserById(){
		//获取当前用户id
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
		}
	}
	
	
}
