package com.wanted.preonboarding.performance.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performance.application.PerformanceService;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
@Slf4j
public class QueryController {
    private final PerformanceService performanceService;

    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceResponse>>> getAllPerformanceInfoList(
        @RequestParam(value = "isReserve") boolean isReserve
    ) {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<PerformanceResponse>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(performanceService.getAllPerformanceInfoList(isReserve))
                .build()
            );
    }

    @GetMapping("/performance")
    public ResponseEntity<ResponseHandler<PerformanceResponse>> getPerformanceInfoDetail(
        @RequestParam(value = "name") String name
    ) {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<PerformanceResponse>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(performanceService.getPerformanceInfoDetail(name))
                .build()
            );
    }
}