package ca.ulaval.glo4002.reservation.api.reservation;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CreateReservationRequestDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDto;
import ca.ulaval.glo4002.reservation.api.reservation.validator.DateFormatValidator;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationId;
import ca.ulaval.glo4002.reservation.service.reservation.RestaurantService;

@Path("/reservations")
public class ReservationResource {

  private final RestaurantService restaurantService;
  private final DateFormatValidator dateFormatValidator;

  public ReservationResource(RestaurantService restaurantService,
                             DateFormatValidator dateFormatValidator)
  {
    this.restaurantService = restaurantService;
    this.dateFormatValidator = dateFormatValidator;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createReservation(@Valid CreateReservationRequestDto createReservationRequestDto) {
    dateFormatValidator.validateFormat(createReservationRequestDto.getDinnerDate());
    dateFormatValidator.validateFormat(createReservationRequestDto.getReservationDetails()
                                                                  .getReservationDate());
    ReservationId reservationId = restaurantService.makeReservation(createReservationRequestDto);
    URI reservationLocation = URI.create(String.format("/reservations/%s",
                                                       reservationId.getLongId()));
    return Response.created(reservationLocation).build();
  }

  @GET
  @Path("/{reservationId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getReservation(@PathParam("reservationId") long id) {
    ReservationId reservationId = new ReservationId(id);
    ReservationDto reservationDto = restaurantService.getReservationFromRestaurant(reservationId);
    return Response.ok().entity(reservationDto).build();
  }
}
