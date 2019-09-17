package eu.wiegandt.openworkshoporganizer;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.UUID;

import static eu.wiegandt.openworkshoporganizer.OpeningTimes.findById;
import static eu.wiegandt.openworkshoporganizer.OpeningTimes.listAll;

@Path("/service/events/openings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OpeningsResource {

  @POST
  @Transactional
  public OpeningTimes save(OpeningTimes openingTimes) {
      openingTimes.persistAndFlush();
    return openingTimes;
  }

    @PUT
    @Transactional
    public OpeningTimes change(OpeningTimes openingTimes) {
        OpeningTimes persistedOpeningTimes = getById(openingTimes.getId());
        persistedOpeningTimes.merge(openingTimes);
        return persistedOpeningTimes;
    }

    @GET
    @Path("/{id}")
    public OpeningTimes getById(@PathParam("id") UUID id) {
        return findById(id);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void delete(@PathParam("id") UUID id) {
        findById(id).delete();
    }

  @GET
  public Collection<OpeningTimes> getAll() {
      return listAll();
  }
}
