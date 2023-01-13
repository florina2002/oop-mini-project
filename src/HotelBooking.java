public class HotelBooking {
    public int guestID;
    public String dateFrom;
    public int roomID;
    public int mealPlan;
    public int nrNights;
    public int price;

    public int getGuestID() {
        return guestID;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(int mealPlan) {
        this.mealPlan = mealPlan;
    }

    public int getNrNights() {
        return nrNights;
    }

    public void setNrNights(int nrNights) {
        this.nrNights = nrNights;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
