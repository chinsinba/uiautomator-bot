package in.BBAT.abstrakt.gui.model;

import java.util.ArrayList;
import java.util.List;

import in.BBAT.data.model.Entities.RoleEntity;
import in.BBAT.data.model.Entities.UserEntity;

public class UserModel {

	private UserEntity user;

	public UserModel(UserEntity userEntity){
		this.user =userEntity;
	}

	public UserModel(){
		this.user = new UserEntity();
	}

	public String getName(){
		return user.getUserName();
	}

	public void setName(String userName){
		user.setUserName(userName);
	}

	public void setPassword(String passWord){
		user.setPassword(passWord);
	}

	public String getPassWord(){
		return user.getPassword();
	}

	public List<RoleModel> getRoles(){ 
		List<RoleModel> roles = new ArrayList<RoleModel>();
		for(RoleEntity role :user.getRoles() ){
			RoleModel model = new RoleModel(role);
			roles.add(model);
		}
		return roles;
	}
}
