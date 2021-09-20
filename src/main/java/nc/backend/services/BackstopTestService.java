package nc.backend.services;

import com.google.gson.Gson;
import nc.backend.jsonBackstopParser.BackstopClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class BackstopTestService {

    @Autowired
    UserTaskService userTaskService;

    public void prepareBackstopJson(Long userId, Long taskId) throws IOException {
        Gson gson = new Gson();
        BackstopClass backstop = gson.fromJson(new FileReader("./backstop.json"), BackstopClass.class);
        backstop.scenarios.get(0).setUrl("/upload/" + userId + "/" + taskId + "/" +
                (userTaskService.getNumberOfAttempts(userId, taskId) + 1) + ".html");
        backstop.scenarios.get(0).setReferenceUrl("/TaskReferences/" + taskId + ".html");
        backstop.report.clear();
        backstop.report.add("");
        FileWriter writer = new FileWriter("./backstop.json");
        gson.toJson(backstop, writer);
        writer.flush();
    }

    public void runProcess(String command) {
        try{
            Process pr = Runtime.getRuntime().exec(command);
            try(BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
                String line;
                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch(Exception e){
            System.out.println("e: "+ e.toString());
        }

    }

    public void runTest(Long userId, Long taskId) throws IOException {

        prepareBackstopJson(userId, taskId);
        runProcess("backstop reference");
        runProcess("backstop test");
    }
}
