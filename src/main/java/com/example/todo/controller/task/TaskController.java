package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String list (Model model) {
        var taskList = taskService.find()
                .stream()
                .map(TaskDTO::toDTO
                )
                .toList();
        model.addAttribute("taskList", taskList);
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String showDetail (@PathVariable("id") long taskId, Model model) {
        var taskEntity = taskService.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: id" + taskId));
        model.addAttribute("task", TaskDTO.toDTO(taskEntity));
        return "tasks/detail";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(TaskForm form, Model model) {
        if(form == null) {
            form = new TaskForm(null, null, null);
        }
        model.addAttribute("taskForm", form);
        return "tasks/form";
    }

    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return showCreationForm(form, model);
        }

        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }
}
