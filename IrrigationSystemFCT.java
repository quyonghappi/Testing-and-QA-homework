package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.IrrigationSystem.IrrigationException;
import com.IrrigationSystem.Result;

public class IrrigationSystemFCT {
    private static final double DELTA = 0.005; 

    @Test
    void testCaseFC01() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(null, 60000, 85.0f, 100, true);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Dữ liệu cảm biến bị thiếu (null)."));
    }

    @Test
    void testCaseFC02() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(-50.0f, 40000,40.0f, 200, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Độ ẩm đất không hợp lệ! Yêu cầu giá trị từ 0% đến 100%."));
    }

    @Test
    void testCaseFC03() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(23.0f, 80000, 50.0f, 400, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Tuổi cây không hợp lệ! Yêu cầu giá trị từ 1 đến 360 tháng."));
    }

    @Test
    void testCaseFC04() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(35.0f, -110000, 80.0f, 210, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Cường độ ánh sáng không hợp lệ! Yêu cầu giá trị từ 0 đến 120000 lux."));
    }

    @Test
    void testCaseFC05() {
        Exception exception = assertThrows(IrrigationException.class, () -> {
            IrrigationSystem.calculateWaterAmount(30.0f, 90000, -70.0f, 300, false);
        });
        assertTrue(exception.getMessage().equals("Lỗi: Độ ẩm không khí không hợp lệ! Yêu cầu giá trị từ 0% đến 100%."));
    }

    @Test
    void testCaseFC06() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(30.0f, 60000, 90.0f, 130, true);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Đang có mưa, không cần tưới."));
    }

    @Test
    void testCaseFC07() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(80.0f, 60000, 50.0f, 180, false);
        assertEquals(0.0, result.getWaterAmount(), DELTA);
        assertTrue(result.getMessage().equals("Độ ẩm đất đủ, không cần tưới."));
    }

    @Test
    void testCaseFC08() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(36.0f, 110000, 26.0f, 240, false);
        assertEquals(13.17, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseFC09() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(36.0f, 110000, 60.0f, 240, false);
        assertEquals(11.45, result.getWaterAmount(), DELTA);
    }

    @Test
    void testCaseFC10() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(22.0f, 32000, 30.0f, 140, false);
        assertEquals(23.73, result.getWaterAmount(), DELTA);
    }
    
    @Test
    void testCaseFC11() throws IrrigationException {
        Result result = IrrigationSystem.calculateWaterAmount(22.0f, 32000, 80.0f, 140, false);
        assertEquals(20.63, result.getWaterAmount(), DELTA);
    }
    
}
