package io.github.triploit.ttag.container;

import java.io.Serializable;
import java.util.List;

public class ItemAttribut implements Serializable
{
    public boolean light; // Strahlt das Item Licht aus?
    public boolean weapon; // Ist es eine Waffe?
    public boolean key; // Ist es ein Schlüsel?
    public boolean catchable; // Kann man es aufheben?
    public boolean readable; // Kann man es lesen?
    public boolean activated; // Ist es aktiviert?
    public boolean activateable; // Kann man es aktivieren? (Mit Batterie oder anderem Item.)
    public boolean burnable; // Ist es brennbar?
    public boolean fire_at_activate; // Brennt es wenn man es aktiviert?
    public int key_id; // ID des Schlüssels
    public List<String> need_to_activate; // Name der Items die Man braucht, um das Item zu aktivieren; ID wäre vielleicht schlauer
    public String text; // Text den man Lesen kann, wenn das Item lesbar ist
}
