package com.newland.beecode.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.exception.ExcelException;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.PartnerService;
@Service("checkService")
public class CheckServiceImpl implements CheckService{
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private FileService fileService;
	@Autowired
	private PartnerService partnerService;
	
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 }
	public static boolean isIntegerOrFloat(String str){
		Pattern pattern = Pattern.compile("([0-9]+)|([0-9]+.[0-9]+)");	
		return pattern.matcher(str).matches(); 
	}
	@Override
	public String checkCustomerFile(MultipartFile file, String fileName,Customer customer,String bizNo)throws ExcelException, AppException {
		StringBuffer tempError = new StringBuffer();
		try {
			this.fileService.storeFile(file,fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("customer");
			if(sheet==null){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_SHEET_NOT_FIND, null, Locale.CHINA));
			}
			if(sheet.getLastRowNum()<1){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_BLANK, null, Locale.CHINA));
			}
			if(customer!=null){
				customer.setCount(sheet.getLastRowNum());
			}
			HSSFRow row;
			String nameError;
			String mobileError;
			String accountError;
			String amountError;
			int errorCount=0;
			ArrayList<String> mobileList = new ArrayList<String>();
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				row = sheet.getRow(j);
				HSSFCell name = row.getCell((short) i++);
				nameError="";
				if(name==null || name.toString().equals("")){
					nameError=messageSource.getMessage(ErrorsCode.BIZ_COUSTMER_NAME_NULL, null, Locale.CHINA);
				}
				HSSFCell mobile = row.getCell((short) i++);
				mobileError="";
				if(mobile==null || mobile.toString().equals("")||mobile.toString().length()!=11 ||!isNumeric(mobile.toString())){
					mobileError=messageSource.getMessage(ErrorsCode.BIZ_COUSTMER_MOBILE_ERROR, null, Locale.CHINA);
				}
				accountError="";
				HSSFCell account = row.getCell((short) i++);
				if(account!=null && !account.toString().equals("")){
					if(!isNumeric(account.toString())){
						accountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_ACCOUNT_NOT_NUMBER, null, Locale.CHINA);
					}else if((account.toString().length()!=16 && account.toString().length()!=19)){
						accountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_ACCOUNT_LENGTH_ERROR, null, Locale.CHINA);
					}
				}
				amountError="";
				if(bizNo!=null && bizNo.equals(Coupon.BIZ_TYPE_VOUCHER)){				
					HSSFCell amount = row.getCell((short) i++);
							
					if(amount==null || amount.toString().equals("")){
						amountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_AMOUNT_NULL, null, Locale.CHINA);						
					}else{
						if(!isIntegerOrFloat(amount.toString())){
							amountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_AMOUNT_NOT_NUMBER, null, Locale.CHINA);
						}
					}
				}
				if(nameError!="" || mobileError!="" ||accountError!="" || amountError!=""){
					errorCount++;
					tempError.append("<tr><td>" + (j+1) + "</td><td>");
					tempError.append(nameError);
					tempError.append("</td><td>");
					tempError.append(mobileError);
					tempError.append("</td><td>");
					tempError.append(accountError);
					tempError.append("</td><td>");
					tempError.append(amountError);
					tempError.append("</td></tr>");
				}
				if(errorCount>=20){
					tempError.append("<tr><td>..........</td><td>");
					tempError.append("..........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td></td></tr>");
					break;
				}
				if(nameError=="" && mobileError==""){
					/*if(mobileList.contains(mobile.toString())){
						
						errorCount++;
						tempError.append("<tr><td>" + (j+1) + "</td><td>");
						tempError.append(messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_EXITS, new Object[]{mobile.toString()}, Locale.CHINA));
						tempError.append("</td><td></td><td></td><td></td></tr>");
						//return messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_EXITS, new Object[]{mobile.toString()}, Locale.CHINA);
					}*/
					mobileList.add( mobile.toString());
				}
			}
			if(errorCount==0){
				return messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_PASS, new Object[]{sheet.getLastRowNum()}, Locale.CHINA);
				
			}
			throw new ExcelException("",tempError.toString());
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_UPLOAD_ERROR,"");
		}
	}
	@Override
	public List<Customer> getCustomer(String fileName)throws ExcelException,AppException {
		try {
			//this.checkCustomerFile(file, fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("customer");
			HSSFRow row;
			List<Customer> list=new ArrayList<Customer>();
			Customer customer;
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				customer=new Customer();
				row = sheet.getRow(j);
				HSSFCell name = row.getCell((short) i++);
				HSSFCell mobile = row.getCell((short) i++);
				HSSFCell account = row.getCell((short) i++);
				HSSFCell amount = row.getCell((short) i++);
				customer.setName(name.toString());
				customer.setMobile(mobile.toString());
				if(account==null ||account.toString().equals("")){
					customer.setAccount("****************");
				}else{
					customer.setAccount(account.toString());
				}
				if(amount==null ||amount.toString().equals("")){
					//customer.setAccount("****************");
				}else{
					BigDecimal val = new BigDecimal(amount.toString());
					customer.setAmount(val);
				}
				
				list.add(customer);
				
			}
			return list;
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_UPLOAD_ERROR,"");
		}
	}
	
	@Override
	@Transactional
	public Set<Partner> partnerCheck(MultipartFile file, String fileName)
			throws AppException {
		StringBuffer tempError = new StringBuffer();
		Set<Partner> partnerSet=new HashSet<Partner>();
		try {
			this.fileService.storeFile(file,fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("partner");
			HSSFRow row;
			String partnerNameError;
			String partnerNoError;
			long errorCount=0;
			if(sheet==null){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_PARTNER_SHEET_NOT_FOUND, null, Locale.CHINA));
			}
			if(sheet.getLastRowNum()<1){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_BLANK, null, Locale.CHINA));
			}
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				row = sheet.getRow(j);
				HSSFCell name = row.getCell((short) i++);
				partnerNameError="";
				if(name==null || name.toString().equals("")){
					partnerNameError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_NAME_NULL, null, Locale.CHINA);
				}
				HSSFCell partnerNo = row.getCell((short) i++);
				partnerNoError="";
				if(partnerNo==null || partnerNo.toString().equals("")){
					partnerNoError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_PARTNERNO_ERROR, null, Locale.CHINA);
				}
				Partner partner=this.partnerService.findByPartnerNo(partnerNo.toString());
				if(partner==null){
					partnerNoError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_NOT_EXITS, null, Locale.CHINA);
				}else{
					partnerSet.add(partner);
				}
				if(partnerNoError!="" || partnerNameError!=""){
					errorCount++;
					tempError.append("<tr><td>" + j + "</td><td>");
					tempError.append(partnerNameError);
					tempError.append("</td><td>");
					tempError.append(partnerNoError);
					tempError.append("</td></tr>");
				}
				if(errorCount>20){
					tempError.append("<tr><td>" + j + "</td><td>");
					tempError.append("..........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td></tr>");
					break;
				}
				
				tempError.append("</td></tr>");
			}
			if(errorCount==0){
				//return messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_PASS, new Object[]{sheet.getLastRowNum()}, Locale.CHINA);
				return partnerSet;
			}
			throw new ExcelException("",tempError.toString());
			
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_PARTNER_EXCEL_FILE_UPLOAD_ERROR,"");
		}
	}
	@Override
	public List<Coupon> getCouponsByRespStatus(MultipartFile file, Integer type)
			throws AppException {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet=null;
			String sheetName=null;
			if(type.equals(SendList.MS_TYPE_MMS)){
				sheetName=SendList.MMS_RESP_FILE_NAME;
			}else{
				sheetName=SendList.SMS_RESP_FILE_NAME;
			}
			sheet=wb.getSheet(sheetName);
			if(sheet==null){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_SHEET_NOT_FIND, new Object[]{sheetName}, Locale.CHINA));
			}
			HSSFRow row;
			List<Coupon> coupons=new ArrayList<Coupon>();
			Coupon coupon =null;
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				coupon =new Coupon();
				row = sheet.getRow(j);
				HSSFCell couponId = row.getCell((short) i++);
				coupon.setCouponId(new Long(couponId.toString()));
				HSSFCell status = row.getCell((short) i++);
				coupon.setMmsStatus(new Integer(status.toString()));
				coupon.setSmsStatus(new Integer(status.toString()));
				HSSFCell statusDesc = row.getCell((short) i++);
				coupon.setMmsDesc(statusDesc.toString());
				coupon.setSmsDesc(statusDesc.toString());
				coupons.add(coupon);
				
			}
			return coupons;
		} catch (RuntimeException e) {
			throw new AppException(ErrorsCode.BIZ_RESP_STATUS_ERROR,"",e);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_RESP_STATUS_ERROR,"",e);
		}
	}
	@Override
	public void checkCodeCheck(MarketingAct act) throws AppException {
		if(act.getBindCard().equals(MarketingAct.BIND_CARD_NO)){
			return ;
		}
		if(act.getCheckCode()==null ||act.getCheckCode().trim().equals("")){
			throw new AppException(ErrorsCode.BIZ_MARKETINGACT_CHECK_CODE_NOT_NULL,"");
		}
		String[] strs=act.getCheckCode().split(MarketingAct.NEW_LINE);
		StringBuffer sb=new StringBuffer();
		Pattern pattern = Pattern.compile("[0-9[*]]{6}"); 
		Pattern pattern_ = Pattern.compile("[0-9]+[*]*");
		int i=0;
		for(String bin:strs){
			if(!pattern.matcher(bin).matches()){
				throw new AppException(ErrorsCode.BIZ_MARKETINGACT_CHECK_CODE_FORMAT_ERROR,"");
			}
			if(!pattern_.matcher(bin).matches()){
				throw new AppException(ErrorsCode.BIZ_MARKETINGACT_CHECK_CODE_ORDER_ERROR,"");
			}
			if(i==strs.length-1){
				sb.append(bin);
			}else{
				sb.append(bin+MarketingAct.CHECK_CODE_REGEX);
			}
			i++;
			
		}
		//act.setCheckCode(sb.toString());
		
	}

}
