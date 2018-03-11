package io.github.triploit.ttag.container;

import io.github.triploit.ttag.Item;
import io.github.triploit.ttag.Runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable
{
    public List<Item> items = new ArrayList<Item>(); // Die Liste an Items
    // Später auch Objekte

    public Item getItemByName(String name) // Item mit Name finden
    {
        for (Item i : items)
        {
            if (i.name.equalsName(name))
            {
                return i;
            }
        }

        return null;
    }

    public Item getItemByID(int id) // Item mit ID finden
    {
        for (Item i : items)
        {
            if (i.id == id)
            {
                return i;
            }
        }

        return null;
    }

    public boolean existsItemByName(String name) // Schauen ob Item mit bestimmtem Namen existiert
    {
        for (Item i : items)
        {
            if (i.name.equalsName(name))
            {
                return true;
            }
        }

        return false;
    }

    public void collectItemFromGame(int id) // Item aus Spiel in Spielerinventar aufheben/verschieben
    {
        if (getFilledSize() > Runtime.game.player.inventory_size)
        {
            System.out.println("Du hast schon zu viel Platz belegt ("+getFilledSize()+"/"+Runtime.game.player.inventory_size+"),\ndas Item, welches du nehmen willst ist zu groß ("+Runtime.game.getItemByID(id).size+")!");
            return;
        }
        items.add(Runtime.game.getItemByID(id));
        Runtime.game.removeItem(id);
    }

    public int getFilledSize() // Schauen wie voll das Inventar ist
    {
        int c = 0;
        for (Item i : items)
        {
            c += i.size;
        }
        return c;
    }

    public boolean existsItemByID(int id) // Schauen ob Item mit der ID existiert
    {
        for (Item i : items)
        {
            if (i.id == id)
            {
                return true;
            }
        }

        return false;
    }

    public void removeItemByName(String name) // Item mit Name entfernen
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).name.equalsName(name)) items.remove(i);
        }
    }

    public void removeItemByID(int id) // Item mit ID entfernen
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).id == id) items.remove(i);
        }
    }
}
