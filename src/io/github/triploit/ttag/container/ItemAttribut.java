package io.github.triploit.ttag.container;

import java.io.Serializable;
import java.util.List;

public class ItemAttribut implements Serializable
{
    public boolean light;
    public boolean weapon;
    public boolean key;
    public boolean catchable;
    public boolean readable;
    public boolean activated;
    public boolean activateable;
    public boolean burnable;
    public boolean fire_at_activate;
    public int key_id;
    public List<String> need_to_activate;
    public String text;
}
