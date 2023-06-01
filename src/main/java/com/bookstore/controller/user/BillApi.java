package com.bookstore.controller.user;

import com.bookstore.model.dto.Recipient_addressDto;
import com.bookstore.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
