package org.javaworld.cmsbackend.logging;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.dao.AuditInfoRepository;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Entity
@Table(name = "AUDIT_INFO")
public class AuditInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String username;
	private Date requestTime;
	private Date responseTime;
	private String appTransaction;
	private String url;
	private String srcIp;
	private String targetIp;
	private String responseStatus;
	private String responseDesc;

	@Transient
	@Autowired
	HttpServletRequest request;

	@Transient
	@Autowired
	HttpServletResponse response;

	@Transient
	@Autowired
	AuditInfoRepository auditInfoRepository;

	public AuditInfo() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getAppTransaction() {
		return appTransaction;
	}

	public void setAppTransaction(String appTransaction) {
		this.appTransaction = appTransaction;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getTargetIp() {
		return targetIp;
	}

	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void init() {
		setUsername(getCurrentUserName());
		setAppTransaction(MDC.get("transactionId"));
		setRequestTime(new Date());
		setUrl(request.getRequestURI());
		setSrcIp(request.getRemoteAddr() + ":" + request.getRemotePort());
		setTargetIp(request.getLocalAddr() + ":" + request.getLocalPort());
	}

	public void persist() {
		setResponseTime(new Date());
		setResponseStatus("not yet");
		setResponseDesc("not yet");
		// save to database
		auditInfoRepository.save(this);
	}

	private String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "";
		if (authentication != null) {
			username = authentication.getName();
		}
		return username;
	}

}
