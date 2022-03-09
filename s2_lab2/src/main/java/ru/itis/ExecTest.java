package ru.itis;

import ru.itis.dis.s2lab1.annotations.Inject;
import ru.itis.dis.s2lab1.Context;
import ru.itis.views.DirectoryPicker;



public class ExecTest {

    @Inject
    static DirectoryPicker picker;

    public static void main(String[] args) {
        new Context(ExecTest.class);
        picker.initFrame();
    }
}
