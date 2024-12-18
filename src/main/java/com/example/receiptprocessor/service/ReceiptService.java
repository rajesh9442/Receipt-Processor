package com.example.receiptprocessor.service;

import org.springframework.stereotype.Service;

import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.model.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReceiptService {

    public String generateReceiptId() {
    	// Generate a unique ID
        return java.util.UUID.randomUUID().toString();
    }

    public int calculatePoints(Receipt receipt) {
        int points = 0;

        // One point for every alphanumeric character in the retailer name
        points += getAlphanumericCount(receipt.getRetailer());

        // 50 points if the total is a round dollar amount with no cents
        if (isRoundDollar(receipt.getTotal())) {
            points += 50;
        }

        // 25 points if the total is a multiple of 0.25
        if (isMultipleOfQuarter(receipt.getTotal())) {
            points += 25;
        }

        // 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // Points for item descriptions that are multiples of 3
        for (Item item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += calculateItemPoints(item.getPrice());
            }
        }

        // 6 points if the day in the purchase date is odd
        if (isOddDay(receipt.getPurchaseDate())) {
            points += 6;
        }

        // 10 points if the purchase time is after 2:00pm and before 4:00pm
        if (isBetween2and4PM(receipt.getPurchaseTime())) {
            points += 10;
        }

        return points;
    }

    private int getAlphanumericCount(String input) {
        return (int) input.chars().filter(Character::isLetterOrDigit).count();
    }

    private boolean isRoundDollar(String total) {
        return total.endsWith(".00");
    }

    private boolean isMultipleOfQuarter(String total) {
        BigDecimal value = new BigDecimal(total);
        return value.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0;
    }

    private int calculateItemPoints(String price) {
        BigDecimal itemPrice = new BigDecimal(price);
        BigDecimal points = itemPrice.multiply(new BigDecimal("0.2")).setScale(0, RoundingMode.UP);
        return points.intValue();
    }

    private boolean isOddDay(String date) {
        int day = Integer.parseInt(date.split("-")[2]);
        return day % 2 != 0;
    }

    private boolean isBetween2and4PM(String time) {
    	String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return (hour == 14 && minute >= 1) || (hour > 14 && hour < 16);
    }
}
