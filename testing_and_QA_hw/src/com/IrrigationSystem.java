package com;
/**
 * IrrigationSystem - Hệ thống tưới cây tự động
 *
 * Mô tả:
 *  - Tự động tính toán lượng nước cần tưới cho cây ăn quả dựa trên các tham số môi trường.
 *  - Hệ thống giúp loại bỏ sự can thiệp thủ công và đảm bảo tưới cây hiệu quả, tối ưu.
 *
 * Đầu vào:
 *  - soilMoisture   : Độ ẩm đất (%), giá trị hợp lệ [0.0, 100.0]
 *  - lightIntensity : Cường độ ánh sáng (lux), giá trị hợp lệ [0, 120000]
 *  - airHumidity    : Độ ẩm không khí (%), giá trị hợp lệ [0.0, 100.0]
 *  - treeAge        : Tuổi cây (tháng), giá trị hợp lệ [1, 360]
 *  - isRain         : Có mưa tại thời điểm đo (true/false)
 *
 * Đầu ra:
 *  - Đối tượng Result gồm:
 *       + message: thông báo mô tả kết quả (có hoặc không cần tưới, hoặc báo lỗi)
 *       + waterAmount: giá trị lượng nước tính toán (lít)
 *
 * Công thức:
 *  Nếu không mưa và soilMoisture < 40 thì:
 *      waterAmount = [6 + (treeAge / 12)^1.5] × (40 - soilMoisture) / 40
 *      Sau đó điều chỉnh:
 *         + Nếu lightIntensity > 50000 → tăng 20%
 *         + Nếu airHumidity < 40       → tăng 15%
 *
 * Ngoại lệ:
 *  - Nếu dữ liệu cảm biến bị thiếu hoặc ngoài miền hợp lệ → ném IrrigationException.
 */
public class IrrigationSystem {

    /**
     * Custom Exception cho hệ thống tưới cây
     */
    public static class IrrigationException extends Exception {
        public IrrigationException(String message) {
            super(message);
        }
    }

    /**
     * Class Result chứa kết quả trả về của hệ thống tưới cây
     *  - message: mô tả kết quả cho người dùng
     *  - waterAmount: lượng nước cần tưới (lít)
     */
    public static class Result {
        private final String message;
        private final double waterAmount;

        public Result(String message, double waterAmount) {
            this.message = message;
            this.waterAmount = waterAmount;
        }

        public String getMessage() {
            return message;
        }

        public double getWaterAmount() {
            return waterAmount;
        }
    }

    /**
     * Method to calculate necessary water amount
     *
     * @param soilMoisture   Độ ẩm đất
     * @param lightIntensity Cường độ ánh sáng 
     * @param airHumidity    Độ ẩm không khí
     * @param treeAge        Tuổi cây (tháng)
     * @param isRain         Có mưa tại thời điểm đo
     * @return Result chứa thông điệp và giá trị lượng nước (lít)
     * @throws IrrigationException if one of the variables is null or is an invalid value 
     */
    public static Result calculateWaterAmount(
            Float soilMoisture,
            Integer lightIntensity,
            Float airHumidity,
            Integer treeAge,
            Boolean isRain
    ) throws IrrigationException {

        // Check null input
        if (soilMoisture == null || lightIntensity == null || airHumidity == null || treeAge == null || isRain == null) {
            throw new IrrigationException("Lỗi: Dữ liệu cảm biến bị thiếu (null).");
        }

        // 2. Kiểm tra miền hợp lệ của variable
        if (soilMoisture < 0.0 || soilMoisture > 100.0) {
            throw new IrrigationException("Lỗi: Độ ẩm đất không hợp lệ! Yêu cầu giá trị từ 0% đến 100%.");
        }
        if (treeAge < 1 || treeAge > 360) {
            throw new IrrigationException("Lỗi: Tuổi cây không hợp lệ! Yêu cầu giá trị từ 1 đến 360 tháng.");
        }
        if (lightIntensity < 0.0 || lightIntensity > 120000.0) {
            throw new IrrigationException("Lỗi: Cường độ ánh sáng không hợp lệ! Yêu cầu giá trị từ 0 đến 120000 lux.");
        }
        if (airHumidity < 0.0 || airHumidity > 100.0) {
            throw new IrrigationException("Lỗi: Độ ẩm không khí không hợp lệ! Yêu cầu giá trị từ 0% đến 100%.");
        }

        // Check case that don't need to water
        if (isRain) {
            return new Result("Đang có mưa, không cần tưới.", 0.00);
        }
        if (soilMoisture >= 40.0) {
            return new Result("Độ ẩm đất đủ, không cần tưới.", 0.00);
        }

        // Calculate base water
        double baseWater = (6 + Math.pow(treeAge / 12.0, 1.5)) * (40.0 - soilMoisture) / 40.0;
        double waterAmount = baseWater;

        // Adjust waterAmount 
        if (lightIntensity > 50000.0) {
            waterAmount *= 1.2;
        }
        if (airHumidity < 40.0) {
            waterAmount *= 1.15;
        }

        waterAmount = Math.round(waterAmount * 100.0) / 100.0;
    
        String message = "Lượng nước cần tưới là: " + String.format("%.2f", waterAmount) + " lít.";

        return new Result(message, waterAmount);
    }

    public static void main(String[] args) {
        try {
            Result result = calculateWaterAmount(30.0f, 60000, 35.0f, 24, false);
            System.out.println(result.getMessage());
        } catch (IrrigationException e) {
            System.out.println(e.getMessage());
        }
    }
}
