package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.thoughtworks.rslist.domain.RsEvent;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RsController {
  private List<RsEvent> rsList = createInitialList();
  private final RsEventRepository rsEventRepository;
  private final UserRepository userRepository;

  public RsController(RsEventRepository rsEventRepository, UserRepository userRepository) {
    this.rsEventRepository = rsEventRepository;
    this.userRepository = userRepository;
  }

  private static List<RsEvent> createInitialList() {
    List<RsEvent> initList = new ArrayList<>();
    return initList;
  }

  @GetMapping("/rs/lists")
  public ResponseEntity<List<RsEvent>> getLists() {
    return ResponseEntity.ok(rsList);
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity<RsEvent> getOneRsEvent(@PathVariable int index) throws InvalidIndexException {
    if(index > rsList.size()) {
      throw new InvalidIndexException("invalid index");
    }
    return ResponseEntity.ok((rsList.get(index - 1)));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventRange(@RequestParam(required = false) Integer start,
                                       @RequestParam(required = false) Integer end) throws InvalidIndexException {
    if(start == null || end == null){
      return ResponseEntity.ok(rsList);
    }
    if(start < 0 || start > rsList.size())
    {
      throw new InvalidIndexException("invalid request param");
    }
    return ResponseEntity.ok(rsList.subList(start-1, end));
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    Integer userId = Integer.valueOf(rsEvent.getUserId());
    if (!userRepository.existsById(userId)) {
      return ResponseEntity.badRequest().build();
    }

    RsEventEntity event = RsEventEntity.builder()
            .eventName(rsEvent.getEventName())
            .keyWord(rsEvent.getKeyWord())
            .userId(String.valueOf(userId))
            .build();
    rsEventRepository.save(event);
    return ResponseEntity.created(null).build();
  }

  @PatchMapping("/re/{reEventId}")
  public ResponseEntity UpdateRsEvent( @RequestBody RsEvent rsEvent)  {
    Integer userId = Integer.valueOf(rsEvent.getUserId());
    Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(userId);

    if (!userRepository.existsById(userId)) {
      return ResponseEntity.badRequest().build();
    } else {
      if (rsEvent.getEventName() != null) {
        rsEventEntity.get().setEventName(rsEvent.getEventName());
      }
      if (rsEvent.getKeyWord() != null) {
        rsEventEntity.get().setKeyWord(rsEvent.getKeyWord());
      }
      rsEventEntity.get().setUserId(rsEvent.getUserId());
      rsEventRepository.save(rsEventEntity.get());
    }
    return ResponseEntity.created(null).build();
  }

  @PutMapping("/rs/{index}")
  public void changeOneRsEvent(@PathVariable int index, @RequestBody RsEvent newrsEvent){
    RsEvent rsEvent = rsList.get(index-1);
    if(newrsEvent.getEventName() !=null && newrsEvent.getKeyWord() != null){
        rsEvent.setEventName(newrsEvent.getEventName());
        rsEvent.setKeyWord(newrsEvent.getKeyWord());
    }
  }

  @DeleteMapping("/rs/{index}")
  public void deleteRsEvent(@PathVariable int index){
    rsList.remove(index-1);
  }

  @PostMapping("/rs/vote/{rsEventId}")
  public void voteEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {

  }

  @ExceptionHandler(InvalidIndexException.class)
  public ResponseEntity exceptionHandler(InvalidIndexException ex) {
    CommenError commentError =  new CommenError();

    commentError.setError(ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commentError);
  }
}
