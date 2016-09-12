package org.nbme.vca.users.repo;

import org.nbme.vca.users.model.VcaUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This repo will interface with the VcaUser data. 
 * @author jreim
 *
 */
public interface VcaUserRepo extends CrudRepository<VcaUser, Long> {
	
	public VcaUser findOne(Long id);
	public List<VcaUser> findAll();
	public List<VcaUser> findByActiveTrue();
	public VcaUser findByUName(String uName);
	public VcaUser findByEmail(String getuName);
	
}

