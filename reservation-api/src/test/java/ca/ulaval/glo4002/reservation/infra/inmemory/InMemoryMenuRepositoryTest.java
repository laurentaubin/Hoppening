package ca.ulaval.glo4002.reservation.infra.inmemory;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class InMemoryMenuRepositoryTest {
  private static final RestrictionType ALLERGIES_RESTRICTION = RestrictionType.ALLERGIES;
  private static final RestrictionType NONE_RESTRICTION = RestrictionType.NONE;
  private static final IngredientName CARROTS_INGREDIENT = IngredientName.CARROTS;

  private final FullCourseFactory fullCourseFactory = new FullCourseFactory(new CourseRecipeFactory());

  private InMemoryMenuRepository menuRepository;

  @BeforeEach
  public void setUp() {
    menuRepository = new InMemoryMenuRepository(fullCourseFactory);
  }

  @Test
  public void whenGetIngredientsQuantity_thenReturnReturnCorrespondingIngredientInformation() {
    // when
    Map<IngredientName, BigDecimal> ingredientsQuantity = menuRepository.getIngredientsQuantityByRestrictionType(ALLERGIES_RESTRICTION);

    // then
    assertThat(ingredientsQuantity).isNotEmpty();
  }

  @Test
  public void givenCarrotsIngredient_whenGetRestrictionTypesByIngredient_thenReturnRestrictionTypeNone() {
    // when
    List<RestrictionType> restrictionTypes = menuRepository.getRestrictionTypesByIngredient(CARROTS_INGREDIENT);

    // then
    assertThat(restrictionTypes).contains(NONE_RESTRICTION);
  }

}
