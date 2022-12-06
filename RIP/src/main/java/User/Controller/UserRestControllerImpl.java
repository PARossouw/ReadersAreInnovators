package User.Controller;

import Category.Dao.CategoryRepoImpl;
import Story.Dao.StoryRepoImpl;
import User.Dao.UserRepoImpl;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import User.Service.UserService;
import User.Service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.json.simple.JSONObject;

@Path("/User")
public class UserRestControllerImpl {

    private final UserService userService;
    ObjectMapper mapper = new ObjectMapper();

    public UserRestControllerImpl() {
        this.userService = new UserServiceImpl(new UserRepoImpl(), new CategoryRepoImpl(), new StoryRepoImpl());
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        return Response.status(Response.Status.OK).entity(userService.login(user)).build();
    }

    @Path("/categories/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPreferredCategoriesToUser(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(userService.addPreferredCategoriesToUser(
                mapper.convertValue(jsonObject.get("reader"), Reader.class), 
                (List) jsonObject.get("categories"))).build();
    }

    @Path("/categories/preffered/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPreferredCategoriesToNewUser(Reader reader) {
        return Response.status(Response.Status.OK).entity(userService.addPreferredCategoriesToNewUser(reader)).build();
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {
        return Response.status(Response.Status.OK).entity(userService.registerUser(user)).build();
    }

    @Path("/writer/block")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response blockWriter(Writer writer) {
        return Response.status(Response.Status.OK).entity(userService.blockWriter(writer)).build();
    }

    @Path("/editor/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewEditor(Editor editor) {
        return Response.status(Response.Status.OK).entity(userService.addNewEditor(editor)).build();
    }

    @Path("/editor/remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeEditor(Editor editor) {
        return Response.status(Response.Status.OK).entity(userService.removeEditor(editor)).build();
    }

    @Path("/writer/top")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topWriters() {
        return Response.status(Response.Status.OK).entity(userService.topWriters()).build();

    }

    @Path("/writer/mostRejected")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topRejectedWritersForMonth() {
        return Response.status(Response.Status.OK).entity(userService.topRejectedWritersForMonth()).build();

    }

    @Path("/editor/mostApproving")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topApprovingEditors() {
        return Response.status(Response.Status.OK).entity(userService.topApprovingEditors()).build();
    }

    @Path("/searchWriter/{writerSearch}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response writerSearch(@PathParam("writerSearch") String writerSearch) {
        return Response.status(Response.Status.OK).entity(userService.writerSearch(writerSearch)).build();
    }

    @Path("/referFriend")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response referFriend(JSONObject jsonObject) {
        User user = mapper.convertValue(jsonObject.get("user"), User.class);
        String number = mapper.convertValue(jsonObject.get("phoneNumber"), String.class);
        return Response.status(Response.Status.OK).entity(userService.referFriend(user, number)).build();
    }
}
