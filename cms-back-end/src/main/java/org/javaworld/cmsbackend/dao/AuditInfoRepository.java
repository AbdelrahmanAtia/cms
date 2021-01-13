package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.logging.AuditInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditInfoRepository extends JpaRepository<AuditInfo, Long>{

}
