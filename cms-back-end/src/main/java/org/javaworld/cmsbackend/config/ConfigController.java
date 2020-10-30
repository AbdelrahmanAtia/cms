package org.javaworld.cmsbackend.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.javaworld.cmsbackend.CmsBackEndApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configs")
public class ConfigController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	private CmsBackEndApplication cmsBackEndApplication;
	
	/*
	@GetMapping("/logs/{transactionId}")
	public String getLogs(@PathVariable String transactionId) throws FileNotFoundException {
		String logsFilePath = cmsBackEndApplication.getLogsFilesPath() + File.separator + "project.log";
		logger.info("logsFilePath = " + logsFilePath);
		File f = new File(logsFilePath);
		Scanner in = new Scanner(f);
		StringBuilder logs = new StringBuilder();
		int linesCount = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (line.contains(transactionId)) {
				logs.append(line + System.lineSeparator());
				linesCount++;
			}
		}
		logger.info("linesCount = " + linesCount);
		in.close();
		return logs.toString();
	}
	*/
	
	//using streams
	@GetMapping("/logs/{transactionId}")
	public String getLogs_v2(@PathVariable String transactionId) throws IOException {
		String logsFilePath = cmsBackEndApplication.getLogsFilesPath() + File.separator + "project.log";
		Path path = Paths.get(logsFilePath);
		try(Stream<String> lines = Files.lines(path)){
			return lines.filter((line) -> {
				return line.contains(transactionId);
			}).collect(Collectors.joining(System.lineSeparator()));
		}	
	}
	
}
