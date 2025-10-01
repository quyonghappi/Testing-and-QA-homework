package com;
import org.junit.jupiter.api.Test;
import com.IrrigationSystem.IrrigationException;
import com.IrrigationSystem.Result;
import static org.junit.jupiter.api.Assertions.*;

public class IrrigationSystemBVATest {
    // Định nghĩa Tolerance (delta) cho so sánh double
    private static final double DELTA = 0.005; 

    // --- TEST KHI isRAIN = FALSE ---
    @Test
    void testCaseBVA01() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().contains("19.23"));
    }

    @Test
    void testCaseBVA02() {
        // soil=-0.1 -> Throw Exception
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(-0.1f, 60000, 50.0f, 180, false);
        });
        assertTrue(exception.getMessage().contains("Độ ẩm đất không hợp lệ"));
    }

    @Test
    void testCaseBVA03() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(0.0f, 60000, 50.0f, 180, false);
        assertEquals(76.91, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA04() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(0.1f, 60000, 50.0f, 180, false);
        assertEquals(76.72, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA05() throws IrrigationException {
        // soil=99.9 -> waterAmount = 0
        Result result = IrrigationSystem.calculateWaterAmount(99.9f, 60000, 50.0f, 180, false);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Độ ẩm đất đủ, không cần tưới."));
    }

    @Test
    void testCaseBVA06() throws IrrigationException {
        // soil=99.9 -> waterAmount = 0
        Result result = IrrigationSystem.calculateWaterAmount(100.0f, 60000, 50.0f, 180, false);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Độ ẩm đất đủ, không cần tưới."));
    }

    @Test
    void testCaseBVA07() {
        Exception exception = assertThrows(IrrigationException.class, () -> {IrrigationSystem.calculateWaterAmount(100.1f, 60000, 50.0f, 180, false); });
        String expectedMessage = "Lỗi: Độ ẩm đất không hợp lệ! Yêu cầu giá trị từ 0% đến 100%.";
        
        // Kiểm tra thông báo ngoại lệ
        assertTrue(exception.getMessage().contains(expectedMessage), 
            "Thông báo lỗi không khớp với: " + expectedMessage
        );
    }

    @Test
    void testCaseBVA08() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(39.9f, 60000, 50.0f, 180, false);
        assertEquals(0.19, result.getWaterAmount(), DELTA);
    }
    
    @Test
    void testCaseBVA09() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(40.0f, 60000, 50.0f, 180, false);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().contains("Độ ẩm đất đủ, không cần tưới."));
    }

    @Test
    void testCaseBVA10() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(40.1f, 60000, 50.0f, 180, false);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().contains("Độ ẩm đất đủ, không cần tưới."));
    }

    @Test
    void testCaseBVA11() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 0, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Tuổi cây không hợp lệ! Yêu cầu giá trị từ 1 đến 360 tháng."));
    }
    
    @Test
    void testCaseBVA12() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 1, false);
        assertEquals(1.81, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA13() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 2, false);
        assertEquals(1.82, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA14() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 359, false);
        assertEquals(50.89, result.getWaterAmount(), DELTA);
    }
    
    @Test
    void testCaseBVA15() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 360, false);
        assertEquals(51.10, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA16() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 361, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Tuổi cây không hợp lệ! Yêu cầu giá trị từ 1 đến 360 tháng."));
    }

    @Test
    void testCaseBVA17() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, -1, 50.0f, 180, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Cường độ ánh sáng không hợp lệ! Yêu cầu giá trị từ 0 đến 120000 lux."));
    }

    @Test
    void testCaseBVA18() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 0, 50.0f, 180, false);
        assertEquals(16.02, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA19() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 1, 50.0f, 180, false);
        assertEquals(16.02, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA20() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 119999, 50.0f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA21() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 120000, 50.0f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA22() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 1200001, 50.0f, 180, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Cường độ ánh sáng không hợp lệ! Yêu cầu giá trị từ 0 đến 120000 lux."));
    }

    @Test
    void testCaseBVA23() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 49999, 50.0f, 180, false);
        assertEquals(16.02, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA24() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 50000, 50.0f, 180, false);
        assertEquals(16.02, result.getWaterAmount(), DELTA);
    }
    
    @Test
    void testCaseBVA25() throws IrrigationException {
        // light=50001 -> Tăng 20%
        // Base: 16.02 * 1.2 = 19.227 -> 19.23
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 50001, 50.0f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA26() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, -0.1f, 180, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Độ ẩm không khí không hợp lệ! Yêu cầu giá trị từ 0% đến 100%."));
    }

    @Test
    void testCaseBVA27() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 0.0f, 180, false);
        assertEquals(22.11, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA28() throws IrrigationException {
        // light=50001 -> Tăng 20%
        // Base: 16.02 * 1.2 = 19.227 -> 19.23
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 0.1f, 180, false);
        assertEquals(22.11, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA29() throws IrrigationException {
        // light=50001 -> Tăng 20%
        // Base: 16.02 * 1.2 = 19.227 -> 19.23
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 99.9f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA30() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 100.0f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA31() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, 100.1f, 180, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Độ ẩm không khí không hợp lệ! Yêu cầu giá trị từ 0% đến 100%."));
    }

    @Test
    void testCaseBVA32() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 39.9f, 180, false);
        assertEquals(22.11, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA33() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 40.0f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseBVA34() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 40.1f, 180, false);
        assertEquals(19.23, result.getWaterAmount(), DELTA);
    }
    
    // --- TEST KHI isRAIN = TRUE ---
  
    @Test
    void testCaseBVA35() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA36() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(-0.1f, 60000, 50.0f, 180, true);
        });
        assertTrue(exception.getMessage().contains("Độ ẩm đất không hợp lệ"));
    }

    @Test
    void testCaseBVA37() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(0.0f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA38() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(0.1f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA39() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(99.9f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA40() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(100.0f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA41() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(100.1f, 60000, 50.0f, 180, true);
        });
        assertTrue(exception.getMessage().contains("Độ ẩm đất không hợp lệ"));
    }

    @Test
    void testCaseBVA42() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(39.9f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA43() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(40.0f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA44() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(40.1f, 60000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA45() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 0, true);
        });
        assertTrue(exception.getMessage().contains("Tuổi cây không hợp lệ"));
    }

    @Test
    void testCaseBVA46() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 1, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA47() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 2, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA48() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 359, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA49() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 360, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA50() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, 50.0f, 361, true);
        });
        assertTrue(exception.getMessage().contains("Tuổi cây không hợp lệ"));
    }

    @Test
    void testCaseBVA51() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, -1, 50.0f, 180, true);
        });
        assertTrue(exception.getMessage().contains("Cường độ ánh sáng không hợp lệ"));
    }

    @Test
    void testCaseBVA52() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 0, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA53() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 1, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA54() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 119999, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA55() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 120000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA56() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 1200001, 50.0f, 180, true);
        });
        assertTrue(exception.getMessage().contains("Cường độ ánh sáng không hợp lệ"));
    }

    @Test
    void testCaseBVA57() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 49999, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA58() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 50000, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA59() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 50001, 50.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA60() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, -0.1f, 180, true);
        });
        assertTrue(exception.getMessage().contains("Độ ẩm không khí không hợp lệ"));
    }

    @Test
    void testCaseBVA61() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 0.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA62() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 0.1f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA63() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 99.9f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA64() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 100.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA65_RainCondition_fromCase31_Exception() {
        Exception exception = assertThrows(IrrigationSystem.IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 60000, 100.1f, 180, true);
        });
        assertTrue(exception.getMessage().contains("Độ ẩm không khí không hợp lệ"));
    }

    @Test
    void testCaseBVA66() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 39.9f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA67() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 40.0f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseBVA68() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 40.1f, 180, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }
}