package io.github.triploit.ttag;

import io.github.triploit.ttag.container.Inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable
{
    public int inventory_size; // Die Größe de Inventars (ähnliches maß wie Gramm oder KiloGramm)
    public int position = 0; // Aktuelle Position des Spielers
    public Inventory inventory = new Inventory(); // Das Inventar
    public boolean does_light = false; // Schauen, ob der Spieler Licht aussendet (ggf. durch Items)
}
