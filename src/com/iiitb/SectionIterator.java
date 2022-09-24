package com.iiitb;

import com.iiitb.modal.Item;
import com.iiitb.modal.Section;

import java.util.ArrayList;
import java.util.List;

public abstract class SectionIterator {
    List<String> order;
    public abstract boolean hasNext();
    public abstract void getNext();
}
