package com.pms.app.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtils {

	private final static Logger logger = LoggerFactory.getLogger(UploadUtils.class);

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

	@SuppressWarnings("unchecked")
	public static String uploadImg(MultipartFile imgfile, String savePath, String saveUrl) {
		try {
			String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };
			if (imgfile != null) {
				String fileName = imgfile.getOriginalFilename();
				File uploadDir = new File(savePath);
				if (!uploadDir.isDirectory()) {
					uploadDir.mkdirs();
				}
				if (!uploadDir.canWrite()) {
					String error = UploadError.getError("上传目录没有写权限。");
					return error;
				}
				File dirFile = new File(savePath);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(fileTypes).contains(fileExt)) {
					String error = UploadError.getError("上传文件扩展名是不允许的扩展名");
					return error;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				File uploadedFile = new File(savePath, newFileName);
				imgfile.transferTo(uploadedFile);
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				return obj.toJSONString();
			} else {
				String error = UploadError.getError("请选择文件。");
				return error;
			}
		} catch (Exception e) {
			logger.error("上传文件发现未知错误.", e);
			String error = UploadError.getError("上传文件失败。");
			return error;
		}
	}
}
