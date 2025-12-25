package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.config.VnPayConfig;
import com.hungnv.tourbooking.config.VnPayUtil;
import com.hungnv.tourbooking.domain.Order;
import com.hungnv.tourbooking.domain.PaymentStatus;
import com.hungnv.tourbooking.dto.CreateVnPayPaymentRequest;
import com.hungnv.tourbooking.dto.CreateVnPayPaymentResponse;
import com.hungnv.tourbooking.repository.OrderRepository;
import com.hungnv.tourbooking.service.OrderService;
import com.hungnv.tourbooking.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final VnPayService vnPayService;
    private final OrderRepository orderRepository;
    private final VnPayConfig vnPayConfig;

    public PaymentController(VnPayService vnPayService, OrderRepository orderRepository, VnPayConfig vnPayConfig) {
        this.vnPayService = vnPayService;
        this.orderRepository = orderRepository;
        this.vnPayConfig = vnPayConfig;
    }

    @PostMapping("/vnpay/create")
    public ResponseEntity<CreateVnPayPaymentResponse> createVnPayPayment(
        @RequestBody CreateVnPayPaymentRequest req,
        HttpServletRequest servletRequest
    ) {
        Order order = orderRepository.findById(req.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found"));
        BigDecimal amount = order.getTotalPrice();  // lấy tiền từ DB

        String clientIp = servletRequest.getRemoteAddr();
        String paymentUrl = vnPayService.createPaymentUrl(order.getId(), amount, clientIp);

        return ResponseEntity.ok(new CreateVnPayPaymentResponse(paymentUrl));
    }
    // TODO: /vnpay/ipn để VNPay gọi về báo kết quả thanh toán

    private Long extractOrderId(String vnpTxnRef) {
        if (vnpTxnRef == null) return null;
        try {
            // vnp_TxnRef format: orderId-timestamp
            String[] parts = vnpTxnRef.split("-");
            return Long.parseLong(parts[0]);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/vnpay/return")
    public ResponseEntity<?> handleVnPayReturn(@RequestBody Map<String, String> vnpParams) {

        // 1. Validate chữ ký
        boolean validSignature = VnPayUtil.validateSignature(vnpParams, vnPayConfig.getSecretKey());
        if (!validSignature) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Chữ ký VNPay không hợp lệ"
            ));
        }

        // 2. Lấy thông tin quan trọng
        String responseCode = vnpParams.get("vnp_ResponseCode");
        String vnpTxnRef = vnpParams.get("vnp_TxnRef");
        Long orderId = extractOrderId(vnpTxnRef);

        if (orderId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Không xác định được orderId từ vnp_TxnRef"
            ));
        }

        // 3. Cập nhật trạng thái Order
        Order order = orderRepository.findById(orderId)
            .orElse(null);

        if (order == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Không tìm thấy đơn hàng",
                "orderId", orderId
            ));
        }

        if ("00".equals(responseCode)) {
            order.setPaymentStatus(PaymentStatus.PAID);
        } else {
            order.setPaymentStatus(PaymentStatus.CANCELLED);
        }
        orderRepository.save(order);

        boolean success = "00".equals(responseCode);
        String msg = success ? "Thanh toán thành công" : "Thanh toán không thành công. Mã lỗi: " + responseCode;

        return ResponseEntity.ok(Map.of(
            "success", success,
            "message", msg,
            "orderId", orderId,
            "responseCode", responseCode
        ));
    }

    @GetMapping("/vnpay/ipn")
    public ResponseEntity<Map<String, String>> handleVnPayIpn(HttpServletRequest request) {

        // Chuyển tất cả query param sang Map<String, String>
        Map<String, String> vnpParams = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (values != null && values.length > 0) {
                vnpParams.put(key, values[0]);
            }
        });

        // 1. Validate chữ ký
        boolean validSignature = VnPayUtil.validateSignature(vnpParams, vnPayConfig.getSecretKey());
        if (!validSignature) {
            return ResponseEntity.ok(Map.of(
                "RspCode", "97",
                "Message", "Invalid signature"
            ));
        }

        String responseCode = vnpParams.get("vnp_ResponseCode");
        String vnpTxnRef = vnpParams.get("vnp_TxnRef");
        Long orderId = extractOrderId(vnpTxnRef);

        if (orderId == null) {
            return ResponseEntity.ok(Map.of(
                "RspCode", "01",
                "Message", "Order not found"
            ));
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.ok(Map.of(
                "RspCode", "01",
                "Message", "Order not found"
            ));
        }

        // Nếu đã PAID rồi thì trả luôn OK
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            return ResponseEntity.ok(Map.of(
                "RspCode", "00",
                "Message", "Order already confirmed"
            ));
        }

        if ("00".equals(responseCode)) {
            order.setPaymentStatus(PaymentStatus.PAID);
        } else {
            order.setPaymentStatus(PaymentStatus.CANCELLED);
        }
        orderRepository.save(order);

        return ResponseEntity.ok(Map.of(
            "RspCode", "00",
            "Message", "Confirm Success"
        ));
    }
}

