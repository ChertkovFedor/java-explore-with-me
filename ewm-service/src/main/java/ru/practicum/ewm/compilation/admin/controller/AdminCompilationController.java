package ru.practicum.ewm.compilation.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.admin.service.AdminCompilationService;
import ru.practicum.ewm.compilation.general.dto.CreateCompilationDto;
import ru.practicum.ewm.compilation.general.dto.UpdateCompilationRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService acServ;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @NotNull @Valid CreateCompilationDto compilationDto) {
        log.info("-- AdminCompilationController -- Create compilation {}", compilationDto);
        return new ResponseEntity<>(acServ.create(compilationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> delete(@PathVariable(name = "compId") Long compId) {
        log.info("-- AdminCompilationController -- Delete compilation {}", compId);
        acServ.delete(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<Object> update(@PathVariable(name = "compId") Long compId,
                                         @RequestBody @NotNull UpdateCompilationRequest updateRequest) {
        log.info("-- AdminCompilationController -- Update compilation {}", compId);
        return new ResponseEntity<>(acServ.update(compId, updateRequest), HttpStatus.OK);
    }
}
