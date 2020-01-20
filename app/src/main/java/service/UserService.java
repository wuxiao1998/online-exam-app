package service;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entity.UserInfo;

@Path("auth")

public interface UserService {
	@GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, UserInfo> queryAll();

	@POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserInfo addUser(UserInfo user, @QueryParam("requestid") int requestid);
	
	@POST
	@Path("/login") //访问localhost(换成服务器ip):8888/rest/auth/login
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public UserInfo login(Map<String,String> userInfo);
	

}
