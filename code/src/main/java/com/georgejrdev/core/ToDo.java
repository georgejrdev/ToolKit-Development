package com.georgejrdev.core;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.georgejrdev.core.interfaces.ToDoInterface;;


public class ToDo implements ToDoInterface{

    private String pathSave;
    private String os;
    private ManipulateJsonFile mJsonFile;

    public ToDo(){
        getCorrectPath();
        this.mJsonFile = new ManipulateJsonFile(this.pathSave);
    }


    @Override
    public void createNewTask(String content){
        this.mJsonFile.addItemInJsonFile(content);
    }

    @Override
    public void checkTask(int id, boolean newState){
        this.mJsonFile.updateItemInJsonFile(id, newState);
    }

    @Override
    public void showTasks(){
        this.mJsonFile.createNewJsonFile();
        
        List<Map<String,Object>> content = orderTasks(this.mJsonFile.getContentJsonFile());

        for(Map<String,Object> item : content) {
            int itemId = (int) item.get("id");
            boolean itemState = (boolean) item.get("state");
            String itemContent = (String) item.get("content");
            
            if (itemState){
                System.out.printf("ID: %d [ ] - %s%n",itemId,itemContent);
            } else {
                System.out.printf("ID: %d [x] - %s%n",itemId,itemContent);
            }
        }
    }

    @Override
    public void deleteTask(int id){
        this.mJsonFile.deleteItemInJsonFile(id);
    }

    private void getCorrectPath(){
        this.os = System.getProperty("os.name");

        if (this.os != null && this.os.toLowerCase().contains("linux")){
                this.pathSave = System.getenv("HOME") + "/ToolKit-dev/save/ToDoSave.json";

        } else {
            String appData = System.getenv("APPDATA");
            this.pathSave = appData + "\\toolkit-dev\\save\\ToDoSave.json";
        }
    } 

    private List<Map<String,Object>> orderTasks(List<Map<String,Object>> content){
        List<Map<String,Object>> orderContent = new ArrayList<>();

        for(Map<String,Object> item : content) {
            boolean state = (boolean) item.get("state");

            if (state == false){
                orderContent.add(item);

            } else {
                orderContent.add(0,item);
            }
        }

        return orderContent;
    }
}
