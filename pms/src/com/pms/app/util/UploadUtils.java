package com.pms.app.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pms.base.service.ServiceException;

public class UploadUtils {

	static String separator = File.separator;
	static List<String> fileTypes = new ArrayList<String>();
	static  {
		fileTypes.add("jpg");
		fileTypes.add("jpeg");
		fileTypes.add("bmp");
		fileTypes.add("png");
		fileTypes.add("gif");
	}
	
	
	public static String uploadFile(HttpServletRequest request, Integer type, String supervisionCustomerCode, String code) throws ServiceException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgfile = multipartRequest.getFile("picfile");
		String fileName = imgfile.getOriginalFilename();
		String imgName = UUID.randomUUID().toString();
		String typeName = "未知";
		switch (type) {
		case 1:
			typeName = "入库";
			break;
		case 2:
			typeName = "出库";
			break;
		}
		String folderName = "attached" + separator + supervisionCustomerCode + separator + typeName + separator + code;
		String path = request.getSession().getServletContext().getRealPath(separator);
		if (!(imgfile.getOriginalFilename() == null || "".equals(imgfile.getOriginalFilename()))) {
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
			if (!fileTypes.contains(ext)) {
				throw new ServiceException("上传文件不是允许的类型！");
			} else {// 如果扩展名属于允许上传的类型，则创建文件
				File folder = new File(path + "images" + separator + folderName);
				if(!folder.exists())
					folder.mkdirs();
				imgName = imgName + "." + ext;
				File file = new File(folder, imgName);
				try {
					imgfile.transferTo(file);
				} catch (Exception e) {
					throw new ServiceException("上传文件发生未知异常.", e);
				}
				String picUrl = folderName + separator + imgName;
				return picUrl;
			}
		}
		return null;
	}
	
	
	public static String uploadTempFile(HttpServletRequest request) throws ServiceException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgfile = multipartRequest.getFile("picfile");
		String fileName = imgfile.getOriginalFilename();
		String imgName = UUID.randomUUID().toString();
		String folderName = "attached" + separator + "temp";
		String path = request.getSession().getServletContext().getRealPath(separator);
		if (!(imgfile.getOriginalFilename() == null || "".equals(imgfile.getOriginalFilename()))) {
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
			if (!fileTypes.contains(ext)) {
				throw new ServiceException("上传文件不是允许的类型！");
			} else {// 如果扩展名属于允许上传的类型，则创建文件
				File folder = new File(path + "images" + separator + folderName);
				imgName = imgName + "." + ext;
				File file = new File(folder, imgName);
				try {
					imgfile.transferTo(file);
				} catch (Exception e) {
					throw new ServiceException("上传文件发生未知异常.", e);
				}
				String picUrl = folderName + separator + imgName;
				return picUrl;
			}
		}
		return null;
	}
	
	public static String uploadPickFile(HttpServletRequest request, String tempPath, String outsRecordCode, String supervisionCustomerCode) throws Exception {
		String ext = tempPath.substring(tempPath.lastIndexOf(".") + 1, tempPath.length()).toLowerCase();
		
		String path = request.getSession().getServletContext().getRealPath(separator);
		File tempFile = new File(path + "images" + separator + tempPath);
		
		String toFilePath = "attached" + separator + supervisionCustomerCode + separator + "出库" + separator + outsRecordCode;
		File toFolder = new File(path + "images" + separator + toFilePath);
		if(!toFolder.exists())
			toFolder.mkdirs();
		File toFile = new File(toFolder, "提货通知书." + ext);
		
		FileUtils.copyFile(tempFile, toFile);
		tempFile.delete();
		
		return toFilePath + "提货通知书." + ext;
	}
	

	public static String uploadIndexPic(HttpServletRequest request, String folderName, Integer type) {
		folderName = "attached/" + folderName + "/index/" + type;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgfile = multipartRequest.getFile("picfile");
		String fileName = imgfile.getOriginalFilename();
		String separator = File.separator;
		String path = request.getSession().getServletContext().getRealPath(separator);
		String imgName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		if (!(imgfile.getOriginalFilename() == null || "".equals(imgfile.getOriginalFilename()))) {
			File file = new FileUpload().getFile(imgfile, path, folderName, imgName);
			if(file == null) return null;
			String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			return folderName + "/" + imgName + ext;
		}
		return null;
	}

}
