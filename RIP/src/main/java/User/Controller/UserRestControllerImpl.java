package User.Controller;

import Category.Dao.CategoryRepoImpl;
import Category.Model.Category;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;

@Path("/User")
public class UserRestControllerImpl {

    private final UserService userService;
    ObjectMapper mapper;

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
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPreferredCategoriesToUser(/*JSONObject jsonObject*/) {
        //return Response.status(Response.Status.OK).entity(userService.addPreferredCategoriesToUser((Reader)jsonObject.get("reader"), (List)jsonObject.get("categories"))).build();

        //just for testing the JSONObject
        JSONObject jsonObject = new JSONObject();

        Reader reader = new Reader();
        reader.setUsername("username is Anton");
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("sex"));
        categories.add(new Category("drugs"));
        categories.add(new Category("alcohol"));

        jsonObject.put("message", userService.addPreferredCategoriesToUser(reader, categories));

        return Response.status(Response.Status.OK).entity(jsonObject).build();

//        jsonObject.put("name", reader.getUsername());
//        
//        
//        jsonObject.put("categories", categories);
//        
//        List<Category> c = (List)jsonObject.get("categories");
//        c.add(new Category("Ryan"));
//        
//        jsonObject.put("c", c);
//            return Response.status(Response.Status.OK).entity(jsonObject).build();
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {
        //  return Response.status(Response.Status.OK).entity("hello again").build();
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
        Map<String, Integer> topWriters = userService.topWriters();
        return Response.status(Response.Status.OK).entity(topWriters).build();

    }

    @Path("/writer/mostRejected")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topRejectedWritersForMonth() {
        Map<String, Integer> topRejectedWriters = userService.topRejectedWritersForMonth();
        return Response.status(Response.Status.OK).entity(topRejectedWriters).build();

    }

    @Path("/editor/mostApproving")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topApprovingEditors() {
        Map<String, Integer> topApprovingEditors = userService.topApprovingEditors();
        return Response.status(Response.Status.OK).entity(topApprovingEditors).build();
    }

    @Path("/searchWriter/{writerSearch}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response writerSearch(@PathParam("writerSearch") String writerSearch) {
        List<Writer> writers = new ArrayList<>();
        writers = userService.writerSearch(writerSearch);
        return Response.status(Response.Status.OK).entity(writers).build();
    }
}
