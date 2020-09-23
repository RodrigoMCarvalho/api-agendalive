package com.rodrigo.agendalive.controller;

import com.rodrigo.agendalive.document.LiveDocument;
import com.rodrigo.agendalive.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/lives")
public class LiveController {

    private LiveService liveService;

    @Autowired
    public void setLiveService(LiveService liveService) {
        this.liveService = liveService;
    }

    @GetMapping
    public ResponseEntity<Page<LiveDocument>> getAllLives(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @RequestParam(required = false) String flag){
        Page<LiveDocument> livePage = liveService.findAll(pageable, flag);
        if(livePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Page<LiveDocument>>(livePage, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveDocument> getOneLive(@PathVariable(value="id") String id) {
        Optional<LiveDocument> lives = liveService.findById(id);
        if(!lives.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<LiveDocument>(lives.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<LiveDocument> saveLive(@RequestBody @Valid LiveDocument live) {
        live.setRegistrationDate(LocalDateTime.now());
        return new ResponseEntity<>(liveService.save(live),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLive(@PathVariable(value="id") String id) {
        Optional<LiveDocument> liveO = liveService.findById(id);
        if(!liveO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            liveService.delete(liveO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiveDocument> updateLive(@PathVariable(value="id") String id,
                                                   @RequestBody @Valid LiveDocument live) {
        Optional<LiveDocument> liveO = liveService.findById(id);
        if(!liveO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            live.setId(liveO.get().getId());
            return new ResponseEntity<LiveDocument>(liveService.save(live), HttpStatus.OK);
        }
    }

}
