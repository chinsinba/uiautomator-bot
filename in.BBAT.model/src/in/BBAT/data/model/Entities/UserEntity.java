package in.BBAT.data.model.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.TableGenerator;

/**
 * 
 * @author Syed Mehtab
 *
 */
@Entity
public class UserEntity implements IBBATEntity {

	@Id
	@TableGenerator(name = "User_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "User_GEN")
	private int id;

	private String userName;

	private String password;

	private Timestamp createdOn;

	private Timestamp recentLogin;

	@OneToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@OrderColumn
	private List<RoleEntity> roles;


	@Override
	public List<IBBATEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public IBBATEntity getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getRecentLogin() {
		return recentLogin;
	}

	public void setRecentLogin(Timestamp recentLogin) {
		this.recentLogin = recentLogin;
	}

}
