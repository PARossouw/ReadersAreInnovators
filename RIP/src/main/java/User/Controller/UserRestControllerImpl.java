package User.Controller;

import Category.Dao.CategoryRepoImpl;
import Category.Model.Category;
import User.Dao.UserRepoImpl;
import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import User.Service.UserService;
import User.Service.UserServiceImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/User")
public class UserRestControllerImpl{

    private final UserService userService;

    public UserRestControllerImpl() {
        this.userService = new UserServiceImpl(new UserRepoImpl(), new CategoryRepoImpl());
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        return Response.status(Response.Status.OK).entity(userService.login(user)).build();
    }

    @Path("/Categories/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPreferredCategoriesToUser(Reader reader, List<Category> categories) {
        return Response.status(Response.Status.OK).entity(userService.addPreferredCategoriesToUser(reader, categories)).build();
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
        Map<Writer, Integer> topWriters = userService.topWriters();
        return Response.status(Response.Status.OK).entity(topWriters).build();
    }

    @Path("/writer/mostRejected")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topRejectedWritersForMonth() {
        Map<Writer, Integer> topRejectedWriters = userService.topRejectedWritersForMonth();
        return Response.status(Response.Status.OK).entity(topRejectedWriters).build();
    }

    @Path("/editor/mostApproving")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topApprovingEditors() {
        Map<Writer, Integer> topApprovingEditors = userService.topApprovingEditors();
        return Response.status(Response.Status.OK).entity(topApprovingEditors).build();
    }

}
