package ru.practicum.ewm.compilation.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.common.service.PublicCompilationService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PublicCompilationController {

    private final PublicCompilationService pcServ;

    @GetMapping("/{compId}")
    public ResponseEntity<Object> get(@PathVariable(name = "compId") Long compId) {
        log.info("-- PublicCompilationController -- Get compilation by id {}", compId);
        return new ResponseEntity<>(pcServ.get(compId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(name = "pinned", required = false) boolean pinned,
                                         @RequestParam(name = "from", defaultValue = "0") @Min(0) long from,
                                         @RequestParam(name = "size", defaultValue = "10") @Min(1) int size) {
        log.info("-- PublicCompilationController -- Find compilation from {}, page size {}", from, size);
        return new ResponseEntity<>(pcServ.getAll(pinned, from, size), HttpStatus.OK);
    }
}
