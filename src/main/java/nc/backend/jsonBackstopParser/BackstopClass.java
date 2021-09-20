package nc.backend.jsonBackstopParser;


import java.util.ArrayList;

public class BackstopClass {
    public String id;
    public ArrayList<Viewport> viewports;
    public ArrayList<Scenario> scenarios;
    public Paths paths;
    public ArrayList<String> report;
    public String engine;
    public int asyncCaptureLimit;
    public int asyncCompareLimit;
    public boolean debug;
    public boolean debugWindow;
}
