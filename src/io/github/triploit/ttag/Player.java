package io.github.triploit.ttag;

import io.github.triploit.ttag.container.Inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable
{
    public int inventory_size;
    public int position = 0;
    public Inventory inventory = new Inventory();
    public boolean does_light = false;
}
