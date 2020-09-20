package ca.ulaval.glo4002.reservation.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.reservation.builder.CreateReservationRequestDtoBuilder;
import ca.ulaval.glo4002.reservation.api.reservation.dto.CreateReservationRequestDto;
import ca.ulaval.glo4002.reservation.service.validator.table.TableValidator;

@ExtendWith(MockitoExtension.class)
class ReservationValidatorTest {
  @Mock
  private DinnerDateValidator dinnerDateValidator;

  @Mock
  private ReservationDateValidator reservationDateValidator;

  @Mock
  private TableValidator tableValidator;

  private ReservationValidator reservationValidator;

  @BeforeEach
  public void setUp() {
    reservationValidator = new ReservationValidator(dinnerDateValidator,
                                                    reservationDateValidator,
                                                    tableValidator);
  }

  @Test
  public void givenValidRequest_whenValidate_thenDoNotThrow() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().withAnyTable()
                                                                                                      .build();
    doNothing().when(tableValidator).validateTables(createReservationRequestDto.getTables());

    // when
    Executable validatingReservation = () -> reservationValidator.validate(createReservationRequestDto);

    // then
    assertDoesNotThrow(validatingReservation);
  }

  @Test
  public void givenAValidRequest_whenValidate_thenDinnerDateIsValidated() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().build();

    // when
    reservationValidator.validate(createReservationRequestDto);

    // then
    verify(dinnerDateValidator).validate(createReservationRequestDto.getDinnerDate());
  }

  @Test
  public void givenAValidRequest_whenValidate_thenReservationDateIsValidated() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().build();

    // when
    reservationValidator.validate(createReservationRequestDto);

    // then
    verify(reservationDateValidator).validate(createReservationRequestDto.getReservationDetails()
                                                                         .getReservationDate());
  }

  @Test
  public void givenAValidRequest_whenValidate_thenTablesAreValidated() {
    // given
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDtoBuilder().build();

    // when
    reservationValidator.validate(createReservationRequestDto);

    // then
    verify(tableValidator).validateTables(createReservationRequestDto.getTables());
  }
}