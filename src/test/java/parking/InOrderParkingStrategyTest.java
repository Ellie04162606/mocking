package parking;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@PrepareForTest({ParkingLot.class})
public class InOrderParkingStrategyTest {

  private static InOrderParkingStrategy inOrderParkingStrategy;

  @Test
  public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
     * With using Mockito to mock the input parameter */
    ParkingLot parkingLot = mock(ParkingLot.class);
    when(parkingLot.getName()).thenReturn("south parking");
    Car car = mock(Car.class);
    when(car.getName()).thenReturn("BMW");

    inOrderParkingStrategy = new InOrderParkingStrategy();
    Receipt result = inOrderParkingStrategy.createReceipt(parkingLot, car);

    assertEquals("south parking", result.getParkingLotName());
    assertEquals("BMW", result.getCarName());


  }

  @Test
  public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

    /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
     * With using Mockito to mock the input parameter */
    Car car = mock(Car.class);
    when(car.getName()).thenReturn("BMW");

    inOrderParkingStrategy = new InOrderParkingStrategy();
    Receipt result = inOrderParkingStrategy.createNoSpaceReceipt(car);

    assertEquals("BMW", result.getCarName());
    assertEquals("No Parking Lot", result.getParkingLotName());
  }

  @Test
  public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt() {

    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
    ParkingLot parkingLot = Mockito.spy(new ParkingLot("south parking", 1));
    List<ParkingLot> parkingLots = new ArrayList<>();
    List<Car> cars = new ArrayList<>();
    parkingLots.add(parkingLot);
    Car car = new Car("BMW");
    cars.add(car);
    inOrderParkingStrategy = new InOrderParkingStrategy();

    when(parkingLot.getParkedCars()).thenReturn(cars);
    Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

    verify(parkingLot, times(1)).isFull();
    assertEquals("No Parking Lot", receipt.getParkingLotName());
  }

  @Test
  public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt() {

    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
    ParkingLot parkingLot = Mockito.spy(new ParkingLot("south parking", 1));
    List<ParkingLot> parkingLots = new ArrayList<>();
    List<Car> cars = new ArrayList<>();
    parkingLots.add(parkingLot);
    Car car = new Car("BMW");
    cars.add(car);
    inOrderParkingStrategy = new InOrderParkingStrategy();

    Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

    verify(parkingLot, times(1)).isFull();
    assertEquals("south parking", receipt.getParkingLotName());
  }

  @Test
  public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt() {

    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
    ParkingLot parkingLot = Mockito.spy(new ParkingLot("south parking", 1));
    ParkingLot parkingLot1 = Mockito.spy(new ParkingLot("north parking", 2));
    List<ParkingLot> parkingLots = new ArrayList<>();
    List<Car> cars = new ArrayList<>();
    parkingLots.add(parkingLot);
    parkingLots.add(parkingLot1);

    Car car = new Car("BMW");
    cars.add(car);
    inOrderParkingStrategy = new InOrderParkingStrategy();

    when(parkingLot.getParkedCars()).thenReturn(cars);
    Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

    verify(parkingLot, times(1)).isFull();
    verify(parkingLot1, times(1)).isFull();
    assertEquals("north parking", receipt.getParkingLotName());
  }

  @Test
  public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot() {

    /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
    ParkingLot parkingLot = Mockito.spy(new ParkingLot("south parking", 1));
    ParkingLot parkingLot1 = Mockito.spy(new ParkingLot("north parking", 2));
    List<ParkingLot> parkingLots = new ArrayList<>();
    parkingLots.add(parkingLot);
    parkingLots.add(parkingLot1);
    Car car = new Car("BMW");
    inOrderParkingStrategy = new InOrderParkingStrategy();

    Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

    verify(parkingLot, times(1)).isFull();
    assertEquals("south parking", receipt.getParkingLotName());
  }
}
