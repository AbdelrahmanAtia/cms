package org.javaworld.cmsbackend.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.javaworld.cmsbackend.CmsBackEndApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	HttpServletResponse httpServletResponse;
	
	@Autowired
	CmsBackEndApplication cmsBackEndApplication;
	
	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public void getAttachment() throws IOException  {

		String[] segments = httpServletRequest.getRequestURL().toString().split("/");
		
		int len = segments.length;
		String attachmentName = segments[len - 1];
		String path = null;
		
		if(attachmentName.startsWith("_init_")) {
			//for initialization data
			path = resourceLoader.getResource("classpath:static/categories_images/" + attachmentName).getFile().getPath();
		} else {
			path = cmsBackEndApplication.getProjectFilesLocation()		
					+ File.separator + segments[len - 3]
					+ File.separator + segments[len - 2]
					+ File.separator + attachmentName;
		}
		
		logger.info("path = " + path);
		
		InputStream in = null;
		try {
			in = new FileInputStream(path);
			httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
			IOUtils.copy(in, httpServletResponse.getOutputStream());
		} finally {
			if (in != null)
				in.close();
		}
	}

}
