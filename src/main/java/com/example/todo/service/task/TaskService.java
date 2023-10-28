package com.example.todo.service.task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    public List<TaskEntity> find() {
        var task1 = new TaskEntity(
                1L,
                "Spring Bootを 学ぶ",
                "TODO アプリケーションを作ってみる",
                TaskStatus.TODO
        );
        var task2 = new TaskEntity(
                1L,
                "Spring Securityを 学ぶ",
                "ログイン機能を作ってみる",
                TaskStatus.DOING
        );
        return List.of(task1, task2);
    }
}
