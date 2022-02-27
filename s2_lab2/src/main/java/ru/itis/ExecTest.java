package ru.itis;

import ru.itis.dis.s2lab1.annotations.Inject;
import ru.itis.dis.s2lab1.Context;
import ru.itis.views.AppMenu;
import ru.itis.views.DirectoryPicker;

import java.io.IOException;


public class ExecTest {

    @Inject
    static DirectoryPicker picker;

    public static void main(String[] args) {
        new Context(ExecTest.class);
        picker.initFrame();
    }
}
