package com.example.receiptprocessor.controller;

import org.springframework.web.bind.annotation.*;

import com.example.receiptprocessor.exception.ReceiptNotFoundException;
import com.example.receiptprocessor.model.PointsResponse;
import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.model.ReceiptResponse;
import com.example.receiptprocessor.service.ReceiptService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final Map<String, Receipt> receiptStore = new HashMap<>();

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ReceiptResponse processReceipt(@RequestBody Receipt receipt) {
        // Generate unique receipt ID and store it temporarily in memory
        String receiptId = receiptService.generateReceiptId();
        receiptStore.put(receiptId, receipt);
        return new ReceiptResponse(receiptId);
    }

    @GetMapping("/{id}/points")
    public PointsResponse getPoints(@PathVariable String id) {
        // Retrieve the receipt from memory and calculate points
        Receipt receipt = receiptStore.get(id);
        if (receipt == null) {
            throw new ReceiptNotFoundException(id);
        }
        int points = receiptService.calculatePoints(receipt);
        return new PointsResponse(points);
    }
}
