package com.thoughtworks.rslist.api;


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

@RestController
public class RsController {
  public static List<RsEvent> rsList = initRsEventList();

  public static List<RsEvent> initRsEventList() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无分类", new User("Userl","male",22,"lliao@a.com","16888888888",10)));
    rsEvents.add(new RsEvent("第二条事件", "无分类", new User("Userj","male",23,"jliao@a.com","15888888888",10)));
    rsEvents.add(new RsEvent("第三条事件", "无分类", new User("Userb","male",21,"bliao@a.com","14888888888",10)));

    return rsEvents;
  }

  @GetMapping("/rs/lists")
  public ResponseEntity<List<RsEvent>> getLists() {
    return ResponseEntity.ok(rsList);
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity getOneRsEvent(@PathVariable int index) throws InvalidIndexException {
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
  /*
  @PostMapping("/rs/event")
  public ResponseEntity addOneRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    rsList.add(rsEvent);
    if(!UserController.users.contains(rsEvent.getUser())) {
      UserController.register(rsEvent.getUser());
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("index", String.valueOf(rsList.indexOf(rsEvent)));
    return new ResponseEntity(headers,HttpStatus.CREATED);
  }
  */
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


  @ExceptionHandler(InvalidIndexException.class)
  public ResponseEntity exceptionHandler(InvalidIndexException ex) {
    CommenError commentError =  new CommenError();

    commentError.setError(ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commentError);
  }
}
