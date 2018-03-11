package io.github.triploit.ttag.container;

import java.io.Serializable;

public class RoomAttribut implements Serializable
{
    public boolean dark, hot, cold, inside; // Attribute: dunkler Raum, heißer Raum, kalter Raum, ???
    public String description; // Beschreibung des Raums
}
