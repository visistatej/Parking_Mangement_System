class ParkingLot {
    private int capacity;
    private Vehicle[] parkedVehicles;
    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.parkedVehicles = new Vehicle[capacity];
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSpaces() {
        int count = 0;
        for (Vehicle vehicle : parkedVehicles) {
            if (vehicle == null) {
                count++;
            }
        }
        return count;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (isFull()) {
            return false;
        }

        for (int i = 0; i < parkedVehicles.length; i++) {
            if (parkedVehicles[i] == null) {
                vehicle.setSlotNumber(i + 1);
                parkedVehicles[i] = vehicle;
                return true;
            }
        }
        return false;
    }

    public boolean removeVehicle(String plateNumber) {
        for (int i = 0; i < parkedVehicles.length; i++) {
            Vehicle vehicle = parkedVehicles[i];
            if (vehicle != null && vehicle.getPlateNumber().equals(plateNumber)) {
                parkedVehicles[i] = null;
                return true;
            }
        }
        return false;
    }

    public Vehicle findVehicleByPlateNumber(String plateNumber) {
        for (Vehicle vehicle : parkedVehicles) {
            if (vehicle != null && vehicle.getPlateNumber().equals(plateNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    private boolean isFull() {
        for (Vehicle vehicle : parkedVehicles) {
            if (vehicle == null) {
                return false;
            }
        }
        return true;
    }
}
