package main.loantrackingbackend.controller;

import main.loantrackingbackend.dto.TestDateDto;
import main.loantrackingbackend.util.TestDateManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/test-date")
public class TestDateController {

    @GetMapping
    public ResponseEntity<LocalDate> getToday() {
        return ResponseEntity.ok(TestDateManager.today());
    }

    @PostMapping("/set")
    public ResponseEntity<LocalDate> setDate(@RequestBody TestDateDto testDateDto) {
        TestDateManager.setDate(testDateDto.getDate());
        return ResponseEntity.ok(TestDateManager.today());
    }

    @PostMapping("/advance")
    public ResponseEntity<LocalDate> advanceDays(@RequestParam int days) {
        TestDateManager.advanceDays(days);
        return ResponseEntity.ok(TestDateManager.today());
    }

    @PostMapping("/rewind")
    public ResponseEntity<LocalDate> rewindDays(@RequestParam int days) {
        TestDateManager.rewindDays(days);
        return ResponseEntity.ok(TestDateManager.today());
    }

    @PostMapping("/reset")
    public ResponseEntity<LocalDate> reset() {
        TestDateManager.reset();
        return ResponseEntity.ok(TestDateManager.today());
    }
}