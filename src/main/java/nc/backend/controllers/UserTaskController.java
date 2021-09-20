package nc.backend.controllers;

import nc.backend.common.utils.ValidationException;
import nc.backend.dtos.UploadDto;
import nc.backend.dtos.UserTaskAttemptsDto;
import nc.backend.services.BackstopTestService;
import nc.backend.services.UserTaskService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;


@RestController
@RequestMapping("/user-tasks")
public class UserTaskController {

    private UserTaskService userTaskService;
    @Autowired
    private BackstopTestService backstopTestService;

    public UserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }


    @GetMapping("/task")
    public UserTaskAttemptsDto getUserTasks(@RequestParam(value = "userId", required = false) Long userId,
                                            @RequestParam(value = "taskId", required = false) Long taskId) throws ValidationException {
        return userTaskService.getUserTasks(userId, taskId);
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public ResponseEntity<UploadDto> uploadFile(@RequestParam("files[]") MultipartFile[] files,
                                                @RequestParam(value = "userId", required = false) Long userId,
                                                @RequestParam(value = "taskId", required = false) Long taskId)
            throws ValidationException, IOException, JSONException {

        List<MultipartFile> multipartFileArrayList = new ArrayList<>();
        Collections.addAll(multipartFileArrayList, files);
        List<String> fileNamesHtml = new ArrayList<String>();
        multipartFileArrayList.forEach(file -> {
            if (file.getOriginalFilename().contains(".html")){
                fileNamesHtml.add(file.getOriginalFilename());
            }
        });

        if (fileNamesHtml.size() > 1){
            throw new NoSuchFileException("Don't try to hack me with your htmls");
        }

        if (fileNamesHtml.size() == 0){
            throw new NoSuchFileException("None html file was provided");
        }

        for (MultipartFile file : files){
            this.userTaskService.uploadFile(file, userId, taskId);
        }

        return new ResponseEntity<UploadDto>(new UploadDto("Success upload"), HttpStatus.OK);
    }
}