package in.BBAT.abstrakt.gui.model;

import in.BBAT.data.model.Entities.RoleEntity;

public class RoleModel {

	private RoleEntity roleEntity;
	
	public RoleModel(RoleEntity role) {
		this.roleEntity = role;
	}

	public String getRoleName(){
		return roleEntity.getRoleName();
	}

	public String getDescription(){
		return roleEntity.getDescription();
	}
}
