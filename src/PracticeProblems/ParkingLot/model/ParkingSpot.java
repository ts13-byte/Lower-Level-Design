package PracticeProblems.ParkingLot.model;

public class ParkingSpot {
   private String id;
   private ParkingSpotType parkingSpotType;
   private Ticket ticket; // nullable

   public ParkingSpot(String id, ParkingSpotType parkingSpotType) {
      this.id = id;
      this.parkingSpotType = parkingSpotType;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public ParkingSpotType getParkingSpotType() {
      return parkingSpotType;
   }

   public void setParkingSpotType(ParkingSpotType parkingSpotType) {
      this.parkingSpotType = parkingSpotType;
   }

   public Ticket getTicket() {
      return ticket;
   }

   public void setTicket(Ticket ticket) {
      this.ticket = ticket;
   }

   public boolean canFit(Vehicle vehicle) {
      return !isOccupied() && vehicle.getType() == parkingSpotType.getCompatibleVehicleType();
   }

   public boolean isOccupied() {
      return ticket != null;
   }

   public void occupy(Ticket ticket) {
      this.ticket = ticket;
   }

   // atomic check - only one thread can occupy a parking spot.
   public synchronized boolean tryOccupy(Vehicle vehicle , Ticket ticket) {
      if(!canFit(vehicle)) {
         return false;
      }

      this.ticket = ticket;
      return true;
   }

   public void vacate() {
      this.ticket = null;
   }
}
