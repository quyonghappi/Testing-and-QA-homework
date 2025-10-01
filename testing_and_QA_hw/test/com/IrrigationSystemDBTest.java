package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.IrrigationSystem.IrrigationException;
import com.IrrigationSystem.Result;

public class IrrigationSystemDBTest {
    // Định nghĩa Tolerance (delta) cho so sánh double
    private static final double DELTA = 0.005; 

    @Test
    void testCaseDB01() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(null, 60000, 50.0f, 180, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Dữ liệu cảm biến bị thiếu (null)."));
    }

    @Test
    void testCaseDB02() throws IrrigationSystem.IrrigationException {
        IrrigationSystem.Result result = IrrigationSystem.calculateWaterAmount(30.0f, 55000, 40.0f, 130, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseDB03() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(-60.0f, 40000, 40.0f, 200, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Độ ẩm đất không hợp lệ! Yêu cầu giá trị từ 0% đến 100%."));
    }

    @Test
    void testCaseDB04() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(60.0f, 40000, 40.0f, 200, false);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Độ ẩm đất đủ, không cần tưới."));
    }

    @Test
    void testCaseDB05() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(23.0f, 80000, 50.0f, 400, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Tuổi cây không hợp lệ! Yêu cầu giá trị từ 1 đến 360 tháng."));
    }

    @Test
    void testCaseDB06() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(35.0f, 122000, 80.0f, 240, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Cường độ ánh sáng không hợp lệ! Yêu cầu giá trị từ 0 đến 120000 lux."));
    }

    @Test
    void testCaseDB07() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 45000, -70.0f, 240, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Độ ẩm không khí không hợp lệ! Yêu cầu giá trị từ 0% đến 100%."));
    }

     @Test
    void testCaseDB08() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 45000, 60.0f, 240, false);
        assertEquals(23.86, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseDB09() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 45000, 20.0f, 240, false);
        assertEquals(27.44, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseDB10() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 90000, -70.0f, 240, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Độ ẩm không khí không hợp lệ! Yêu cầu giá trị từ 0% đến 100%."));
    }

     @Test
    void testCaseDB11() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 90000, 60.0f, 240, false);
        assertEquals(28.63, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseDB12() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 90000, 20.0f, 240, false);
        assertEquals(32.93, result.getWaterAmount(), DELTA);
    }


}
