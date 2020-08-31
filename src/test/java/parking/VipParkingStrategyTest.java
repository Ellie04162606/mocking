package parking;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class VipParkingStrategyTest {

  @InjectMocks
  VipParkingStrategy vipParkingStrategy;

  @Test
  public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

    /* Exercise 4, Write a test case on VipParkingStrategy.park()
     * With using Mockito spy, verify and doReturn */
    ParkingLot parkingLot = spy(new ParkingLot("south parking", 1));
    VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
    List<ParkingLot> parkingLots = new ArrayList<>();
    List<Car> cars = new ArrayList<>();
    parkingLots.add(parkingLot);
    Car car = new Car("A-BMW");
    cars.add(car);

    when(parkingLot.getParkedCars()).thenReturn(cars);
    doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);
    Receipt receipt = vipParkingStrategy.park(parkingLots, car);

    assertEquals("south parking", receipt.getParkingLotName());
  }

  @Test
  public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

    /* Exercise 4, Write a test case on VipParkingStrategy.park()
     * With using Mockito spy, verify and doReturn */
    ParkingLot parkingLot = spy(new ParkingLot("south parking", 1));
    VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
    List<ParkingLot> parkingLots = new ArrayList<>();
    List<Car> cars = new ArrayList<>();
    parkingLots.add(parkingLot);
    Car car = new Car("A-BMW");
    cars.add(car);

    when(parkingLot.getParkedCars()).thenReturn(cars);
    Receipt receipt = vipParkingStrategy.park(parkingLots, car);

    assertEquals("No Parking Lot", receipt.getParkingLotName());
  }

  @Test
  public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue() {

    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
    Car car = new Car("A-BMW");
    CarDao carDao = mock(CarDaoImpl.class);
    when(carDao.isVip(car.getName())).thenReturn(true);

    boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);

    assertEquals(true, allowOverPark);


  }

  @Test
  public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse() {

    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
  }

  @Test
  public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
  }

  @Test
  public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
    /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
     * You may refactor the code, or try to use
     * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
     */
  }

  private Car createMockCar(String carName) {
    Car car = mock(Car.class);
    when(car.getName()).thenReturn(carName);
    return car;
  }
}
