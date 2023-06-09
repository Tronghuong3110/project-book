package com.bookstore.controller.user;

import com.bookstore.model.dto.Recipient_addressDto;
import com.bookstore.model.response.ResponseBill;
import com.bookstore.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class BillApi {

    @Autowired
    private IBillService billService;

    @PostMapping("/bill")
    public ResponseEntity<?> addBill(@RequestBody Recipient_addressDto recipientAddressDto,
                                     @RequestParam String totalPay) {
        String message = billService.saveBill(recipientAddressDto, Float.valueOf(totalPay));
        if(message.equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/bills")
    public ResponseEntity<?> getListBill() {
         List<ResponseBill> results = billService.findAllByBillId();
        if(results == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(results);
    }

    @DeleteMapping("/bill")
    public ResponseEntity<?> deleteBill(@RequestParam Long billId) {
        String message = billService.deleteBill(billId);
        return ResponseEntity.ok(message);
    }

}
