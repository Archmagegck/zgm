package com.pms.app.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	public File getFile(MultipartFile imgFile, String path, String folderName, String imgName) {
		String fileName = imgFile.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		ext = ext.toLowerCase();
		File file = null;
		
		List<String> fileTypes = new ArrayList<String>();
		fileTypes.add("jpg");
		fileTypes.add("jpeg");
		fileTypes.add("bmp");
		fileTypes.add("png");
		fileTypes.add("gif");

		if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
			imgName = imgName + "."+ext;
			file = this.creatFolder(path, folderName, imgName);
			try {
				imgFile.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public File creatFolder(String path, String folderName, String imgName) {
		File file = null;
		String separator = File.separator;
		File folder = new File(path+"images"+separator+folderName);
		if(folder.exists()){
			file = new File(folder,imgName);
		}else {
			folder.mkdirs();
			file = new File(folder,imgName);
		}
		return file;
	}
}
