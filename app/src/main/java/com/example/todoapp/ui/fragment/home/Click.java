package com.example.todoapp.ui.fragment.home;

import com.example.todoapp.models.Task;

public interface Click {
    void click (Task task);
    void delete (Task task);
}
