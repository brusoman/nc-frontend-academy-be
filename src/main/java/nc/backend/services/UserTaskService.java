package nc.backend.services;

import com.google.gson.Gson;
import nc.backend.common.utils.ValidationException;
import nc.backend.daos.TaskDao;
import nc.backend.daos.UserDao;
import nc.backend.daos.UserTaskDao;
import nc.backend.dtos.UserTaskAttemptsDto;
import nc.backend.dtos.UserTaskDto;
import nc.backend.entities.Task;
import nc.backend.entities.User;
import nc.backend.entities.UserTask;
import nc.backend.entities.UserTaskPK;
import nc.backend.jsonReportParser.ReportClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.math.BigDecimal;

import static nc.backend.common.utils.ValidationUtils.validateIsNotNull;

@Service
public class UserTaskService {
    private final UserTaskDao userTaskDao;
    private final TaskService taskService;
    private final UserDao userDao;
    private final TaskDao taskDao;

    private static Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);
    private final String UPLOAD_PATH = "upload";
    @Autowired
    private BackstopTestService backstopTestService;


    public UserTaskService(UserTaskDao userTaskDao, TaskService taskService, UserDao userDao, TaskDao taskDao) {
        this.userTaskDao = userTaskDao;
        this.taskService = taskService;
        this.userDao = userDao;
        this.taskDao = taskDao;
    }

    public UserTaskAttemptsDto getUserTasks(Long userId, Long taskId) throws ValidationException {
        validateIsNotNull(userId, "No user is provided");
        validateIsNotNull(taskId, "No task provided");

        List<UserTask> userTasks = userTaskDao.findByUserIdAndTaskId(userId, taskId);
        validateIsNotNull(userTasks, "No userTasks with userId & taskId");

        List<UserTaskDto> userTaskDtoList = buildUserTaskDtoListFromUserTaskList(userTasks);
        Task task = this.taskDao.findByID(taskId);
        User user = this.userDao.findByID(userId);
        validateIsNotNull(user, "There is no user with such id");
        validateIsNotNull(task, "There is no task with such id");

        return new UserTaskAttemptsDto(userTaskDtoList);
    }


    //todo permitted file types
    public void uploadFile(MultipartFile file, Long userId, Long taskId) throws ValidationException, NoSuchFileException {
        validateIsNotNull(file.getOriginalFilename(), "Uploaded file has null name");

        if (!file.getOriginalFilename().contains(".html") &&
                !file.getOriginalFilename().contains(".css")
        && !file.getOriginalFilename().contains(".js")) {
            throw new NoSuchFileException("Wrong file type");
        }

        User user = this.userDao.findByID(userId);
        Task task = this.taskDao.findByID(taskId);
        validateIsNotNull(user, "There is no user with such id");
        validateIsNotNull(task, "There is no task with such id");

        File uploadedFile = null;
        File uploadFolder = createFolder("", this.UPLOAD_PATH);
        File userFolder= createFolder(this.UPLOAD_PATH, String.valueOf(userId));
        File userTaskFolder = createFolder(this.UPLOAD_PATH + "/" + userFolder.getName(), String.valueOf(taskId));

        if (file.getOriginalFilename().contains(".html")){
            uploadedFile = new File(this.UPLOAD_PATH + "/" + userFolder.getName()
                    + "/" + userTaskFolder.getName(), (getNumberOfAttempts(userId, taskId) + 1) + ".html");

        }

        if (file.getOriginalFilename().contains(".css") || file.getOriginalFilename().contains(".js")){
            uploadedFile = new File(this.UPLOAD_PATH + "/" + userFolder.getName()
                    + "/" + userTaskFolder.getName(), file.getOriginalFilename());

            if(uploadedFile.exists()){
                uploadedFile.delete();
            }
        }

        int attemptsNumber = this.getNumberOfAttempts(userId, taskId);
        if (attemptsNumber == task.getAttempts_max()){
            throw new ValidationException("Limited max attempts number");
        }


        try {
            uploadedFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(file.getOriginalFilename().contains(".html")){
            System.out.println(file.getOriginalFilename());
            Date beforeTest = new Date();
            try {
                this.backstopTestService.runTest(userId, taskId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Date afterTest = new Date();
            try {
                insertUserTask(userId, taskId, beforeTest, afterTest);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        logger.info("File has uploaded" + " " + file.getOriginalFilename());
    }

    private File createFolder(String path, String name) throws NoSuchFileException {
        File folder = new File(path, name);
        if(!folder.exists()){
            folder.mkdir();
        }

        return folder;
    }

    private List<UserTaskDto> buildUserTaskDtoListFromUserTaskList(List<UserTask> userTasks){
        List<UserTaskDto> userTaskDtoList = new ArrayList<>();

        userTasks.sort((first, second) -> -first.getTime().compareTo(second.getTime()));
        userTasks.sort((first, second) -> -first.getProgress().compareTo(second.getProgress()));

        userTasks.forEach(userTask -> {
            userTaskDtoList.add(new UserTaskDto(userTask.getProgress(),
                    userTask.getTime().toString(), userTask.getPath_result().substring(16)
                    .replace("/", "--"),
                    userTask.getPath_upload().substring(16).replace("/", "--")));

        });
        return userTaskDtoList;
    }

    public int getNumberOfAttempts(Long userId, Long taskId) {
        List<UserTask> userTasks = userTaskDao.findByUserIdAndTaskId(userId, taskId);
        logger.info("size {}", userTasks.size());
        return userTasks.size();
    }

    public String getReportPath(Date beforeTest, Date afterTest) {
        SimpleDateFormat formatForDate = new SimpleDateFormat("yyyyMMdd-HHmmss");

        String reportPath = null;
        for (Date time = beforeTest; time.getTime() - afterTest.getTime() <= 1000; time.setTime(time.getTime() + 1000)) {
            try { //попытка открыть файл на чтение
                new FileReader("./backstop_data/bitmaps_test/" + formatForDate.format(time) + "/report.json");
                reportPath = "./backstop_data/bitmaps_test/" + formatForDate.format(time) + "/";
                break;
            } catch (Exception e) { }
        }

        System.out.println(reportPath);
        return reportPath;
    }

    public UserTask generateUserTask(Long userId, Long taskId, Date beforeTest, Date afterTest) throws FileNotFoundException {
        String reportPath = getReportPath(beforeTest, afterTest);
        UserTaskPK userTaskPK = new UserTaskPK(userId, taskId, (long) getNumberOfAttempts(userId, taskId) + 1);

        Gson gson = new Gson();
        ReportClass report = gson.fromJson(new FileReader(reportPath + "report.json"), ReportClass.class);
        String mismatch = report.tests.get(0).pair.diff.misMatchPercentage;

        String path_result = report.tests.get(0).pair.diffImage;
        if (path_result == null) path_result = report.tests.get(0).pair.test;

        //String path_upload = report.tests.get(0).pair.url;
        String path_upload = report.tests.get(0).pair.test;
        System.out.println("STATUS OF TEST: " + report.tests.get(0).status + "|  mismatch: " + report.tests.get(0).pair.diff.misMatchPercentage + "%");

        BigDecimal mismatchDec = new BigDecimal(mismatch);
        return new UserTask(userTaskPK, new BigDecimal(100).subtract(mismatchDec), path_result, path_upload, ZonedDateTime.now());
    }


    public void insertUserTask(Long userId, Long taskId, Date beforeTest, Date afterTest) throws FileNotFoundException {
        UserTask userTask = generateUserTask(userId, taskId, beforeTest, afterTest);
        userTaskDao.save(userTask);
    }
}
